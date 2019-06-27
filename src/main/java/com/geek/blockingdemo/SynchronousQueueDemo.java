package com.geek.blockingdemo;

import java.util.concurrent.SynchronousQueue;

/**
 * @author 邱润泽 bullock
 *
 * 当第一个元素没有消费完成时 第二个元素会一直阻塞
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        SynchronousQueue<String> blockingDeque = new SynchronousQueue<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("put  1 ");
                    blockingDeque.put("1");

                    System.out.println("put  2 ");
                    blockingDeque.put("2");

                    System.out.println("put  3");
                    blockingDeque.put("3");

                    System.out.println("put  4 ");
                    blockingDeque.put("4");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AAA").start();

         new Thread(new Runnable() {
              @Override
              public void run() {
                  try {
                      System.out.println("take  1 ");
                      System.out.println(blockingDeque.take()+"=====");
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
         },"BBB").start();
    }
}
