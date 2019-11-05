package com.geekq.learnguava.steamandlambda;

import javax.swing.*;
import java.util.function.Function;

/**
 * @author 邱润泽 bullock
 */
public class LambdaComposeAndThen {

    //function接口中 除了 apply  还有 俩个内置的方法
    //function 只能接受一个参数 bifunction 能接受俩个参数
    public static void main(String[] args) {

        LambdaComposeAndThen lambdaComposeAndThen = new LambdaComposeAndThen();

        System.out.println(lambdaComposeAndThen.compute1(5,i->i*2,i->i*i));
        System.out.println(lambdaComposeAndThen.compute2(5,i->i*2,i->i*i));

    }


    public int compute1(int i , Function<Integer,Integer> after,Function<Integer,Integer> before){
        return after.compose(before).apply(i);
    }


    public int compute2(int i , Function<Integer,Integer> before,Function<Integer,Integer> after){
        return before.andThen(after).apply(i);
    }




}
