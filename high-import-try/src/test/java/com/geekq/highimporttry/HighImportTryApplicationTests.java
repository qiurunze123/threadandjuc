package com.geekq.highimporttry;

import com.geekq.highimporttry.entity.ImportDataStep;
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

import java.util.List;

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

//    @Autowired
//    private TimerRunner4ForkJoin timerRunner4ForkJoin ;

    @Autowired
    private TimerRunnerFutureTask timerRunnerFutureTask;

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
}
