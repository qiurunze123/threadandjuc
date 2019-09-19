package com.geekagain.waitnotify;



/**
 * @author 邱润泽 bullock
 */
public class NotifyAndNotifyAll  implements Runnable{

    public static Object object = new Object();

    @Override
    public void run() {

        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + "get  lock ....");

            try {
                System.out.println(Thread.currentThread().getName() + "wait to start .");
                object.wait();
                System.out.println(Thread.currentThread().getName() + "wait to end.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NotifyAndNotifyAll notifyAndNotifyAll = new NotifyAndNotifyAll();
        Thread A = new Thread(notifyAndNotifyAll);
        Thread B = new Thread(notifyAndNotifyAll);
        Thread C = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    object.notifyAll();
                    System.out.println("thread c notify over");
                }
            }
        });

        A.start();
        B.start();
        Thread.sleep(100);
        C.start();

    }


}
