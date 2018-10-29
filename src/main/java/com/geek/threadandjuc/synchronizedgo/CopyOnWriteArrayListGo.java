package com.geek.threadandjuc.synchronizedgo;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 建议在高并发的情况下使用
 */
public class CopyOnWriteArrayListGo {

    private static volatile int count = 0;

    public static void testCOWArrayList() throws Exception{
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list);
        list.add(1); //无论元素是否已经存在都添加
        System.out.println(list);
        //如果元素不存在则不用添加,CopyOnWriteArraySet就是利用此函数实现的
        //英文：Absent adj.	缺席的，不在场的; 缺少的，缺乏的; 不在意的，茫然的;
        list.addIfAbsent(2);
        System.out.println(list);
    }

    public static void testCOWArraySet() throws Exception{
        final CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<Integer>();
        set.add(2);
        set.add(3);
        set.add(1);
        set.add(4);
        System.out.println(set);
        set.add(2);
        set.add(3);
        set.add(4);
        System.out.println(set);
    }

    public static void main(String[] args) throws Exception {
//		testCOWArrayList();
//		testCOWArraySet();
    }
}
