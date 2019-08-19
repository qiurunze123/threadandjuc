package com.geek.controller;

import com.geek.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 邱润泽 bullock
 */
@RestController(value = "/index")
public class DemoServiceController {

    @Autowired
    private DemoService demoService;

    public String getDemoResult(){
        String result = demoService.sayHello("nihao");
        return  result ;
    }
}
