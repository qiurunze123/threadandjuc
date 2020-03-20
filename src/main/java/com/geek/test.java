package com.geek;

import java.math.BigDecimal;

/**
 * @author 邱润泽 bullock
 */
public class test {


    public static void main(String[] args) {
        System.out.println(new BigDecimal("" + -10000).subtract(new BigDecimal("" + 30)).doubleValue());
}
}
