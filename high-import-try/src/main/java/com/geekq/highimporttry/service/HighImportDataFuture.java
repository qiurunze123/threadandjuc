package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author 邱润泽 bullock
 */
public class HighImportDataFuture implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(HighImportDataFuture.class);

    private ImportDataTaskDao importDataTaskDao;
    private ImportDataStepDao importDataStepDao;
    private String day;
    private HighImportDataService  highImportDataService;


    public HighImportDataFuture(ImportDataTaskDao importDataTaskDao,
                                     ImportDataStepDao importDataStepDao,
                                     String day,HighImportDataService  highImportDataService) {
        this.importDataStepDao = importDataStepDao;
        this.importDataTaskDao = importDataTaskDao;
        this.day = day;
        this.highImportDataService = highImportDataService;
    }
    @Override
    public Integer call() throws Exception {
        logger.info("start HighImportDataFuture ------>>  账单 point 批量插入处理 ！");
        List<ImportDataTask> importDataTasks = importDataTaskDao.queryTaskDatas(Constant.IMPORTTYPE.point.name(), day);
        //开始时间
        long start = System.currentTimeMillis();
        Integer countSum = 0;
        for (ImportDataTask importDataTask : importDataTasks) {

            /**
             * 返回结果失败结果重试10次
             */
            final Integer RetryTime = 10;
            int handleTime = 0;
            while (handleTime < RetryTime) {
                List<ImportDataStep> steps = importDataStepDao.queryAllStepNotDealAndFail(importDataTask.getId(),
                        day, Constant.IMPORTTYPE.point.name());

                if (CollectionUtils.isEmpty(steps)) {
                    break;
                }
                countSum++;//执行了多少次
                logger.info("point HighImportDataFuture handletime  重试!,{}", handleTime);
                highImportDataService.insertPointTaskStep(steps);
                handleTime++;
            }
        }
        long end = System.currentTimeMillis();
        logger.info("end ------>>  HighImportDataFuture 账单 point 批量插入处理结束！time :{}", (end - start) + "ms");

        logger.info("countSum + " + countSum);

        return countSum ;
    }



}
