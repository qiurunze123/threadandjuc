package com.geekq.highimporttry;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.timer.TimerRunner;
import com.geekq.highimporttry.timer.TimerRunner2;
import com.geekq.highimporttry.timer.TimerRunner3;
import com.geekq.highimporttry.timer.TimerRunner4ForkJoin;
import com.geekq.highimporttry.util.Constant;
import org.apache.ibatis.type.Alias;
import org.checkerframework.checker.units.qual.A;
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
    private TimerRunner4ForkJoin timerRunner4ForkJoin ;
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
}
