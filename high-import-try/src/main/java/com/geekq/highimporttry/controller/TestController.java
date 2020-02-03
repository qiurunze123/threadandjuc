package com.geekq.highimporttry.controller;

import com.geekq.highimporttry.service.HighImportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 邱润泽 bullock
 */
@Controller
public class TestController {


    @Autowired
    private HighImportDataService highImportDataService;


    @ResponseBody
    @RequestMapping(value = "/test")
    public void test(){
        highImportDataService.recordHandle("11111");
    }

    /**
     * 进行两种测试
     * 关闭逃逸分析，同时调大堆空间，避免堆内GC的发生，如果有GC信息将会被打印出来
     * VM运行参数：-Xmx4G -Xms4G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
     *
     * 开启逃逸分析
     * VM运行参数：-Xmx4G -Xms4G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
     *
     * 执行main方法后
     * jps 查看进程
     * jmap -histo 进程ID
     *
     */
    @ResponseBody
    @RequestMapping(value = "/threadrun")
    public void threadRun(){


        synchronized (new Object()){
            //逻辑
            System.out.println("我来测试了.......");
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        //查看执行时间
        System.out.println("cost-time " + (end - start) + " ms");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static Student alloc() {
        //Jit对编译时会对代码进行 逃逸分析
        //并不是所有对象存放在堆区，有的一部分存在线程栈空间
        Student student = new Student();
        return student;
    }

    static class Student {
        private String name;
        private int age;
    }



}
