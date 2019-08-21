package com.geek.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.geek.service.DemoService;
import org.springframework.stereotype.Component;

/**
 * @author 邱润泽 bullock
 */
@Service
@Component("demoService")
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println(" === name === "+ name);
        return "hello";
    }

    @Override
    public String testSync1(String name, Integer count) {
        return name+count;
    }

    @Override
    public String testSync2(String name, Integer count) {
        return name+count;
    }
}
