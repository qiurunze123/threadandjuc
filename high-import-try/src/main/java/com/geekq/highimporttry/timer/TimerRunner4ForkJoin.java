package com.geekq.highimporttry.timer;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.util.Constant;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class TimerRunner4ForkJoin extends RecursiveAction {

    public static final Logger logger = LoggerFactory.getLogger(TimerRunner4ForkJoin.class);
    @Autowired
    private HighImportDataService highImportDataService;

    @Autowired
    private ImportDataTaskDao importDataTaskDao;

    @Autowired
    private ImportDataStepDao importDataStepDao;

    private static final int IMPORT_TASK_AND_STEP_RETRY_TIME = 5;


    /**
     * ThreadFactoryBuilder是一个Builder设计模式的应用,可以设置守护进程、错误处理器、线程名字
     * <p>
     * guava
     */
    private ExecutorService executorService = new ThreadPoolExecutor(2, 2,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactoryBuilder().setNameFormat("高可用改造专用线程池-%d").build());


    public static final int THREAD_HOLD = 2000 ;

    private int start;
    private int end;
    private int id ;
    private String day ;

    public TimerRunner4ForkJoin(int start, int end , int id ) {
        this.start = start;
        this.end = end;
        this.id = id;
        this.day = day ;
    }
    /**
     * fork / join
     */

    @Override
    protected void compute() {
        List<ImportDataStep> steps = importDataStepDao.queryAllStepNotDealAndFail(id,
                day, Constant.IMPORTTYPE.point.name());
    }




}
