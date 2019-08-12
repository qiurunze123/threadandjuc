package com.geek.sandl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱润泽 bullock
 */

class ShareRecourse{

    private int number = 1;
    private Lock lock = new ReentrantLock(true);
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printc1()  {
        lock.lock();
        try {
            while(number!=1){
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("A"+i);
            }
            number =2 ;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printc2()  {
        lock.lock();
        try {
            while(number!=2){
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("B"+i);
            }
            number =3 ;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printc3()  {
        lock.lock();
        try {
            while(number!=3){
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("C"+i);
            }
            number =1 ;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SynchronizedAndLock {


    public static void main(String[] args) {

        ShareRecourse shareRecourse =new ShareRecourse();
         new Thread(new Runnable() {
              @Override
              public void run() {
                  for (int i = 0; i < 10; i++) {
                      shareRecourse.printc1();
                  }
              }
         },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    shareRecourse.printc2();
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    shareRecourse.printc3();
                }
            }
        },"C").start();
    }

}
