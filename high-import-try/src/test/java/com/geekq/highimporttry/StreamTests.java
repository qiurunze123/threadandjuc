package com.geekq.highimporttry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邱润泽 bullock
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class StreamTests {


//    /**
//     * map、flatMap方法
//     */
//    @Test
//    public void testMapAndFlatMap() {
//        List<Person> list = new ArrayList<>();
//        List<Friend> friends = new ArrayList<>();
//        friends.add(new Friend("Java5"));
//        friends.add(new Friend("Java6"));
//        friends.add(new Friend("Java7"));
//        Person person = new Person();
//        person.setFriends(friends);
//        list.add(person);
//
//        //映射出名字
//        List<Friend> collect1 = list.stream().flatMap(friend -> friend.getFriends().stream()).collect(Collectors.toList());
//
//        System.out.println(collect1);
//
//    }
}
