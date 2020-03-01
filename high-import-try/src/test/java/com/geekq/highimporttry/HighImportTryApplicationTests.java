package com.geekq.highimporttry;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.logic.ImportDataStepLogic;
import com.geekq.highimporttry.logic.ImportDataTaskLogic;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.timer.*;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.geekq.highimporttry.util.Constant.BILL_2;

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

    @Autowired
    private ImportDataTaskLogic importDataTaskLogic;

    @Autowired
    private ImportDataStepLogic stepLogic;

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

        highImportDataService.recordHandle("20200224");

        try {
            Thread.currentThread().sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testHintF() {

        HintManager hintManager2 = HintManager.getInstance();
        hintManager2.setDatabaseShardingValue(1);
        Integer result = stepLogic.isExistTodayTable(BILL_2,"import_point_" +
                "20200301");
        hintManager2.close();
        try {
            Thread.currentThread().sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void streamCode(){
        int[] nums = { 1, 2, 3 };
        // 外部迭代
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        System.out.println("结果为：" + sum);

        // 使用stream的内部迭代
        // map就是中间操作（返回stream的操作）
        // sum就是终止操作
        IntStream.of(nums).parallel();

    }

    @Test
    public void testCreateStream() {
        //利用Stream.of方法创建流
        Stream<String> stream = Stream.of("hello", "world", "Java8");
        stream.forEach(System.out::println);
        System.out.println("##################");
        //利用Stream.iterate方法创建流
        Stream.iterate(10, n -> n + 1)
                .limit(5)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println("##################");
        //利用Stream.generate方法创建流
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
        System.out.println("##################");
        //从现有的集合中创建流
        List<String> strings = Arrays.asList("hello", "world", "Java8");
        String string = strings.stream().collect(Collectors.joining(","));
        System.out.println(string);
    }



    /*
     * @Author 欧阳思海
     * @Description 字符串与流之间的转换
     * @Date 9:41 2019/9/2
     * @Param []
     * @return void
     **/
    @Test
    public void testString2Stream() {
        String s = "hello world Java8".codePoints()//转换成流
                .collect(StringBuffer::new,
                        StringBuffer::appendCodePoint,
                        StringBuffer::append)//将流转换为字符串
                .toString();

        String s1 = "hello world Java8".chars()//转换成流
                .collect(StringBuffer::new,
                        StringBuffer::appendCodePoint,
                        StringBuffer::append)//将流转换为字符串
                .toString();

        System.out.println(s+"=============="+s1);
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
