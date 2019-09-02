package com.geek;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author 邱润泽 bullock
 */
public class ResultMapTest {
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void printResultMapInfo() throws Exception {
        Configuration configuration = new Configuration();
        String resource = "ArticleMapper2l ";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        XMLMapperBuilder builder = new XMLMapperBuilder(inputStream,
                configuration, resource, configuration.getSqlFragments());
        builder.parse();
        ResultMap resultMap = configuration.getResultMap("articleResultArticle");
        System.out.println("\n----------+✨ mappedColumns ✨+-----------");
        System.out.println(resultMap.getMappedColumns());
        System.out.println("\n---------+✨ mappedProperties ✨+---------");
        System.out.println(resultMap.getMappedProperties());
        System.out.println("\n---------+✨ idResultMappings ✨+----------");
        resultMap.getIdResultMappings().forEach(
                rm -> System.out.println(simplify(rm)));
        System.out.println("\n------+✨ propertyResultMappings ✨+-------");
        resultMap.getPropertyResultMappings().forEach(
                rm -> System.out.println(simplify(rm)));
        System.out.println("\n----+✨ constructorResultMappings ✨+-----");
        resultMap.getConstructorResultMappings().forEach(
                rm -> System.out.println(simplify(rm)));
        System.out.println("\n---------+✨ resultMappings ✨+-----------");
        resultMap.getResultMappings().forEach(
                rm -> System.out.println(simplify(rm)));
        inputStream.close();
    }
    /**简单输出 simplify 结果*/
    private String simplify(ResultMapping resultMapping) {
        return String.format(
                "ResultMapping{column='%s', property='%s', flags=%s, ...}",
                resultMapping.getColumn(), resultMapping.getProperty(),
                resultMapping.getFlags());
}
}
