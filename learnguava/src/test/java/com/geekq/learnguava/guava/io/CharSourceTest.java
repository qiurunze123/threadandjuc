package com.geekq.learnguava.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CharSourceTest {

    @Test
    public void testCharSourceWrap() throws IOException {
        CharSource charSource = CharSource.wrap("i am the CharSource");
        String resultAsRead = charSource.read();
        assertThat(resultAsRead, equalTo("i am the CharSource"));
        ImmutableList<String> list = charSource.readLines();
        assertThat(list.size(), equalTo(1));
        assertThat(charSource.length(), equalTo(19L));
        assertThat(charSource.isEmpty(), equalTo(false));
        assertThat(charSource.lengthIfKnown().get(), equalTo(19L));
    }

    @Test
    public void testConcat() throws IOException {
        CharSource charSource = CharSource.concat(
                CharSource.wrap("i am the CharSource1\n"),
                CharSource.wrap("i am the CharSource2")
        );

        System.out.println(charSource.readLines().size());
        charSource.lines().forEach(System.out::println);
    }
}
