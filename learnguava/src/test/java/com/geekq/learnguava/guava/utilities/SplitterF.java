package com.geekq.learnguava.guava.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author 邱润泽 bullock
 */
public class SplitterF {


    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;

    private SplitterF(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private SplitterF(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }
    private interface Strategy {
        List<String> iterator(Splitter splitter, CharSequence toSplit);
    }



    public static SplitterF on(final String separator) {
        return new SplitterF(
                new Strategy() {
                    @Override
                    public List<String> iterator(Splitter splitter, CharSequence toSplit) {
                        List<String> list = new ArrayList<>();
                        list.add(separator);
                        return list;
                    }
                });
    }




}
