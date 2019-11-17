package com.apollo.threadinterview.threadsafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiurunze
 * @date 2019/11/17
 * 描述：     构造函数中新建线程
 * 在构造函数中新开线程 等待一下时间后可正常运行
 *
 * 可能大家觉得不会再构造函数中开线程  但是我们有些情况会不自觉的这样做 比如在构造函数中拿到一个线程池的引用或者说
 * 数据库的连接池 而我们创建 这个连接池的链接的时候 他往往都是在后台新开线程的 我们根本察觉不到 因为我们是调用他的构造函数
 * 比如我们调用一个数据库链接的构造函数 （他也会需要多线程加快自己的速度）
 */
public class ThreadsError6 {

    private Map<String, String> states;

    public ThreadsError6() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1", "周一");
                states.put("2", "周二");
                states.put("3", "周三");
                states.put("4", "周四");
            }
        }).start();
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadsError6 threadsError6 = new ThreadsError6();
//        Thread.sleep(1000);
        System.out.println(threadsError6.getStates().get("1"));
    }
}
