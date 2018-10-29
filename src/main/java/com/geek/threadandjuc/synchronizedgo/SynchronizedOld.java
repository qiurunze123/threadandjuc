package com.geek.threadandjuc.synchronizedgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedOld {
    public static void main(String[] args) throws Exception {
        /*
         * 使用以下方法被包裹的类将支持多线程
         * Collections.synchronizedCollection(c);
         * Collections.synchronizedList(list);
         * Collections.synchronizedMap(m);
         * Collections.synchronizedSet(s);
         * Collections.synchronizedSortedMap(m);
         * Collections.synchronizedSortedSet(s);
         */
        // 非线程安全的List
//        final List<String> list = new ArrayList<String>(1);
        // 线程安全的List
        final List<String> list = Collections.synchronizedList(new ArrayList<String>());
        ExecutorService es = Executors.newFixedThreadPool(100);

        //向list中并发加入1万个元素,如果是线程安全的那么list.size=1万,否则!=1万
        for (int i = 0; i < 10000; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    list.add("5");
                }
            });
        }

        es.shutdown();

        while (true) {
            if (es.isTerminated()) {
                System.out.println("所有的子线程都结束了！");
                System.out.println(list.size());
                if(list.size()!=10000){
                    System.out.println("线程不安全！");
                }else{
                    System.out.println("线程安全!");
                }
                break;
            }
        }
    }
}
