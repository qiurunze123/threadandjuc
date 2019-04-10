package com.geek.WaitNotify;

/**
 * @author 邱润泽 bullock
 */
public class WaitNotify3 {

    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();
        ThreadA ta = new ThreadA(lock);
        Thread tta = new Thread(ta);

        tta.start();
        Thread.sleep(50);

        ThreadB tb = new ThreadB(lock);
        Thread ttb = new Thread(tb);
        ttb.start();

    }
}

class ThreadA implements Runnable{

    private Object mLock;

    public ThreadA(Object lock){
        mLock = lock;
    }

    @Override
    public void run() {
        synchronized (mLock) {
            if(MyList.getSize() != 5){
                try {
                    System.out.println("before wait");
                    mLock.wait();
                    System.out.println("after wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

class ThreadB implements Runnable{

    private Object mLock;

    public ThreadB(Object lock){
        mLock = lock;
    }

    @Override
    public void run() {
        synchronized (mLock) {
            for(int i = 0; i< 10; i++){
                MyList.add();
                if(MyList.getSize() == 5){
                    mLock.notify();
                    System.out.println("已发出notify通知");
                }
                System.out.println("增加"+(i+1)+"条数据");
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同步方法之外的方法");
    }

}
