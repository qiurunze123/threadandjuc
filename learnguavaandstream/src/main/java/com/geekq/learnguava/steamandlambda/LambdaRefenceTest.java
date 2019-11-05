package com.geekq.learnguava.steamandlambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author 邱润泽 bullock
 *
 * 如果某个方法满足lambda表达式的形式那么就可以将这个lambda表达式用方法引用表示
 * 但是如果方法比较复杂 就不可以用 方法引用实际上是lambda表达式的一种语法糖
 *
 * 方法引用总共分为四类：
 * 1.类型:: 静态方法名
 * 2.对象:: 实例方法名
 * 3.类型::实例方法名
 * 4.类名:: new
 */
public class LambdaRefenceTest {

    public static void main(String[] args) {
        Student student1 = new Student("zhangsan",60);
        Student student2 = new Student("lisi",70);
        Student student3 = new Student("wangwu",80);
        Student student4 = new Student("zhaoliu",90);
        List<Student> students = Arrays.asList(student1,student2,student3,student4);
        //分数由大到小进行输出
        students.sort(((o1, o2) -> o1.getScore()-o2.getScore()));

        students.forEach(student -> System.out.println("普通方式"+student.getScore()));

        System.out.println("----------------------------------");

        students.sort(Student::compareStudentByScore);
        students.forEach(student -> System.out.println("类名+静态方法"+student.getScore()));
        System.out.println("------------------------------");


        //对象::实例方法名
        StudentComparator studentComparator = new StudentComparator();
        students.sort(studentComparator::compareStudentByScore);
        students.forEach(student -> System.out.println("对象::实例方法名"+student.getScore()));


        System.out.println("------------------------------");


        //类名::new 可以搞出一个对象
        Supplier<Student> supplier = Student::new;
        Student student = supplier.get();




    }
}
