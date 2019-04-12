package com.geek.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 证明 ABA问题
 * 如果只被允许充值一次 则 此方法有问题
 */
public class AtomicReference1 {
	static AtomicReference<Integer> money=new AtomicReference<Integer>();
	public static void main(String[] args) {
	    money.set(19);
	    for(int i = 0 ; i < 3 ; i++) {
            new Thread() {
                @Override
                public void run() {
                    while(true){
                        while(true){
                            Integer m=money.get();
                            if(m<20){
                                if(money.compareAndSet(m, m+20)){
                                    System.out.println("充值成功:"+money.get()+"");
                                    break;
                                }
                            }else{
                                break ;
                            }
                        }
                    }
                }
            }.start();
        }

	    new Thread() {
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    while(true){
                        Integer m=money.get();
                        if(m>10){
                            if(money.compareAndSet(m, m-10)){
                                System.out.println("消费十元 ，余额:"+money.get());
                                break;
                            }
                        }else{
                            System.out.println("gogo");
                            break;
                        }
                    }
                    try {Thread.sleep(100);} catch (InterruptedException e) {}
                }
            }
        }.start();
	}

}
