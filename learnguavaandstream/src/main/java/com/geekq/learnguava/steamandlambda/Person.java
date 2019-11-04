package com.geekq.learnguava.steamandlambda;

/**
 * @author 邱润泽 bullock
 */
public class Person {

    private  String name ;

    private  String age;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public Person setAge(String age) {
        this.age = age;
        return this;
    }
}
