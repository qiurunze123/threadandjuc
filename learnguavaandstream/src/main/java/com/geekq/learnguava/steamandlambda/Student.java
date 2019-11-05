package com.geekq.learnguava.steamandlambda;

/**
 * @author 邱润泽 bullock
 */
public class Student {

    private String name ;

    private int score ;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Student setScore(int score) {
        this.score = score;
        return this;
    }

    public static int compareStudentByScore(Student student1,Student student2){
        return student1.getScore() - student2.getScore();
    }


    public static int compareStudentByName(Student student1,Student student2){
        return student1.getName().compareToIgnoreCase(student2.getName());
    }


}
