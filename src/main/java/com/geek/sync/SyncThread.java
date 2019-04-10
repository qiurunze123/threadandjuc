package com.geek.sync;

/**
 * @author 邱润泽 bullock
 *
 * 修饰代码块
 *
 * 在执行的时候只有一个线程可以获取执行权，因为new出一个对象在执行
 * synchronized代码块的时候会锁定当前对象，执行完代码块才会释放
 */
public class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    @Override
    public  void run() {
        /**
         * 同一时刻只有一个线程执行 其他线程受阻
         */
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        SyncThread syncThread = new SyncThread();
        Thread thread1 = new Thread(syncThread, "SyncThread1");
        Thread thread2 = new Thread(syncThread, "SyncThread2");
        thread1.start();
        thread2.start();
    }
}
