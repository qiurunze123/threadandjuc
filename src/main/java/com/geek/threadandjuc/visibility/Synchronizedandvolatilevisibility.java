package com.geek.threadandjuc.visibility;

/**
 * java　多线程内存可见性
 */
public class Synchronizedandvolatilevisibility {
    //可见性：　一个线程对共享变量的修改，能够即使的被其他线程看到
    //共享变量：　如果一个变量在多个线程的工作内存中都存在副本，那么这个变量就是这几个线程的共享变量
    //ＪＭＭ：　1.所有的变量都存储在主内存中2.每个线程都有自己自己的独立工作内存，里面保存每个线程使用到的变量的副本（主内存中该变量的一份拷贝）
    //　1.线程对共享变量的所有操作都必须在自己的工作内存中进行，不能从主内存中读写　2.线程值传地需要主内存来完成
    //volatile　能够保证可见性　但是不能保证原子性　内存屏障和禁止指令重排序来优化实现可见性　number++
    //valitile 使用场景　1.对变量的写入依赖不依赖当前值　不满足　number＋＋

    private boolean ready = false ;
    private int result = 0;
    private int number = 1;
    public void write(){
        ready = true;
        number = 2;
    }

    public void read(){
        if(ready){
            result = number*3;
        }
        System.out.println("result的值为："+result);
    }

    private class ReadWriteThread extends Thread {
        private boolean flag ;//根据传入参数的不同判断执行读操作还是写操作
        public ReadWriteThread(boolean flag){
            this.flag = flag ;
        }
        @Override
        public void run(){
            if(flag){
                //true 为读操作
                write();
            }else {
                read();
            }
        }
    }

    public static void main(String[] args) {
        Synchronizedandvolatilevisibility sysDemo =  new Synchronizedandvolatilevisibility();
        //启动线程执行写操作
        sysDemo.new ReadWriteThread(true).start();
        //可以添加休眠操作
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        sysDemo.new ReadWriteThread(false).start();
    }


}
