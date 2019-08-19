package com.geek.controller;

import com.geek.service.DemoService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 邱润泽 bullock
 */
@Controller(value = "/index")
public class DemoServiceController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/test")
    public String getDemoResult(){
        String result = demoService.sayHello("nihao");
        return  result ;
    }
}
