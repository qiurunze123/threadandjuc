package com.geekagain.threadsafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 邱润泽 bullock 逸出问题
 *
 * 在构造函数中直接新建线程
 * 可能没有执行完线程 就 构造完了函数
 */
public class ThreadSafeError9 {
    private Map<String, String> states;

    public ThreadSafeError9() {
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
        ThreadSafeError9 multiThreadsError9 = new ThreadSafeError9();
        Thread.sleep(1000);
        System.out.println(multiThreadsError9.getStates().get("1"));
    }
}
