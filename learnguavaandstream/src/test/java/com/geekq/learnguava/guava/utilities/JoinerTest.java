package com.geekq.learnguava.guava.utilities;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * 邱润泽 -- bulllock
 */
public class JoinerTest {

    Logger logger = LoggerFactory.getLogger(JoinerTest.class);

    private final List<String> stringList = Arrays.asList(
            "Google", "Guava", "Java", "Scala", "Kafka"
    );

    private final List<String> stringListWithNullValue = Arrays.asList(
            "Google", "Guava", "Java", "Scala", null
    );

    private final Map<String, String> stringMap = of("Hello", "Guava", "Java", "Scala");


    private final String targetFileName = "G:\\Teaching\\qiurunze\\guava-joiner.txt";
    private final String targetFileNameToMap = "G:\\Teaching\\qiurunze\\guava-joiner-map.txt";

    /**
     * 给字符串 按照规则拼接
     */
    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(stringList);
        logger.info(result);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    /**
     * 去除NULL字符串 存在的控制 并按照规则拼接
     */
    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        logger.info(result);
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }


    /**
     * 把NULL 值设置成默认值 并按照规则拼接
     */
    @Test
    public void testJoin_On_Join_WithNullValue_UseDefaultValue() {
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        logger.info(result);
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    /**
     * 追加到 stringbuilder   -- append to
     */
    @Test
    public void testJoin_On_Append_To_StringBuilder() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder, stringListWithNullValue);
        logger.info(resultBuilder.toString());
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
        assertThat(builder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    /**
     * wirte file --
     */
    @Test
    public void testJoin_On_Append_To_Writer() {

        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }
//=======================JDK8 ================================================
    @Test
    public void testJoiningByStreamSkipNullValues() {
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty()).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }


    @Test
    public void testJoiningByStreamWithDefaultValue() {
        String result = stringListWithNullValue.stream().map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    private String defaultValue(final String item) {
        return item == null || item.isEmpty() ? "DEFAULT" : item;
    }

//=======================JDK8 ================================================

    /**
     *  key value 按照 = 拼接 每个键值对 按照 # 拼接
     */
    @Test
    public void testJoinOnWithMap() {

        String result = Joiner.on('#').withKeyValueSeparator("=").join(stringMap);
        logger.info("================== "+result);
        assertThat(Joiner.on('#').withKeyValueSeparator("=").join(stringMap), equalTo("Hello=Guava#Java=Scala"));
    }


    @Test
    public void testJoinOnWithMapToAppendable() {
        try (FileWriter writer = new FileWriter(new File(targetFileNameToMap))) {
            String joiner = Joiner.on("#").withKeyValueSeparator("=").appendTo(writer, stringMap).toString();
            logger.info(joiner);
            assertThat(Files.isFile().test(new File(targetFileNameToMap)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }
}
