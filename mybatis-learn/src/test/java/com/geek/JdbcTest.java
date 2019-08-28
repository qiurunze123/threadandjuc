package com.geek;

import com.geek.entity.Article;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱润泽 bullock
 */
public class JdbcTest {

    @Test
    public void testJdbc(){
        String url = "jdbc:mysql://localhost:3306/miaosha?" +
                "useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false";

        Connection conn = null ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection(url);
            conn =DriverManager.getConnection(url,"root","nihaoma");
            String author = "qiurunze";
            String date = "2018.06.10";
            String sql = "SELECT id, title, author, content, createtime"
                    + " FROM article"
                    + " WHERE author = '" + author
                    + "' AND createtime > '" + date + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Article> articles = new ArrayList<>(rs.getRow());
            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
//                article.setAuthor(rs.getString("author"));
                article.setContent(rs.getString("content"));
                article.setCreateTime(rs.getDate("createtime"));
                articles.add(article);
            }
            System.out.println("Query SQL ==> " + sql);
            System.out.println("Query Result: ");
            articles.forEach(System.out::println);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
