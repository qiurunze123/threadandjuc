package com.geekagain.threadproperty;

/**
 * @author 邱润泽 bullock
 */
public class ThreadProperty2 extends Thread {

    public ThreadProperty2(String name){
        super(name);//把线程名称传递给父类,让父类(Thread)给子线程起一个名字
    }

    @Override
    public void run() {
        //获取线程的名称
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        ThreadProperty2 threadProperty2 =  new ThreadProperty2("我是传给父类的 来设置姓名");

        threadProperty2.start();
    }
}
