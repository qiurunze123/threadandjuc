package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.logic.ImportDataStepLogic;
import com.geekq.highimporttry.logic.ImportDataTaskLogic;
import com.geekq.highimporttry.logic.ImportPointLogic;
import com.geekq.highimporttry.logic.PointLogic;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.ImportPointDao;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.util.Constant;
import com.geekq.highimporttry.util.DateUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.hint.HintManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

import static com.geekq.highimporttry.util.Constant.BILL_2;
import static com.geekq.highimporttry.util.Constant.IMPORT_TASK_AND_STEP_RETRY_TIME;

/**
 * @author 邱润泽 bullock
 */
@Service
@Slf4j
public class HighImportDataServiceImpl implements HighImportDataService {

    public static final Logger logger = LoggerFactory.getLogger(HighImportDataServiceImpl.class);

    @Autowired
    private PointLogic pointLogic;

    @Autowired
    private ImportDataTaskLogic importDataTaskLogic;

    @Autowired
    private ImportDataStepLogic importDataStepLogic;

    @Autowired
    private ImportPointLogic importPointLogic;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Override
    public void recordHandle(String day) {

        logger.warn("数据导入 start!  day:{}", day);
        //导入 -- 时间 等初始化
        ImportDataTask pointTask = this.queryPointTask(day);
        //根据数据数量 来初始化 数据范围
        List<ImportDataStep> pointSteps = this.queryPointSteps(pointTask);

        //保证数据一致性
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(definition);
        try {
            HintManager hintManager = HintManager.getInstance();
            hintManager.setDatabaseShardingValue(1);
            // 插入 task表
            importDataTaskLogic.insert(pointTask);
            //  插入step 表
            for (ImportDataStep stat : pointSteps) {
                stat.setTaskId(pointTask.getId());
            }
            importDataStepLogic.insertBatch(pointSteps);
            hintManager.close();
            transactionManager.commit(transaction);
        } catch (Exception e) {
            logger.error("数据插入失败！！回滚！", e);
            transactionManager.rollback(transaction);
        }
    }

    @Override
    public void recordHandleImport(String day) {
        //自定义线程池
        ExecutorService executor = new ThreadPoolExecutor(4, 4,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("高可用改造专用线程池-%d").build());


        try {
            //强制路由 分片规则
            BillRecordPointInsertTask pointInsertTask = new BillRecordPointInsertTask(importDataTaskLogic, importDataStepLogic,
                    day, this);
            executor.execute(pointInsertTask);
        } catch (Exception e) {
            logger.error("导入数据 import_x_x 线程池发生错误", e);
        }
        executor.shutdown();
        try {
            while (!executor.isTerminated()) {
                if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.info("recordHandleImport thread pool close successfully");
                } else {
                    logger.info("Waiting recordHandleImport thread pool close...");
                }
            }
        } catch (InterruptedException e) {
            logger.error("线程池异常", e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
            executor.shutdownNow();
        }

    }


    /**
     * ===============================   查询 point 参数拼接 =========================
     */

    public ImportDataTask queryPointTask(String day) {
        logger.info("start -------- > point 拼接task!");
        ImportDataTask record = new ImportDataTask();
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setDay(day);
        record.setStatus(Constant.IMPORT_NOT_DEAL);
        record.setType(Constant.IMPORTTYPE.point.name());
        record.setVersion(1);
        return record;
    }

