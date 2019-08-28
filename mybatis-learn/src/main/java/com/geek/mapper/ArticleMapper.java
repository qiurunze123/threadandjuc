package com.geek.mapper;

import com.geek.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author 邱润泽 bullock
 */
public interface ArticleMapper {

    Article findByAuthorAndCreateTime(@Param("author") String author , @Param("createTime") String createTime);

    Article findOne(@Param("id") int id);
}
