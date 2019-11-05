package com.geekq.learnguava.steamandlambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 邱润泽 bullock
 */
public class StreamGroupByTest {

    public static void main(String[] args) {
        //collectors 封装了很多常用的汇聚操作
        //将结果汇聚到ArrayList中
        //Collectors.toList();
        //将结果汇聚到HashSet中
        //Collectors.toSet();
        //将结果汇聚到一个指定类型的集合中
        //Collectors.toCollection(Supplier<C> collectionFactory);

        //我们现在按Student的name进行分组，如果使用sql来表示就是select * from student group by name; 再看下使用Stream的方式
        Student student1 = new Student("zhangsan",60);
        Student student2 = new Student("lisi",70);
        Student student3 = new Student("lisi",80);
        Student student4 = new Student("zhaoliu",90);
        List<Student> students = Arrays.asList(student1,student2,student3,student4);
        Map<String, List<Student>> collect = students.stream().
                collect(Collectors.groupingBy(Student::getName));

        System.out.println(collect);

        //按照name分组 想求出每组学生的数量就需要借助groupingby的另一个重载的方法
        //按name分组 得出每组的学生数量 使用重载的groupingBy方法，第二个参数是分组后的操作
        Map<String, Long> collect1 = students.stream().
                collect(Collectors.groupingBy(Student::getName, Collectors.counting()));

        //我们还可以对分组后的数据求平均值
        Map<String, Double> collect2 = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.averagingDouble(Student::getScore)));

        //针对上面的Student，我们现在再加一个需求，分别统计一下及格和不及格的学生（分数是否>=60）
        //这时候符合Stream分区的概念了，Stream分区会将集合中的元素按条件分成两部分结果，key是Boolean类型，value是结果集，满足条件的key是true

        Map<Boolean, List<Student>> collect3 = students.stream().collect(Collectors.partitioningBy(student -> student.getScore() >= 60));
        System.out.println(collect3.get(true));//输出及格的Student
        System.out.println(collect3.get(false));//输出不及格的Student
    }
}
