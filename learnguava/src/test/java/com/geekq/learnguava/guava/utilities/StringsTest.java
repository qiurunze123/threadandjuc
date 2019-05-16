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
        assertThat(Strings.emptyToNull(""), nullValue());
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.nullToEmpty("hello"), equalTo("hello"));
        assertThat(Strings.commonPrefix("Hello", "Hit"), equalTo("H"));
        assertThat(Strings.commonPrefix("Hello", "Xit"), equalTo(""));
        assertThat(Strings.commonSuffix("Hello", "Echo"), equalTo("o"));
        assertThat(Strings.repeat("Alex", 3), equalTo("AlexAlexAlex"));
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty(""), equalTo(true));

        assertThat(Strings.padStart("Alex", 3, 'H'), equalTo("Alex"));
        assertThat(Strings.padStart("Alex", 5, 'H'), equalTo("HAlex"));
        assertThat(Strings.padEnd("Alex", 5, 'H'), equalTo("AlexH"));
    }

    @Test
    public void testCharsets() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8, equalTo(charset));
    }

    /**
     * functor
     */
    @Test
    public void testCharMatcher() {
        assertThat(CharMatcher.javaDigit().matches('5'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));

        assertThat(CharMatcher.is('A').countIn("Alex Sharing the Google Guava to Us"), equalTo(1));
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("      hello Guava     ", '*'), equalTo("*hello*Guava*"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world"), equalTo("helloworld"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world"), equalTo(" 234 "));

    }

    public Integer text(){
        return 0;
    }
}
