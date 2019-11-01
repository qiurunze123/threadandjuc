package com.geekq.learnguava.guava.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TableExampleTest
{
    //ArrayTable
    //TreeBaseTable
    //HashBaseTable
    //ImmutableTable

    @Test
    public void test()
    {
        Table<String, String, String> table = HashBasedTable.create();
        table.put("Language", "Java", "1.8");
        table.put("Language", "Scala", "2.3");
        table.put("Database", "Oracle", "12C");
        table.put("Database", "Mysql", "7.0");
        System.out.println(table);

        Map<String, String> language = table.row("Language");
        assertThat(language.containsKey("Java"), is(true));
        //Map<String,Map<String,String>>
        assertThat(table.row("Language").get("Java"), equalTo("1.8"));
        Map<String, String> result = table.column("Java");
        System.out.println(result);

        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println(cells);
    }
}