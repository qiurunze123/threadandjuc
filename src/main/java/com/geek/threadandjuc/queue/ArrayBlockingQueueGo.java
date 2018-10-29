package com.geek.threadandjuc.queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue的实现类之ArrayBlockingQueue
 * 基于数组实现的阻塞有界队列、创建时可指定长度，内部实现维护了一个定长数组用于缓存数据,
 * 内部没有采用读写分离，add和poll数据不能同时进行，可以指定先进先出或后进先出。 使用ReentrantLock(重入锁)控制线程安全
 * ---------------------------------------------------
 *  offer 如果队列已经满了,则不阻塞，不抛出异常
 *  offer 可设置最大阻塞时间,2秒,如果队列还是满的,则不阻塞，不抛出异常
 *  add 如果队列满了，则不阻塞，直接抛出异常
 *  put 如果队列满了,则永远阻塞, 不抛出异常
 * ---------------------------------------------------
 * peek 读取头元素不移除，队列为空,返回null,不阻塞, 不抛异常
 * poll 读取头元素并移除，队列为空,返回null,不阻塞, 不抛异常
 * poll 可指定阻塞时间,2秒,如果队列依然为空,则返回null,不抛异常
 * take 读取头元素并移除,如果队列为空,则永远阻塞,不抛出异常
 * drainTo 取出queue中指定个数（或全部）的元素放入list中,并移除，当队列为空时不抛出异常
 */
public class ArrayBlockingQueueGo {
    // 测试各种添加元素的方法
    public static void testAdd() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        queue.add(1);
        queue.offer(2);
        try {
            queue.add(null); //不允许添加null元素
        } catch (Exception e1) {
            System.out.println("queue.add(null)异常"+e1.getMessage());
        }
        try {
            queue.put(3);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        try {
            queue.add(4); // 如果队列满了,则抛出异常
        } catch (Exception e1) {
            System.out.println("queue.add(4)异常"+e1.getMessage());
        }
        System.out.println("1>>" + queue);
        queue.offer(4); // 如果队列已经满了,则不阻塞，不抛出异常
        System.out.println("queue.offer(4)>>" + queue);
try {
        // 可设置最大阻塞时间,5秒,如果队列还是满的,则不阻塞，不抛出异常
        queue.offer(6, 5, TimeUnit.SECONDS);
        System.out.println("queue.offer(6, 5, TimeUnit.SECONDS)>>" + queue);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

		try {
        queue.put(7); // 如果队列满了,则永远阻塞, 不抛出异常
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
		System.out.println(">>" + queue);

}

    // 测试获取数据
    public static void testTake1() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(2);
        queue.add(1);
        queue.add(2);

        System.out.println("1>>" + queue.peek()); // 读取头元素不移除
        System.out.println(queue);

        System.out.println("2>>" + queue.poll()); // 读取头元素，并移除
        System.out.println("3>>" + queue);

        try {
            // 获取头元素,并移除数据
            System.out.println("4>>" + queue.take());
            System.out.println("5>>" + queue);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("6>>" + queue.peek()); // 队列为空,返回null,不阻塞, 不抛异常
        System.out.println("7>>" + queue.poll()); // 队列为空,返回null,不阻塞, 不抛异常

        try {
            // 可指定阻塞时间,2秒,如果队列依然为空,则返回null,不抛异常
            System.out.println("8>>" + queue.poll(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            queue.take(); // 如果队列为空,则永远阻塞,不抛出异常
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("9>>over");
    }

    // 测试获取数据2
    public static void testTake2() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        ArrayList<Integer> list = new ArrayList<Integer>();
        // 英文 drain 喝光，喝干; 使（精力、金钱等）耗尽; 使流出; 排掉水;
        queue.drainTo(list, 2); // 取出queue中指定个数的元素放入list中,并移除
        System.out.println(list);
        System.out.println(queue);
    }

    // 测试获取数据3
    public static void testTake3() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        ArrayList<Integer> list = new ArrayList<Integer>();
        queue.drainTo(list); // 取出queue中的全部元素放入list中,并移除
        System.out.println("1>>" + list);
        System.out.println("2>>" + queue);

        ArrayList<Integer> list1 = new ArrayList<Integer>();
        queue.drainTo(list1); // 当队列为空时不抛出异常
        System.out.println("3>>" + list1);
        System.out.println("4>>" + queue);
    }
    public static void main(String[] args) {
        testAdd();
    }

}
