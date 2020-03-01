package com.geekq.highimporttry.controller;

import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Controller
@RequestMapping("/high")
public class HighController {

    @Autowired
    private HighImportDataService highImportDataService;

    @RequestMapping("/execute")
    public void execute(@RequestParam(name = "id", required = false) Integer id ){
        String day = DateUtil.formatDate(new Date());
        highImportDataService.importData(day);
    }

}
