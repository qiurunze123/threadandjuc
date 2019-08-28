package com.geek;

import com.geek.entity.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author 邱润泽 bullock
 */
public class HibernateTest {

    private SessionFactory buildSessionFactory;
    @Before
    public void init() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.xml");
        buildSessionFactory = configuration.buildSessionFactory();
    }
    @After
    public void destroy() {
        buildSessionFactory.close();
    }

    /**
     * ORM
     */
    @Test
    public void testORM() {
        System.out.println("------------✨ ORM Query ✨------------");
        Session session = null;
        try {
            session = buildSessionFactory.openSession();
            int id = 1;
            Article article = session.get(Article.class, id);
            System.out.println("ORM Query Result: ");
            System.out.println(article);
            System.out.println("=============="+article.getAuthor()+"=========================");
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
        }
    }


    /**
     * hql
     */
    @Test
    public void testHQL() {
        System.out.println("-----------✨ HQL Query ✨+-----------");
        Session session = null;
        try {
            session = buildSessionFactory.openSession();
            String hql = "from Article where author = :author"
                    + " and createtime > :createTime";
            Query query = session.createQuery(hql);
            query.setParameter("author", "qiurunze");
            query.setParameter("createTime", "2018.06.10");
            List<Article> articles = query.list();
            System.out.println("HQL Query Result: ");
            articles.forEach(System.out::println);
            System.out.println();
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
        }
    }


    /**
     * JPA
     * @throws ParseException
     */
    @Test
    public void testJpaCriteria() throws ParseException {
        System.out.println("-----------✨ JPA Criteria ✨------------");
        Session session = null;
        try {
            session = buildSessionFactory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Article> criteriaQuery =
                    criteriaBuilder.createQuery(Article.class);
            Root<Article> article = criteriaQuery.from(Article.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Predicate greaterThan = criteriaBuilder.greaterThan(
                    article.get("createTime"), sdf.parse("2018.06.10"));
            Predicate equal = criteriaBuilder
                    .equal(article.get("author"), "qiurunze");
            criteriaQuery.select(article).where(equal, greaterThan);
            Query<Article> query = session.createQuery(criteriaQuery);
            List<Article> articles = query.getResultList();
            System.out.println("JPA Criteria Query Result: ");
            articles.forEach(System.out::println);
        } finally {
            if (Objects.nonNull(session)) { session.close(); }
        }
    }
}
