package com.apollo.threadinterview.leetcodethreadsub;

/**
 * @author 邱润泽 bullock
 */
public class Counter {

    public static void main(String[] args) {
        System.out.println(getResult(20));
    }




    public static int getResult(int cycle) {
        //最开始5个长度不累加
        if (cycle <= 5) {
            return cycle;
        }

        int maxI = 5;
        int result = 0;

        for (int i = 5; i <= cycle; i++) {
            result++;
            if (i % maxI == 0
                    && i!=cycle) {

                maxI = i + maxI * 2;
                result = 0;
            }
        }
        return result;
    }
}
