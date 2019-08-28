package com.geek.entity;

import com.geek.constanst.ArticleTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 邱润泽 bullock
 */
@Setter
@Getter
public class ArticleDO {

    private Integer id;
    private String title;
    private AuthorDO author;
    private ArticleTypeEnum type;
    private String content;
    private Date createTime;
}
