package com.geekq.learnguava.steamandlambda;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 邱润泽 bullock
 */
//map to convert list
public class MapToList {

    public static void main(String[] args) {

        Map<String,String> map = new HashMap<>();
        map.put("1","这是第一个");
        map.put("2","这是第二个");
        map.put("3","这是第三个");
        List<String> valueList = map.values().stream().collect(Collectors.toList());
        System.out.println(valueList);
        System.out.println("-------------------------------------------------------");

        List<String> sortedValueList  = map.values().stream().sorted().collect(Collectors.toList());

        System.out.println(sortedValueList);
        System.out.println("-------------------------------------------------------");

       List<Person> personList =  map.entrySet().stream().sorted(Comparator.comparing(e->e.getKey()))
                .map(e->{
                    Person person = new Person();
                    person.setAge(e.getKey());
                    person.setName(e.getValue());
                    return person;
                }).collect(Collectors.toList());

        System.out.println(personList);
        System.out.println("-----------------------------------------------------------");

    }
}
