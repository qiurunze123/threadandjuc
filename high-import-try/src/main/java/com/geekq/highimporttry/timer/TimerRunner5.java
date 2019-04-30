package com.geekq.highimporttry.timer;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.util.Constant;
import com.geekq.highimporttry.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 邱润泽 bullock
 *
 * 利用 future  task 来 多线程查询 需要的数据
 *
 * 数据分批次
 */
@Service
public class TimerRunner5 {

    public static final Logger logger = LoggerFactory.getLogger(TimerRunner5.class);
    @Autowired
    private HighImportDataService highImportDataService;

    @Autowired
    private ImportDataTaskDao importDataTaskDao;

    @Autowired
    private ImportDataStepDao importDataStepDao;

    private static final int IMPORT_TASK_AND_STEP_RETRY_TIME = 5;



    /**
     * 跑任务
     */
    public void timeGo() {


        logger.info("billNewRunnerImport start 导入step  and task 数据开始 2 ！");
        long start = System.currentTimeMillis();
        Date date = new Date();
        String day = DateUtil.format(date, DateUtil.DATE_FORMAT_DAY_SHORT);
        List<ImportDataTask> importDataTasks = importDataTaskDao.queryTaskDatas(Constant.IMPORTTYPE.point.name(), day);
        logger.info("每天需要处理 importDataTasks size : {}", importDataTasks.size());

        for (ImportDataTask task:importDataTasks) {
            List<ImportDataStep> steps = importDataStepDao.queryAllStepNotDealAndFail(task.getId(),
                    day, Constant.IMPORTTYPE.point.name());
            highImportDataService.recordHandlePoints(steps);
        }
        long end = System.currentTimeMillis();
        logger.info("导入生成全部 task .step import_x_x_x 需要时间 ！time  = {}", end - start);
    }


}
