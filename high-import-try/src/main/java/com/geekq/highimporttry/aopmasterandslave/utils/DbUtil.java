package com.geekq.highimporttry.aopmasterandslave.utils;

public class DbUtil {
    public static String master="master";
    public static String slave="slave";

    private static ThreadLocal<String> threadLocal=new ThreadLocal();


    public static void setDb(String db){
        threadLocal.set(db);
    }

    public static String getDb(){
        return threadLocal.get();
    }

}
