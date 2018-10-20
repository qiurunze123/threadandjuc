package com.geek.threadandjuc.juc.exchanger;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.concurrent.Exchanger;

public class ExchangerTest1 {


    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<String>();

        ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger, "A");
        ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger, "B");


        Exchanger<String> exchanger2 = new Exchanger<String>();

        ExchangerRunnable exchangerRunnable3 = new ExchangerRunnable(exchanger2, "C");
        ExchangerRunnable exchangerRunnable4 = new ExchangerRunnable(exchanger2, "D");

        new Thread(exchangerRunnable1).start();
        new Thread(exchangerRunnable2).start();
       /* new Thread(exchangerRunnable3).start();
        new Thread(exchangerRunnable4).start();
*/

    }
}

    class ExchangerRunnable implements Runnable{
        Exchanger<String> exchanger =null;
        String object =null;

        public ExchangerRunnable(Exchanger<String> exchanger, String object) {
            this.exchanger = exchanger;
            this.object = object;
        }
        @Override
        public void run() {
            try {
                Object previous = this.object;
                if ("C".equals(previous)) {
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "对数据c的耗时花费一秒");
                } else if ("D".equals(previous)) {
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getName() + "对数据d的耗时花费2秒");
                } else if ("A".equals(previous)) {
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getName() + "对数据a的耗时花费2秒");
                } else if ("B".equals(previous)) {
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getName() + "对数据b的耗时花费2秒");
                }



                this.object = this.exchanger.exchange(this.object);
                System.out.println(Thread.currentThread().getName() + "exchanges" + previous+"for"+this.object);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
