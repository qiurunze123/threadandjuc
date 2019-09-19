package com.geekagain.threadcatch;

/**
 * @author 邱润泽 bullock
 */
public class ThreadCatchProcess3 implements Thread.UncaughtExceptionHandler {

    private String name;

    public ThreadCatchProcess3(String name) {
        this.name = name;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("线程异常终止进程" + t.getName());
        System.out.println(name + "捕获了异常" + t.getName() + "异常");

    }
}
