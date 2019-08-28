package com.geek;

import com.geek.entity.Article;
import com.geek.entity.Author;
import com.geek.mapper.ArticleMapper;
import com.geek.mapper.AuthorMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @author 邱润泽 bullock
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-mybatis.xml")
public class SpringWithMyBatisTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    /** 􃠚自动注入 SqlSession 无需获取 */
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Before
    public void printBeanInfo() {
        ListableBeanFactory lbf = applicationContext;
        String[] beanNames = lbf.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        System.out.println("\n--------------☆ bean name ☆-------------");
        Arrays.asList(beanNames).subList(0, 5).forEach(System.out::println);
        AuthorMapper authorDao =
                (AuthorMapper) applicationContext.getBean("authorMapper");
        ArticleMapper articleDao =
                (ArticleMapper) applicationContext.getBean("articleMapper");
        System.out.println("\n-----------☆ bean class info ☆------------");
        System.out.println("AuthorDao Class: " + authorDao.getClass());
        System.out.println("ArticleDao Class: " + articleDao.getClass());
        System.out.println("\n-------xxxx--------xxxx-------xxx--------\n");
    }



    @Test
    public void testOne2One() {
        Article article = articleMapper.findOne(1);
        Author author = article.getAuthor();
        article.setAuthor(null);
        System.out.println("\nauthor info:");
        System.out.println(author);
        System.out.println("\narticles info:");
        System.out.println(article);
    }
    @Test
    public void testOne2Many() {
        Author author = authorMapper.findOne(2);
        System.out.println("\nauthor info:");
        System.out.println(author);
        System.out.println("\narticles info:");
        author.getArticles().forEach(System.out::println);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
