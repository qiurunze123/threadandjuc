package com.apollo.threadinterview.leetcodethreadsub.T05;

import org.springframework.context.annotation.Conditional;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱润泽 bullock
 */
public class BoundBlockingQueue {

    private volatile int size;
    private volatile int capacity;
    private Node head ;
    private Node tail;
    private final Lock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();

    //双向节点
    class Node{

        int val;
        Node next;
        Node prev;
        Node(int val){
            this.val = val;
        }
    }

    //双向链表
    public BoundBlockingQueue(int capacity){
        this.capacity = capacity;
        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            while (size == capacity) {
                notFull.await();
            }

            Node newNode = new Node(element);
            //新节点头插
            newNode.next = head.next;
            head.next.prev = newNode;
            newNode.prev = head;
            //唤醒出队方法
            notEmpty.signal();
            size++;
        }finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (size == 0) {
                //暂停出队方法
                notEmpty.await();
            }
            //尾部出队
            Node node = tail.prev;
            tail.prev = null;
            node.next = null;
            tail = node;
            size--;
            notFull.signal();//唤醒人队方法
            return node.val;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return size;
    }









}
