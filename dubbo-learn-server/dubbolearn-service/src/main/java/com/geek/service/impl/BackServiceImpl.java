package com.geek.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.geek.entity.User;
import com.geek.service.BackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 邱润泽 bullock
 */
@Service
@Component("backService")
public class BackServiceImpl implements BackService {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(BackServiceImpl.class);


    @Override
    public User callbackListener(String key, User user) {
        logger.info("我是callback");
        return new User(key);
    }
}
