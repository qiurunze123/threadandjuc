package com.geek.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.geek.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 邱润泽 bullock
 */

@Component("notifyService")
public class NotifyServiceImpl implements NotifyService {


    private static final Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);

    public Map<Integer, User> ret    = new HashMap<Integer, User>();
    public Map<Integer, Throwable> errors = new HashMap<Integer, Throwable>();

    @Override
    public void onreturn(User msg, String key, User user) {
        System.out.println("onreturn:" + msg);
        ret.put(1, msg);
    }
    @Override
    public void onthrow(Throwable ex, Integer id) {

        errors.put(id, ex);
    }
}
