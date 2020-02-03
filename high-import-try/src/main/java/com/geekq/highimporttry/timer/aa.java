package com.geekq.highimporttry.timer;

/**
 * @author 邱润泽 bullock
 */
public class aa {
    public static void main(String[] args) {
        String test = "Failed to freeze 9.0000 on Point 2210283, userId 1305904511, available 6.0000, freezing 25310.6000>";
        String test1 = test.replaceAll("<","");
        System.out.println(test1);
    }
}
