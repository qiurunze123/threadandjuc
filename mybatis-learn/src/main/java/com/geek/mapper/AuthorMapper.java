package com.geek.mapper;

import com.geek.entity.Article;
import com.geek.entity.Author;
import org.apache.ibatis.annotations.Param;

/**
 * @author 邱润泽 bullock
 */
public interface AuthorMapper {

    Author findOne(@Param("id") int id);
}
