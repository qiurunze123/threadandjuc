package com.geek.lockthinkdemo;

import java.util.concurrent.TimeUnit;

/**
 * @author 邱润泽 bullock
 */

class Phone1{
    public  synchronized void sendSms() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\tsendSms");
        sendEmail();
    }
    public  synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\tsendEmail");
    }

}
public class ReentrantLockDemo {

    /**
     * Description:
     *  可重入锁(也叫做递归锁)
     *  指的是同一先生外层函数获得锁后,内层敌对函数任然能获取该锁的代码
     *  在同一线程外外层方法获取锁的时候,在进入内层方法会自动获取锁
     *  也就是说,线程可以进入任何一个它已经标记的锁所同步的代码块
     **/
        /**
         * t1 sendSms
         * t1 sendEmail
         * t2 sendSms
         * t2 sendEmail
         *
         * @param args
         */
        public static void main(String[] args) {
            Phone1 phone1 = new Phone1();
            Thread t1 = new Thread(() -> {
                try {
                    phone1.sendSms();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "t1");
            Thread t2 = new Thread(() -> {
                try {
                    phone1.sendSms();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "t2");


            t1.start();
            t2.start();

        }


}
