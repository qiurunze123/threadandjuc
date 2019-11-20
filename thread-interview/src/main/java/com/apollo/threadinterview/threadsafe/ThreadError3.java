package com.apollo.threadinterview.threadsafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/17
 *
 */
public class ThreadError3{

    private Map<String, String> states;

    public ThreadError3() {
        states = new HashMap<>();
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周四");
    }

    public Map<String, String> getStates() {
        return states;
    }

    //==================解决==================

    public Map<String,String> getStatesImprove(){
        //对方本已想修改只想获取数据
//        return states;
        //副本 不会影响原来的数据
        return new HashMap<>(states);
    }


    public static void main(String[] args) {
        ThreadError3 multiThreadsError3 = new ThreadError3();
        Map<String, String> states = multiThreadsError3.getStates();

        //=======================================
        System.out.println(multiThreadsError3.getStatesImprove().remove("1"));
        System.out.println(multiThreadsError3.getStatesImprove().get("1"));


        System.out.println(states.get("1"));
        states.remove("1");
        System.out.println(states.get("1"));

    }
}
