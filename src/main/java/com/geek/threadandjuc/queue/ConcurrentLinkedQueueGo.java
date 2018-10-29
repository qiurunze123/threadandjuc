package com.geek.threadandjuc.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *  * 无阻赛、无锁、高性能、无界队列(直至内存耗尽)、线程安全，性能优于BlockingQueue、不允许null值
 *  * 使用CAS算法进行入队和出队操作
 *
 *   它是一个基于链接节点的无界线程安全队列。该队列的元素遵循先进先出的原则。头是最先加入的，
 *   尾是最近加入的。插入元素是追加到尾上。提取一个元素是从头提取。
 *   当多个线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。
 *   该队列不允许null元素。此实现采用了有效的“无等待 (wait-free)”算法，
 *
 *   单生产者，单消费者  用 LinkedBlockingqueue
 * 多生产者，单消费者   用 LinkedBlockingqueue
 * 单生产者 ，多消费者   用 ConcurrentLinkedQueue
 * 多生产者 ，多消费者   用 ConcurrentLinkedQueue
 *
 * 看了下ConcurrentLinkedQueue的API 原来.size() 是要遍历一遍集合的，所以尽量要避免用size而改用isEmpty().
 */
public class ConcurrentLinkedQueueGo {

    public static void main(String[] args) {

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

        queue.add(1);
        queue.add(2);//add方法实际调用了offer方法

        queue.offer(3);
        queue.offer(4);

        System.out.println(queue);

        //英文：peek 偷看，窥视; 一瞥，看一眼
        System.out.println("[1]peek="+queue.peek()); //读取头元素,但是不移除
        System.out.println("[2]size="+queue.size()); //peek方法不会导致size改变

        //英文: poll 得到（一定数目的）选票; 对…进行调查; 修剪; 修剪;
        System.out.println("[3]poll="+queue.poll()); //读取头元素，并且移除
        System.out.println("[4]size="+queue.size()); //poll方法导致size改变

        System.out.println("[5]poll="+queue.poll());
        System.out.println("[6]poll="+queue.poll());
        System.out.println("[7]poll="+queue.poll());
        System.out.println("[8]size="+queue.size());

        System.out.println("peek="+queue.peek()); //队列为空, 读取头元素，返回null
        System.out.println("pool="+queue.poll()); //队列为空, 读取头元素并移除, 返回null

    }
}
