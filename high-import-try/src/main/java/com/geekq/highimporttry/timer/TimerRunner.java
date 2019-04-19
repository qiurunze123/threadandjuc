package com.geekq.highimporttry.timer;

import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 邱润泽 bullock
 */
@Service
public class TimerRunner {

    public static final Logger logger  = LoggerFactory.getLogger(TimerRunner.class);
    @Autowired
    private HighImportDataService highImportDataService;

    @Autowired
    private ImportDataTaskDao importDataTaskDao ;

    @Autowired
    private ImportDataStepDao importDataStepDao ;

    private static final int IMPORT_TASK_AND_STEP_RETRY_TIME = 5;

    /**
     * 跑任务
     */
    public void timeGo(){


        logger.info("billNewRunnerImport start 导入step  and task 数据开始！");
        long start = System.currentTimeMillis();
        Date date = new Date();
        String day = DateUtil.format(date, DateUtil.DATE_FORMAT_DAY_SHORT);
        /**
         * ======= 如果 今天的数据 存在 则无需重复导入 重试 5次 ====
         */
        boolean flagImport = false;
        int importTaskAndStepTime = 0;
        while (importTaskAndStepTime < IMPORT_TASK_AND_STEP_RETRY_TIME && !flagImport) {
            boolean todayIsTryImport = importDataTaskDao.queryTodayTaskIsImport(day) > 0;
            if (todayIsTryImport) {
                logger.info("今日{}数据已导入importdataTask 和 importdataStep 表 ", day);
            } else {
                highImportDataService.recordHandle(day);
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
                recordHandleImport = importDataStepDao.queryTodayStepIsImportSuccess(day) == 0;
                if (recordHandleImport) {
                    logger.info("import_x_x_x 该数据已导入完毕 ====!");
                } else {
                    highImportDataService.recordHandleImport(day);
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


}
