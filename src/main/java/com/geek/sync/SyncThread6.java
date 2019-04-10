package com.geek.sync;

/**
 * @author 邱润泽 bullock
 *
 * 修饰一个静态方法
 *
 * 我们知道静态方法是属于类的而不属于对象的。同样的，synchronized修饰的静态方法锁定的是这个类的所有对象
 *
 *  syncThread1和syncThread2是SyncThread的两个对象，但在thread1和thread2并发执行时却保持了线程同步。
 *  这是因为run中调用了静态方法method，而静态方法是属于类的，
 *  所以syncThread1和syncThread2相当于用了同一把锁。这与【SyncThread】是不同的。
 */
public class SyncThread6 implements Runnable {

    private static int count;

    public SyncThread6() {
        count = 0;
    }

    public synchronized static void method() {
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void run() {
        method();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SyncThread6 syncThread1 = new SyncThread6();
        SyncThread6 syncThread2 = new SyncThread6();
        Thread thread1 = new Thread(syncThread1, "SyncThread1");
        Thread thread2 = new Thread(syncThread2, "SyncThread2");
        thread1.start();
        thread2.start();
    }

}
