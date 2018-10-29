package com.geek.threadandjuc.synchronizedgo;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;


class DemoDemo{
    public DemoDemo() {
    }
}
/**
 *  * 并发容器构建
 *  * ConcurrentHashMap -> 替代HashMap、Hashtable
 *  * ConcurrentSkipListMap -> 替代TreeMap
 *  * map.put 与 map.putIfAbsent 的区别
 */
public class SynchronizedNew {


    public static void testMap1(){
        final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String,Integer>();
//        final Hashtable<String,Integer> map = new Hashtable<String, Integer>();
        for (int i=0;i<=10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Long start = System.currentTimeMillis();

                    for (int i=0;i<1000000;i++){
                        map.put("a"+i,i);
                    }
                    System.out.println(System.currentTimeMillis()-start);
                }
            }).start();
        }
    }

    //对比concurrentSkiplistmap 与 SortedMap 的性能
    public static void testSkipListMap1() throws InterruptedException{

        final SortedMap<String,DemoDemo> skipMap = Collections.synchronizedSortedMap(new TreeMap<String, DemoDemo>());


        final CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i=0 ; i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Long start = System.currentTimeMillis();
                    Random rn = new Random();
                    for (int i=0;i<1000;i++){
                        try {
                            skipMap.put("k"+i%10, new DemoDemo());
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("添加100个元素耗时："+(System.currentTimeMillis()-start)+"毫秒");
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        //System.out.println(skipMap);
        System.out.println(skipMap.size());

    }


    public static void testMap2(){
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String,Integer>();
        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 1);
        map.put("d", 1);
        //如果key已经存在则更新
        map.put("a", 2);
        System.out.println(map);
        //如果key存在则不更新,不存在则添加
        map.putIfAbsent("b", 2);
        map.putIfAbsent("e", 3);
        System.out.println(map);
    }

    public static void testSkipListMap2(){
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<String, Integer>();
        map.put("a", 1);
        map.put("b1", 1);
        map.put("c", 1);
        map.put("d", 1);
        //如果key已经存在则更新
        map.put("a", 2);
        System.out.println(map);
        //如果key存在则不更新
        map.putIfAbsent("b", 2);
        System.out.println(map);
    }

    public static void main(String[] args) throws Exception {
		testMap1();
//		testMap2();
        //testSkipListMap1();
//        testSkipListMap2();
    }

}
