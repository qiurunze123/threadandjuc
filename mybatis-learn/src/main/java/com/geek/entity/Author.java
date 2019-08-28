package com.geek.entity;

import com.geek.constanst.SexEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 邱润泽 bullock
 */
@Setter
@Getter
public class Author {

    private Integer id;
    private String name;
    private Integer age;
    private SexEnum sex;
    private String email;
    private List<Article> articles;
}
