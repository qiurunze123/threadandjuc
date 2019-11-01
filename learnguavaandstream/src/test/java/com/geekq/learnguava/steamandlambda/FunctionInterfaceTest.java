package com.geekq.learnguava.steamandlambda;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 * <p>
 * 只要是一个接口中只包含一个方法 则可以使用lambda表达式 这样的接口成为函数接口
 * 此方法既不包含参数也不包含返回值
 */
public class FunctionInterfaceTest {

    @Test
    public void testLambda() {
        func(new FunctionInterface() {
            @Override
            public void test() {
                System.out.println("第一个测试lambda表达式");
            }
        });

        func(() -> System.out.println("第一个lambda表达式"));
    }

    private void func(FunctionInterface functionInterface) {
        functionInterface.test();
    }


    @Test
    public void testLambdaParam() {
        func(new FunctionInterfaceParam() {
            @Override
            public void test(int param) {
                System.out.println("带参数的lambda表达式");
            }
        });

        func((int x) -> System.out.println("带参数的lambda表达式"));

    }

    private void func(FunctionInterfaceParam functionInterfaceParam) {
        int x = 1;
        functionInterfaceParam.test(x);
    }


    @Test
    public void testLambdaT() {
        func((Integer x) -> {
            System.out.println("我是来测试的 不服不服");
            return true;
        });
    }


    private void func(FunctionInterfaceT<Integer> functionInterfaceT) {
        int x = 1;
        functionInterfaceT.test(x);
    }

    @Test
    public void testStream() {

        List<String> stringList = new ArrayList<>();

        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        List<String> count = stringList.stream().filter((String s) -> {
                    return Integer.valueOf(s) > 1;
                }
        ).collect(Collectors.toList());
        System.out.println(count);
    }

    @Test
    public void testStream2() {
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        stringList.stream().forEach((String s) -> System.out.println(s));
    }

    @Test
    public void testStream3() {
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        stringList.stream().forEach((String s) -> System.out.println(s));
    }

    /**
     * 终端操作——非短路操作：forEach
     * 对所有元素进行迭代处理，无返回值
     */
    @Test
    public void testStream4() {
        Arrays.asList(1,2,3,4,5).forEach((Integer i) -> System.out.println(i));
    }
    /**
     * 终端操作——非短路操作：reduce
     * 通过累加器对前面的序列进行累计操作，并最终返回一个值。累加器有两个参数，
     * 第一个是前一次累加的结果，第二个是前面集合的下一个元素。
     *  通过reduce可以实现avg sum max min count等操作
     */
    @Test
    public void testStream5() {
        List<String> stringList = Arrays.asList("S01", "S02", "S03");
        Optional<String> reduce = stringList.stream().reduce((x, y) -> {
            return x + "_" + y;
        });
        //打印：S01_S02_S03
        System.out.println(reduce.orElse("default"));
    }
}
