package com.geekq.learnguava.steamandlambda;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author 邱润泽 bullock
 */
public class BiFunctionTest {

    //因为BiFunction的apply方法接收两个参数，但是任何一个方法不可能有两个返回值
    // 所以也没办法放在BiFunction前面执行
    // 这也是为什么BiFunction没有compose方法的原
    //执行逻辑 先执行Bifunction 然后将结果传给function 在最后计算返回结果
    public static void main(String[] args) {

        BiFunctionTest biFunctionTest = new BiFunctionTest();

        System.out.println(biFunctionTest.compute(4,5,(a,b)->a*b,a->a*2));

    }

    public int compute(int a , int b , BiFunction<Integer,Integer,Integer> biFunction,
                       Function<Integer,Integer> function){
        return biFunction.andThen(function).apply(a,b);
    }
}
