package com.geek.threadandjuc2;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 邱润泽 bullock
 */
public class ThreadGo1 {

    public static void main(String[] args) {
        Set<Integer> productIds = new HashSet<>();
        System.out.println(JSON.toJSONString(productIds));
    }
}
