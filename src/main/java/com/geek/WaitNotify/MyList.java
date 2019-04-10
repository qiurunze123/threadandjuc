package com.geek.WaitNotify;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱润泽 bullock
 */
public class MyList {

    private static List<String> list = new ArrayList<String>();

    public static void add(){
        list.add("sth");
    }

    public static int getSize(){
        return list.size();
    }
}
