package com.geek.service;

import com.geek.entity.User;

/**
 * @author 邱润泽 bullock
 */
public interface BackService {

    User callbackListener(String key, User user);
}
