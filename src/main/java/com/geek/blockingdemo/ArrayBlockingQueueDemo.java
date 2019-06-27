package com.geek.blockingdemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 邱润泽 bullock
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);

        new Thread(new Runnable() {
              @Override
              public void run() {
                  try {
                      System.out.println("put  1 ") ;
                      blockingQueue.put("1");

                      System.out.println("put  2") ;
                      blockingQueue.put("2");

                      System.out.println("put  3 ") ;
                      blockingQueue.put("3");

                      System.out.println("put  4 ") ;
                      blockingQueue.put("4");

                      System.out.println("put  5 ") ;
                      blockingQueue.put("5");
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
         },"AAA").start();


          new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       System.out.println(blockingQueue.take());
                       System.out.println( blockingQueue.take());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
          },"BBB").start();
    }
}
