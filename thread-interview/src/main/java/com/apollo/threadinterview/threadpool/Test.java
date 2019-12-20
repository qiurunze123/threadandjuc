package com.apollo.threadinterview.threadpool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 邱润泽 bullock
 */
public class Test {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();

        map.put(null,null);
        map.put(null,null);

        map.put("1","1");

        System.out.println(map.get(null));

    }
}
