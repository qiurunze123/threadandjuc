package com.geek.priority;


public class PriorityDemo {
    public static class HightPriority extends Thread{
        public HightPriority(String a) {
            super(a);
        }
        @Override
        public void run(){
            for (int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
//    public static class LowPriority extends Thread{
//        static int count=0;
//        @Override
//        public void run(){
//            while(true){
//                synchronized(PriorityDemo.class){
//                    count++;
//                    System.out.println("LowPriority is complete"+count);
//                    if(count>10){
//                        System.out.println("LowPriority is complete");
//                        break;
//                    }
//                }
//            }
//        }
//    }
    
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread high=new HightPriority("A");
        Thread low=new HightPriority("B");

        low.setPriority(Thread.MIN_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);
        high.start();
        low.start();

    }
}
