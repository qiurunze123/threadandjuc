package com.geek;

import com.geek.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author 邱润泽 bullock
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application.xml")
public class SpringJdbcTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testSpringJdbc() {
        String author = "qiurunze";
        String date = "2018.06.10";
        String sql = "SELECT id, title, author, content, createtime"
                + " FROM article"
                + " WHERE author = '" + author
                + "' AND createtime > '" + date + "'";
        List<Article> articles = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setTitle(rs.getString("title"));
//            article.setAuthor(rs.getString("author"));
            article.setContent(rs.getString("content"));
            article.setCreateTime(rs.getDate("createtime"));
            return article;
        });
        System.out.println("Query SQL ========> " + sql);
        System.out.println("Spring JDBC Query Result: ");
        articles.forEach(System.out::println);
    }
}
