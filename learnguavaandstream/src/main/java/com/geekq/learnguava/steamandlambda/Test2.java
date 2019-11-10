package com.geekq.learnguava.steamandlambda;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/3
 * <p>
 * 这个抽象方法复习了根类的某个public 方法  object 类 那么就不会把这个抽象接口的个数加+1
 *
 * 再将函数作为一等公民看待的语言中 lamdba表达式的类型是函数 但是在java 中 lamdba表达式是对象
 *
 * 他们必须依赖于一类特别的对象类型 -- 函数式接口
 */
@FunctionalInterface
interface MyInterface {

    void test();

    String toString();

}


public class Test2 {

    public void myTest(MyInterface myInterface){
        System.out.println(1);
        myInterface.test();
        System.out.println(2);
    }

    public static void main(String[] args) {

        Test2 test = new Test2();

        test.myTest(new MyInterface() {
            @Override
            public void test() {
                System.out.println("mytest");
            }
        });


        test.myTest(()->{
            System.out.println("mytest");
        });

        System.out.println("------------------------");

        /*
         * 赋给了一个对象引用
         */
        MyInterface myInterface = ()->{
            System.out.println("hello");
        };

        System.out.println(myInterface.getClass());

        System.out.println(myInterface.getClass().getSuperclass());

        System.out.println(myInterface.getClass().getInterfaces()[0]);

        System.out.println("------------------------");




    }
}