package com.geekq.learnguava.steamandlambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/3
 *
 *  * <p>Note that instances of functional interfaces can be created with
 *  * lambda expressions, method references, or constructor references.
 */
public class Test1 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
//
//        for (int i=0;i<list.size();i++){
//            System.out.println(list.get(i));
//        }
//        System.out.println("------------------------");
//
//        for (Integer i :list){
//            System.out.println(i);
//        }
//        System.out.println("-------------------------");
        //Consumer<? super T> action 表示要么是integer 要么是integer的父类
        //@FunctionalInterface 注解意思 函数式接口 可以点进去看看 这个接口的注释
        // * <p>Note that instances of functional interfaces can be created with
        // * lambda expressions, method references, or constructor references.
        //public interface Consumer<T>


        /**
         * 关于函数式接口
         * 1.如果一个接口只有一个抽象方法那么改接口就是一个函数式接口
         * 2.如果我们在某个接口上声明functionInterface那么编译器就会按照函数式的接口定义来要求接口
         * 3.如果某个接口只有一个抽象方法但我们并没有给该接口声明functionInterface注解那么编译器依旧会将该接口看作是函数式接口
         */


        /**
         * for each  跟进去看是 ITerable的默认方法 在jdk1.8 之后再接口中可以实现默认方法
         *
         * 这样继承这个接口的所有方法都有了这个方法的实现
         */
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });


        list.forEach((Integer i)->{
            System.out.println(i);
        });
        System.out.println("-------------------------");

        //通过方法引用 第二种创建方法   method references
        list.forEach(System.out::println);
    }
}
