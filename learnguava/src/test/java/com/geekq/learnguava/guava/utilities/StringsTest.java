package com.geekq.learnguava.guava.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class StringsTest {

    @Test
    public void testStringsMethod() {
        /**
         * "" 转换为null
         */
        assertThat(Strings.emptyToNull(""), nullValue());
        /**
         * null 转换为 ""
         */
        assertThat(Strings.nullToEmpty(null), equalTo(""));

        /**
         * 获取两个字符串相同的前缀
         */
        assertThat(Strings.commonPrefix("Dello", "Det"), equalTo("De"));
        /**
         * 如果没有 则 获取 变为 ""
         */
        assertThat(Strings.commonPrefix("Hello", "Xit"), equalTo(""));

        /**
         * 获取 两个字符串相同后缀
         */
        assertThat(Strings.commonSuffix("Hello", "Echo"), equalTo("o"));

        /**
         * 把一个字符 按规则重复
         */
        assertThat(Strings.repeat("Alex", 3), equalTo("AlexAlexAlex"));

        /**
         * 是否为  "" 或者 null
         */
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty(""), equalTo(true));

        /**
         * 字符串开始或结束位置重复增加某个字符串到某个长度：padEnd和padStart方法
         */
        System.out.println(Strings.padStart("Alex", 3, 'H'));
        assertThat(Strings.padStart("Alex", 3, 'H'), equalTo("Alex"));
        assertThat(Strings.padStart("Alex", 5, 'H'), equalTo("HAlex"));
        assertThat(Strings.padEnd("Alex", 5, 'H'), equalTo("AlexH"));
    }

    /**
     * 设置字符集
     */
    @Test
    public void testCharsets() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8, equalTo(charset));
    }

    /**
     * functor 返回想要的字符
     */
    @Test
    public void testCharMatcher() {
        assertThat(CharMatcher.javaDigit().matches('5'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));

        /**
         * countIn 返回sequence中匹配到的字符计数  is(char match): 返回匹配指定字符的Matcher
         *
         * breakingWhitespace 去除空格  collapseFrom 替换成想要的字符
         */
        assertThat(CharMatcher.is('A').countIn("Alex Sharing the Google Guava to Us"), equalTo(1));
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("      hello Guava     ", '*'), equalTo("*hello*Guava*"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world"), equalTo("helloworld"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world"), equalTo(" 234 "));

    }

    public Integer text(){
        return 0;
    }
}
