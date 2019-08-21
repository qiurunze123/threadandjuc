package com.geek.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.geek.entity.User;
import com.geek.service.BackService;
import com.geek.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author 邱润泽 bullock
 */
@Controller
@RequestMapping(value = "/index")
public class DemoServiceController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceController.class);
    @Autowired
    private DemoService demoService;
    @Autowired
    private BackService callbackService;

    @RequestMapping(value = "/test")
    public String getDemoResult(){
        String result = demoService.sayHello("nihao");
        return  result ;
    }


    /**
     * 有一些特定场景需要这么处理 必须等待所有接口全部返回 才可以
     * @return
     */
    @RequestMapping(value = "/sync")
    @ResponseBody
    public String getSyncTest(){
       String syncResult1 = demoService.testSync1("我再测试sync1",1);
        Future<String> future1 = RpcContext.getContext().getFuture();

        String syncResult2 = demoService.testSync2("我在测试sync2",2);
        Future<String> future2 = RpcContext.getContext().getFuture();

        try {
            syncResult1 = future1.get();
            syncResult2 = future2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        logger.info("syncResult1"+syncResult1);
        logger.info("syncResult2"+syncResult2);

        return "测试 dubbo  异步成功";
    }



    /**
     * 有一些特定场景需要这么处理 必须等待所有接口全部返回 才可以
     * @return
     */
    @RequestMapping(value = "/callback")
    @ResponseBody
    public String getCallBackTest(){
        User user =new User("GEEKQ");
        User u = callbackService.callbackListener(" code ",user);
        logger.info(JSON.toJSONString(u));
        return JSON.toJSONString(u);
    }
}
