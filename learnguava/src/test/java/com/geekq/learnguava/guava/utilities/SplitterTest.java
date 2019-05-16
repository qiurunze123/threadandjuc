package com.geekq.learnguava.guava.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * 邱润泽 -- bullock
 */
public class SplitterTest {

    Logger logger = LoggerFactory.getLogger(JoinerTest.class);

    /**
     * 按照 规则分割 字符串
     */
    @Test
    public void testSplitOnSplit() {
//        List<String> result = Splitter.on("|").splitToList("hello|world");

        List<String> result = Splitter.on("|").splitToList("hello|world");

        for (String re : result) {
            logger.info(re);
        }
//        assertThat(result, notNullValue());
//        assertThat(result.size(), equalTo(2));
//        assertThat(result.get(0), equalTo("hello"));
//        assertThat(result.get(1), equalTo("world"));
    }

    /**
     * 分割 字符串 得到 list
     */
    @Test
    public void testSplit_On_Split_OmitEmpty() {
        /**
         * 如果 为null 不去除
         */
        List<String> result = Splitter.on("|").splitToList("hello|world|||");
        for (String re : result) {
            logger.info(re);
        }
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(5));

        /**
         * 如果 有null 自动去除omitEmptyStrings
         */
        result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");

        for (String re : result) {
            logger.info(re);
        }
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
    }

    /**
     * 去除 字符串前后的空字符trimResults 用于控制是否删除拆分结果中的空字符串
     */
    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult() {
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello "));
        assertThat(result.get(1), equalTo(" world"));

        result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        System.out.println(result);
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    /**
     * 按照规则截取字符串 fixedLength
     * aaaabbbbccccdddd
     */
    @Test
    public void testSplitFixLength() {
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        logger.info(result.toString());
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(4));
        assertThat(result.get(0), equalTo("aaaa"));
        assertThat(result.get(3), equalTo("dddd"));
    }

    /**
     * 用于控制拆分的结果个数
     */
    @Test
    public void testSplitOnSplitLimit() {
        List<String> result = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        logger.info(result.toString());
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
        assertThat(result.get(2), equalTo("java#google#scala"));
    }


    @Test
    public void testSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        logger.info(result.toString());
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void testSplitOnPattern() {
        List<String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().splitToList("hello | world|||");
        logger.info(result.toString());
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }


    /**
     * 分割 mapwithKeyValueSeparator   返回  withKeyValueSeparator传递map项key/value
     */
    @Test
    public void testSplitOnSplitToMap() {
        Map<String, String> result = Splitter.on(Pattern.compile("\\|")).trimResults()
                .omitEmptyStrings().withKeyValueSeparator("=").split("hello=HELLO| world=WORLD|||");
        logger.info(result.toString());
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("hello"), equalTo("HELLO"));
        assertThat(result.get("world"), equalTo("WORLD"));

    }
}
