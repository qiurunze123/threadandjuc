package com.geekagain.singletongo;

/**
 * @author 邱润泽 bullock
 */
public class StringThread implements Runnable {

    private static final String LOCK = "我和我的我祖国";

    private String name;

    public StringThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        String lock = buildLock();
        synchronized (lock) {
            System.out.println("[" + Thread.currentThread().getName() + "]开始运行了");
            // 休眠5秒模拟脚本调用
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + Thread.currentThread().getName() + "]结束运行了");
        }
    }

    private String buildLock() {
        StringBuilder sb = new StringBuilder();
        sb.append(LOCK);
        sb.append(name);
        String lock = sb.toString();
        System.out.println("[" + Thread.currentThread().getName() + "]构建了锁[" + lock + "]");
        return lock;
    }

    public static void main(String[] args) {

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new StringThread("搞起来"));
        }

        for (int i = 0; i < 5; i++) {
            threads[i].start();
        }

        for (;;) {
            ;
        }
    }

}