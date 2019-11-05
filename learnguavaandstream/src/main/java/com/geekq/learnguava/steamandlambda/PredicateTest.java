package com.geekq.learnguava.steamandlambda;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 邱润泽 bullock
 *
 * Predicate 最主要的就是提供了一个test 接口 主要用于过滤一类的
 */
public class PredicateTest {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        //输出大于等于6的数
        Predicate<Integer> predicate =  integer -> integer>=6;
        PredicateTest predicateTest = new PredicateTest();
        List<Integer> result = predicateTest.conditionFilter(list,predicate);
        result.forEach((Integer i)-> System.out.println(i));

        System.out.println("-------------------------------------------------");

        //输出全部数字 true 条件意味着 打印集合中所有的元素
        result = predicateTest.conditionFilter(list,integer -> true);
        result.forEach(System.out::println);

        System.out.println("---------------------------------------------------");


        Predicate<Integer> predicate1 = integer -> integer>2;
        Predicate<Integer> predicate2 = integer -> integer<5;
        List<Integer> listAnd = predicateTest.conditionFilterAnd(list,predicate1,predicate2);
        listAnd.forEach((Integer i ) -> System.out.println(i));

        System.out.println("-----------------------------------------------------");
        Predicate<Integer> predicate3 = integer -> integer>2;
        Predicate<Integer> predicate4 = integer -> integer<5;
        List<Integer> listOr = predicateTest.conditionFilterOr(list,predicate3,predicate4);
        listOr.forEach((Integer i ) -> System.out.println(i));

        System.out.println("--------------------------------------------------------");

        Predicate<Integer> predicate5 = integer -> integer>8;
        List<Integer> listNega = predicateTest.conditionFilterNegate(list,predicate5);
        listNega.forEach((Integer i ) -> System.out.println(i));


        System.out.println("----------------------------------------------------------");

        System.out.println(Predicate.isEqual("test").equals("test"));

    }

    //条件过滤
    public  List<Integer> conditionFilter( List<Integer> list ,
                                           Predicate<Integer> predicate){
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    //negate 讲条件取反
    public List<Integer> conditionFilterNegate(List<Integer> list , Predicate<Integer> predicate){
        return  list.stream().filter(predicate.negate()).collect(Collectors.toList());
    }

    //and 将传入的条件和当前条件已并且的关系过滤数据
    public List<Integer> conditionFilterAnd(List<Integer> list , Predicate<Integer> predicate,Predicate<Integer> predicate1){
        return list.stream().filter(predicate.and(predicate1)).collect(Collectors.toList());
    }

    //or方法同样接受一个predicate 类型 将传入条件和当前条件以或者的关系过滤条件
    public List<Integer> conditionFilterOr(List<Integer> list , Predicate<Integer> predicate,Predicate<Integer> predicate1){
        return list.stream().filter(predicate.or(predicate1)).collect(Collectors.toList());
    }
}
