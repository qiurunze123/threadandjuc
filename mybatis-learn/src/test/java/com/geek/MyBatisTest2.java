package com.geek;

import com.geek.entity.Article;
import com.geek.entity.ArticleDO;
import com.geek.entity.Author;
import com.geek.entity.AuthorDO;
import com.geek.mapper.ArticleMapper;
import com.geek.mapper.AuthorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author 邱润泽 bullock
 */
public class MyBatisTest2 {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void prepare() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }


    @Test
    public void testOne2One() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleMapper articleDao = session.getMapper(ArticleMapper.class);
            Article article = articleDao.findOne(1);
            Author author = article.getAuthor();
            article.setAuthor(null);
            System.out.println("\nauthor info:");
            System.out.println(author);
            System.out.println(author.getName());
            System.out.println("\narticles info:");
            System.out.println(article);
            System.out.println(article.getContent());

        } finally {
            session.close();
        }
    }



    @Test
    public void testOne2Many() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuthorMapper authorDao = session.getMapper(AuthorMapper.class);
            Author author = authorDao.findOne(2);
            List<Article> arts = author.getArticles();
            List<Article> articles =
                    Arrays.asList(arts.toArray(new Article[arts.size()]));
            arts.clear();
            System.out.println("\nauthor info:");
            System.out.println(author);
            System.out.println("\narticles info:");
            articles.forEach(System.out::println);
        } finally {
            session.close();
        }
    }
}
