package com.apollo.threadinterview;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author 邱润泽 bullock
 */
@Slf4j
public class ThreadPrint {

    public static void main(String[] args) {
        PrintALIUseSemaphore print = new PrintALIUseSemaphore();

        Runnable r1 =  new Runnable() {
              @Override
              public void run() {
                  print.printA();
              }
         };

        Runnable r2 =  new Runnable() {
            @Override
            public void run() {
                print.printB();
            }
        };

        Runnable r3 =  new Runnable() {
            @Override
            public void run() {
                print.printC();
            }
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("高可用改造专用线程池-%d").build());

        for (int i = 0; i <10 ; i++) {
            executor.execute(r1);
            executor.execute(r2);
            executor.execute(r3);
        }

        try {
        } catch (Exception e) {
            System.out.println("多线程发生错误e"+e);
        }
        executor.shutdown();
        try {
            while (!executor.isTerminated()) {
                if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                } else {
                    System.out.println("完成");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("线程池异常");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
            executor.shutdownNow();
        }
        System.out.println("*********结束********");
    }
}

class PrintALIUseSemaphore {
    private Semaphore semaphoreA = new Semaphore(1);
    private Semaphore semaphoreB = new Semaphore(0);
    private Semaphore semaphoreC = new Semaphore(0);
    //private int attempts = 0;


    public void printA() {
        print("A", semaphoreA, semaphoreB);
    }

    public void printB() {
        print("L", semaphoreB, semaphoreC);
    }

    public void printC() {
        print("I", semaphoreC, semaphoreA);
    }

    private void print(String name, Semaphore currentSemaphore, Semaphore nextSemaphore) {
        for (int i = 0; i < 2; ) {
            try {
                currentSemaphore.acquire();
                System.out.println("我来打印：：第二道题 "+name);
                i++;
                nextSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
