package com.geekq.learnguava.steamandlambda;

import java.util.Optional;

/**
 * @author 邱润泽 bullock
 *
 * Optional 主要作用解决java中nullpointerException
 */
public class OptionTest {

    public static void main(String[] args) {

        String name ="geek";
        Optional<String> optional = Optional.ofNullable(name);

        optional.ifPresent(s-> System.out.println(s));

        System.out.println("------------------------------------------");

        String nameNull = null;
        String geek = Optional.ofNullable(nameNull).orElse("geek");
        System.out.println(geek);

    }
}
