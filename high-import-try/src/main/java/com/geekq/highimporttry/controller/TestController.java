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
}
