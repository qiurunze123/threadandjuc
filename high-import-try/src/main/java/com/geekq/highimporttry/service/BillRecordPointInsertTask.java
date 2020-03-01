package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.logic.ImportDataStepLogic;
import com.geekq.highimporttry.logic.ImportDataTaskLogic;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.util.Constant;
import com.geekq.highimporttry.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.geekq.highimporttry.util.Constant.BILL_2;

/**
 * @author 邱润泽 bullock
 */
public class BillRecordPointInsertTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BillRecordPointInsertTask.class);

    private ImportDataTaskLogic importDataTaskLogic;
    private ImportDataStepLogic importDataStepLogic;
    private String day;
    private HighImportDataService  highImportDataService;

    public BillRecordPointInsertTask(ImportDataTaskLogic importDataTaskLogic,
                                     ImportDataStepLogic importDataStepLogic,
                                     String day,HighImportDataService  highImportDataService) {
        this.importDataStepLogic = importDataStepLogic;
        this.importDataTaskLogic = importDataTaskLogic;
        this.day = day;
        this.highImportDataService = highImportDataService;
    }


    @Override
    public void run() {
        logger.info("start ------>>  账单 point 批量插入处理！");
        HintManager hintManager1 = HintManager.getInstance();
        hintManager1.setDatabaseShardingValue(1);
        List<ImportDataTask> importDataTasks = importDataTaskLogic.queryTaskDatas(Constant.IMPORTTYPE.point.name(), day);
        hintManager1.close();
        //开始时间
        long start = System.currentTimeMillis();
        for (ImportDataTask importDataTask : importDataTasks) {

            /**
             * 返回结果失败结果重试10次
             */
            //强制路由 分片规则

            final Integer RetryTime = 10;
            int handleTime = 0;
            while (handleTime < RetryTime) {

                HintManager hintManager2 = HintManager.getInstance();
                hintManager2.setDatabaseShardingValue(1);
                List<ImportDataStep> steps = importDataStepLogic.queryAllStepNotDealAndFail(importDataTask.getId(),
                        day, Constant.IMPORTTYPE.point.name());
                hintManager2.close();

                if (CollectionUtils.isEmpty(steps)) {
                    break;
                }
                logger.info("point handletime  重试!,{}", handleTime);
                highImportDataService.insertPointTaskStep(steps);
                handleTime++;
            }
        }
        long end = System.currentTimeMillis();
        logger.info("end ------>>  账单 point 批量插入处理结束！time :{}", (end - start) + "ms");
    }


}