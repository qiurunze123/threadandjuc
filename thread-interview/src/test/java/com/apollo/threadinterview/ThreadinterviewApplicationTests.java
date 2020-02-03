package com.apollo.threadinterview;

import com.apollo.threadinterview.threadbase.RunnableStyle;
import com.apollo.threadinterview.threadbase.ThreadStyle;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONAwareEx;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
class ThreadinterviewApplicationTests {

    @Test
    public void testThread(){
        log.warn("打印测试");
    }


    /**
     * 实现多线程的方式 是1中还是2种还是4种
     * 1.实现runnable 接口
     * 2.继承Thread类
     *  https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html
     *
     *     @Override 746
     *     public void run() {
     *         if (target != null) {
     *             target.run();
     *         }
     *     }
     *
     *     本质上 一个是继承thread类复写run方法 一个是实现runnable传入thread类 本质上没有什么区别都是最终调用
     *     start方法来创建线程不同的是run的来源 一个是target.run 一个是run()整个被重写
     *
     *     类似于什么线程池 定时器 lambda表达式等等都不算是创建线程的方式  本质上都是底层
     *
     */
    @Test
    public void implThreadTest() {
        RunnableStyle runnableStyle =new RunnableStyle();
        Thread thread = new Thread(runnableStyle);
        thread.start();
    }

    @Test
    public void implThreadTes1t() {
    }

    @Test
    public void threadTest() {
       new ThreadStyle().start();
    }

    /**
     * 结果是什么 ？？
     */
    @Test
    public void bothRunnableThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("你好吗我是runnable");
            }
        }){
            @Override
            public void run() {
                System.out.println("我是thread方法");
            }
        }.start();
    }

    /**
     * 为什么线程池不是 创建线程的方式 DefaultThreadFactory 596
     * @throws InterruptedException
     */
    @Test
    public void ExecutorServiceTest() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Task() {
            });
        }
        Thread.sleep(500000);

    }


    class Task implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
    //-=============================================================================================
}
