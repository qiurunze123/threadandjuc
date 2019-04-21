package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 邱润泽 bullock
 */
public class BillRecordPointInsertTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BillRecordPointInsertTask.class);

    private ImportDataTaskDao importDataTaskDao;
    private ImportDataStepDao importDataStepDao;
    private String day;
    private HighImportDataService  highImportDataService;

    public BillRecordPointInsertTask(ImportDataTaskDao importDataTaskDao,
                                     ImportDataStepDao importDataStepDao,
                                     String day,HighImportDataService  highImportDataService) {
        this.importDataStepDao = importDataStepDao;
        this.importDataTaskDao = importDataTaskDao;
        this.day = day;
        this.highImportDataService = highImportDataService;
    }


    @Override
    public void run() {
        logger.info("start ------>>  账单 point 批量插入处理！");
        List<ImportDataTask> importDataTasks = importDataTaskDao.queryTaskDatas(Constant.IMPORTTYPE.point.name(), day);
        //开始时间
        long start = System.currentTimeMillis();
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
                logger.info("point handletime  重试!,{}", handleTime);
                highImportDataService.insertPointTaskStep(steps);
                handleTime++;
            }
        }
        long end = System.currentTimeMillis();
        logger.info("end ------>>  账单 point 批量插入处理结束！time :{}", (end - start) + "ms");
    }


}