package com.geekq.highimporttry;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.timer.*;
import com.geekq.highimporttry.util.Constant;
import org.apache.ibatis.type.Alias;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class HighImportTryApplicationTests {

    @Autowired
    private HighImportDataService highImportDataService ;

    @Autowired
    private TimerRunner timerRunner ;

    @Autowired
    private TimerRunner2 timerRunner2 ;

    @Autowired
    private TimerRunner3 timerRunner3;

    @Autowired
    private TimerRunner5 timerRunner5;
    @Autowired
    private PointDao pointDao;

//    @Autowired
//    private TimerRunner4ForkJoin timerRunner4ForkJoin ;

    @Autowired
    private TimerRunnerFutureTask timerRunnerFutureTask;

    @Autowired
    private BlockingRunTask blockingRunTask;

    /**
     * 导入 task 和 step 表
     */
    @Test
    public void contextLoads() {

        highImportDataService.recordHandle("20190415");

        try {
            Thread.currentThread().sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void blockingRunTask(){
        blockingRunTask.dealOrderTask();
    }
    /**
     *  import ——  point
     * @throws InterruptedException
     */
    @Test
    public void importTask() throws InterruptedException {

        highImportDataService.recordHandleImport("20190415");

        Thread.currentThread().sleep(50000);
    }
    @Test
    public void joda() throws InterruptedException {

        DateTime dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
        System.out.println(dateTime.plusDays(90).toString("E MM/dd/yyyy HH:mm:ss.SSS"));

        DateTime dt1 = new DateTime();
        System.out.println(dt1);

    }


    /**
     *  import ——  point
     * @throws InterruptedException
     */
    @Test
    public void importAll() throws InterruptedException {

        timerRunner.timeGo();

        Thread.currentThread().sleep(50000);
    }


    /**
     *  import ——  point
     * @throws InterruptedException
     */
    @Test
    public void importAll2() throws InterruptedException {

        timerRunner2.timeGo();

        Thread.currentThread().sleep(50000);
    }


    @Test
    public void importAll3() throws InterruptedException {

        timerRunner3.timeGo();

        Thread.currentThread().sleep(50000);
    }

    @Test
    public void importAllfork() throws InterruptedException {

//        TimerRunner4ForkJoin timerRunner4ForkJoin = new TimerRunner4ForkJoin()

//        Thread.currentThread().sleep(50000);
    }

    @Test
    public void importFuture() throws InterruptedException {

        timerRunnerFutureTask.timeGo();
    }

    @Test
    public void getImportFuture() throws InterruptedException {

        timerRunner5.timeGo();
        Thread.sleep(5000);
    }

    private int threadCount = 5; //子线程数

    private CountDownLatch countDownLatch = new CountDownLatch(threadCount) ;

    /**  select count(*) from point;
     select count(*) from  import_point_201908 ;
     delete from import_point_201908 ;
     delete from point ;
     delete from import_data_step ;
     delete from import_data_task ;
     * 共 1000000 万 数据
     * @throws InterruptedException
     */
    @Test
    public void createMillionData() throws InterruptedException {

        ExecutorService executorService  = Executors.newFixedThreadPool(5);
        for (int i = 1; i < 6; i++) {
             executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Point> points = new ArrayList<>();
                        for (int i = 1; i <100000 ; i++) {
                            Point point =new Point();
                            point.setUser(i);
                            point.setAvailablePoints(new BigDecimal(100000));
                            point.setDelayUpdateMode(i);
                            point.setFrozenPoints(new BigDecimal(100000));
                            point.setLastUpdateTime(new Date());
                            point.setLatestPointLogId(i);
                            point.setVersion(0);
                            points.add(point);
                        }
                        pointDao.insertBatch(points,new Date());
                    }finally {
                        countDownLatch.countDown();
                    }

                }
            });
        }
        System.out.println("== 处理中 == ");
        countDownLatch.await();
        System.out.println("== 插入结束 == ");
    }

}