    public List<ImportDataStep> queryPointSteps(ImportDataTask importDataTask) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(2);
        long maxPointId = pointLogic.getMaxPointId();
        hintManager.close();
        logger.info("start -------->> pointSteps拼接开始!");
        long index;
        if (maxPointId % Constant.limitNew != 0) {
            index = maxPointId / Constant.limitNew + 1;
        } else {
            index = maxPointId / Constant.limitNew;
        }
        List<ImportDataStep> lists = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            ImportDataStep step = new ImportDataStep();
            step.setCreateTime(new Date());
            step.setDay(importDataTask.getDay());
            //mysql between 去除 重复数据
            if (i == 0) {
                step.setRangeStart((long) i * Constant.limitNew);
            } else {
                step.setRangeStart((long) i * Constant.limitNew + 1);
            }
            //获取 最后一条
            if (i == index - 1) {
                step.setRangeEnd(maxPointId);
            } else {
                step.setRangeEnd((long) (i + 1) * Constant.limitNew);
            }
            step.setMsg("point 表数据 index :" + index + "起始位置" + step.getRangeStart() + "结束位置" + step.getRangeEnd() + "天数" + importDataTask.getDay());
            step.setTaskId(importDataTask.getId());
            step.setVersion(1);
            step.setUpdateTime(new Date());
            step.setStatus(Constant.IMPORT_NOT_DEAL);
            step.setType(Constant.IMPORTTYPE.point.name());
            lists.add(step);
        }
        return lists;
    }


    /**
     * ==============================================
     *
     * @param steps
     */
    @Override
    public void insertPointTaskStep(List<ImportDataStep> steps) {
        //返回结果
        for (ImportDataStep step : steps) {
            try {
                logger.info("start  账单 point 批量插入处理！ " + step.getRangeStart() + " end :" + step.getRangeEnd());
                //强制路由 分片规则
                HintManager hintManager1 = HintManager.getInstance();
                hintManager1.setDatabaseShardingValue(2);
                List<Point> points = pointLogic.queryAllByPointId(step.getRangeStart(), step.getRangeEnd());
                hintManager1.close();
                String yMonth = DateUtil.formatCompactMonthDate(DateUtil.parse(step.getDay()));
                /**
                 * 事务保证批次和失败成功状态对应上
                 */
                //强制路由 分片规则
                HintManager hintManager2 = HintManager.getInstance();
                hintManager2.setDatabaseShardingValue(1);

                DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
                TransactionStatus transaction = transactionManager.getTransaction(definition);
                try {
                    if (!CollectionUtils.isEmpty(points)) {
                        logger.info("start insert batch !");

                        int flag = importPointLogic.insertBatch(points, "import_point_" +
                                yMonth, step.getDay(), new Date());
                        if (flag > 0) {
                            importDataStepLogic.updateByStepId(step.getId(), Constant.IMPORT_SUCCESS, new Date());
                            importDataTaskLogic.updateByTaskId(step.getTaskId(), Constant.IMPORT_SUCCESS, new Date());
                        } else {
                            importDataStepLogic.updateByStepId(step.getId(), Constant.IMPORT_FAIL, new Date());
                            importDataTaskLogic.updateByTaskId(step.getTaskId(), Constant.IMPORT_FAIL, new Date());
                        }
                    } else {
                        importDataStepLogic.updateByStepId(step.getId(), Constant.IMPORT_RANGE_NO_DATA, new Date());
                        importDataTaskLogic.updateByTaskId(step.getTaskId(), Constant.IMPORT_RANGE_NO_DATA, new Date());
                        logger.info("point  start" + step.getRangeStart() + "end :" + step.getRangeEnd() + "+ 范围无数据！！");
                    }
                    transactionManager.commit(transaction);
                } catch (Exception e) {
                    logger.error("数据插入失败！！回滚！", e);
                    transactionManager.rollback(transaction);
                }
                hintManager2.close();
                logger.info("start  " + step.getRangeStart() + " end :" + step.getRangeEnd());
            } catch (Exception e) {
                logger.error("数据插入失败！！回滚！此次批次失败的start! {},{},继续下一批次start ", step.getRangeStart(), e);
            }
        }
    }

    private ExecutorService executorService = new ThreadPoolExecutor(4, 4,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactoryBuilder().setNameFormat("高可用改造专用线程池-%d").build());

    @Override
    public List<Point> recordHandlePoints(List<ImportDataStep> steps) {

        System.out.println("多线程查询======== start");

        List<Point> points = new ArrayList<>();
        for (ImportDataStep step : steps) {
            Future<List<Point>> future = executorService.submit(new HighImportDataPointFuture(step, pointLogic));
            try {
                points.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("多线程查询======== point " + points.size() + " 条数==============");
        return points;
    }

    @Override
    public void importData(String data) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        String day = DateUtil.format(date, DateUtil.DATE_FORMAT_DAY_SHORT);
        /**
         * ======= 如果 今天的数据 存在 则无需重复导入 重试 5次 ====
         */
        boolean flagImport = false;
        int importTaskAndStepTime = 0;

        while (importTaskAndStepTime < IMPORT_TASK_AND_STEP_RETRY_TIME && !flagImport) {

            //强制路由 分片规则
            HintManager hintManager = HintManager.getInstance();
            hintManager.setDatabaseShardingValue(1);
            boolean todayIsTryImport = importDataTaskLogic.queryTodayTaskIsImport(day) > 0;
            hintManager.close();

            if (todayIsTryImport) {
                logger.info("今日{}数据已导入importdataTask 和 importdataStep 表 ", day);
            } else {
                this.recordHandle(day);
            }
            flagImport = todayIsTryImport;
            importTaskAndStepTime++;
        }
        if (flagImport) {
            boolean recordHandleImport = false;
            int recordHandleImportStepTime = 0;
            /**
             * ========  如果 没有未导入与导入失败的则无需重试 =======
             */
            while (recordHandleImportStepTime < IMPORT_TASK_AND_STEP_RETRY_TIME && !recordHandleImport) {
                //强制路由 分片规则
                HintManager hintManager = HintManager.getInstance();
                hintManager.setDatabaseShardingValue(1);
                recordHandleImport = importDataStepLogic.queryTodayStepIsImportSuccess(day) == 0;
                hintManager.close();

                if (recordHandleImport) {
                    logger.info("import_x_x_x 该数据已导入完毕 ====!");
                } else {
                    String yMonth = DateUtil.formatCompactMonthDate(new Date());
                    String dyTable = "import_point_" + yMonth ;
                    HintManager hintManager2 = HintManager.getInstance();
                    hintManager2.setDatabaseShardingValue(1);
                    //如果没有今日的表则创建一个
                    importDataStepLogic.isExistTodayTable(BILL_2,"import_point_" +
                            yMonth);
                    hintManager2.close();
                    this.recordHandleImport(day);
                }
                recordHandleImportStepTime++;
            }

            if (!recordHandleImport) {
                logger.info("import_x_x_x 导入失败！ 重试结束等待下次任务！");
                return;
            }
        } else {
            logger.info("task and step 未导入！");
            return;
        }
        long end = System.currentTimeMillis();
        logger.info("导入生成全部 task .step import_x_x_x 需要时间 ！time  = {}", end - start);
    }


    private static TableRuleConfiguration getOrderTableRuleConfiguration(String logicTable) {
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration(logicTable,
                null);
        orderTableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE"
                , "id",getProps()));
        return orderTableRuleConfig;
    }

    private static Properties getProps(){
        Properties props = new Properties();
        props.setProperty("worker.id", "123");
        return props;
    }


}
