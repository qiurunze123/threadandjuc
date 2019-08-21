package com.geek.entity;

import java.io.Serializable;

/**
 * @author 邱润泽 bullock
 */
public class User implements Serializable {

    private String name ;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
