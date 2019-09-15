package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock 在catch 子语句中调用Thread.currentThread.interrupt()来恢复设置中断状态
 * 以便于在后续得执行中依然能够检查到刚才发生了中断
 */
public class RigihtWayStopThreadInProdTwo implements Runnable {
    @Override
    public void run() {
        while(true){
            if(Thread.currentThread().isInterrupted()){
                System.out.println("程序中断运行结束");
                System.out.println("lets go");
                break;
            }

                reInterrupt();
        }
    }

    private void reInterrupt(){
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
       Thread thread =  new Thread(new RigihtWayStopThreadInProdTwo());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
