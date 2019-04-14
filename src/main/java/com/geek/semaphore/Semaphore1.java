package com.geek.semaphore;

import com.geek.threadandjuc.base.SemapDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphore1 implements  Runnable {
    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Semaphore1 semaphore1=new Semaphore1();
        Thread[] thread = new Thread[20];
        for (int i=0 ; i<20 ; i++){
            thread[i] = new Thread(semaphore1);
        }
        for (int i=0 ; i<10 ; i++){
            thread[i].start();
        }

//        ExecutorService exec = Executors.newFixedThreadPool(20);
//        final Semaphore1 semaphore1=new Semaphore1();
//        for(int i=0;i<20;i++){
//            exec.submit(semaphore1);
//        }
    }
}
