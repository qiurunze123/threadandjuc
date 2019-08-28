package com.geek;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.junit.Test;

/**
 * @author 邱润泽 bullock
 */
public class MetaClassTest {
    private class Author {
        private Integer id;
        private String name;
        private Integer age;
        /**
         * 􀐰􀑚􀖌􃘵􁈩􁓄􀽊􃇷􁮷􃄐
         */
        private MetaClassTest.Article[] articles;
// 􂴱􂮕 getter/setter


        public Integer getId() {
            return id;
        }

        public Author setId(Integer id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Author setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getAge() {
            return age;
        }

        public Author setAge(Integer age) {
            this.age = age;
            return this;
        }

        public Article[] getArticles() {
            return articles;
        }

        public Author setArticles(Article[] articles) {
            this.articles = articles;
            return this;
        }
    }

    private class Article {
        private Integer id;
        private String title;
        private String content;
        private Author author;

        public Integer getId() {
            return id;
        }

        public Article setId(Integer id) {
            this.id = id;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Article setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Article setContent(String content) {
            this.content = content;
            return this;
        }

        public Author getAuthor() {
            return author;
        }

        public Article setAuthor(Author author) {
            this.author = author;
            return this;
        }
    }

    @Test
    public void testHasSetter() {
        //为author 创建元信息
        MetaClass authorMeta = MetaClass.forClass(
                Author.class, new DefaultReflectorFactory());
        System.out.println("------------☆ Author ☆------------");
        System.out.println("id -> " + authorMeta.hasSetter("id"));
        System.out.println("name -> " + authorMeta.hasSetter("name"));
        System.out.println("age -> " + authorMeta.hasSetter("age"));
        System.out.println("articles->" + authorMeta.hasSetter("articles"));
        //   如上，Article 类中包含了一个Author 引用。然后我们调用articleMeta 的hasSetter 检测
        // author.id 和author.name 属性是否存在，我们的期望结果为true。测试结果如下：
        System.out.println("articles[] -> " +
                authorMeta.hasSetter("articles[]"));
        System.out.println("title -> " + authorMeta.hasSetter("title"));
        MetaClass articleMeta = MetaClass.forClass(
                Article.class, new DefaultReflectorFactory());
        System.out.println("\n------------☆ Article ☆------------");
        System.out.println("id -> " + articleMeta.hasSetter("id"));
        System.out.println("title -> " + articleMeta.hasSetter("title"));
        System.out.println("content -> " +
                articleMeta.hasSetter("content"));
// 下面两个均为复杂属性 分别检测Article类中的Author类
// 是否包括 name和id的setter方法
        System.out.println("author.id->" +
                articleMeta.hasSetter("author.id"));
        System.out.println("author.name->" +
                articleMeta.hasSetter("author.name"));
    }
   // 否存在setter 方法，结果也均为true。这说明PropertyTokenizer 对数组和复合属性均进行了 处理。那它是如何处理的呢
}
