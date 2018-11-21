package com.geek.threadandjuc.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *sdf.parse 线程不安全 在线城池共享这个对象是错误的
 */
public class ThreadLocalFalse {

    private static  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable{

        int i=0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            Date t = null;
            try {
                t = sdf.parse("2015-03-29 19:29:"+i%60);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(i+":"+t);
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            es.submit(new ParseDate(i));
        }

    }
}
