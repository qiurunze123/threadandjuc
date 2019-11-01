package com.geekq.highimporttry.entity;

import java.util.List;

/**
 * @author 邱润泽 bullock
 */
public class Person {

    List<Friend> friends ;

    private String name ;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public Person setFriends(List<Friend> friends) {
        this.friends = friends;
        return this;
    }
}
