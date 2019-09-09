package com.geek;

import com.alibaba.fastjson.JSON;
import com.geek.entity.Article;
import com.geek.mapper.ArticleMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 邱润泽 bullock
 */
public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void prepare() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    @Test
    public void testMyBatis() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleMapper articleDao = session.getMapper(ArticleMapper.class);
            Article articles = articleDao.
                    findByAuthorAndCreateTime("qiurunze", "2018-06-10");

            System.out.println("===================="+ JSON.toJSONString(articles) +"=================");
            SqlSession session2 = sqlSessionFactory.openSession();
            ArticleMapper articleDao2 = session2.getMapper(ArticleMapper.class);
            Article articles1 = articleDao2.
                    findByAuthorAndCreateTime("qiurunze", "2018-06-10");
            System.out.println("===================="+JSON.toJSONString(articles1)+"=================");

            session2.commit();
            session2.close();

        } finally {
            session.commit();
            session.close();

        }
    }
}
