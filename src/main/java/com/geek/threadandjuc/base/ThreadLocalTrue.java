package com.geek.threadandjuc.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *sdf.parse 线程不安全
 */
public class ThreadLocalTrue {

    private static  ThreadLocal<SimpleDateFormat> t1 = new  ThreadLocal<SimpleDateFormat>();

    public static class ParseDate implements Runnable{

        int i=0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if(t1.get()==null){
                t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            Date t = null;
            try {
                t = t1.get().parse("2015-03-29 19:29:"+i%60);
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
