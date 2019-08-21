package com.geek.service;

import com.geek.entity.User;

/**
 * @author 邱润泽 bullock
 */
public interface NotifyService {
    public void onreturn(User msg,String key, User user);
    public void onthrow(Throwable ex, Integer id);
}