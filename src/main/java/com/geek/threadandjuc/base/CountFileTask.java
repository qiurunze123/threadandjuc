package com.geek.threadandjuc.base;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountFileTask extends RecursiveTask<Integer> {

    private File file ;

    public CountFileTask(File file) {
        this.file = file;
    }

    @Override
    protected Integer compute() {

        int sum =0;
        File[] list = file.listFiles();

        for (File f:list
             ) {

            if(f.isDirectory()){
                ArrayList<CountFileTask> subTasks = new ArrayList<>();
                CountFileTask cft = new CountFileTask(f);
                subTasks.add(cft);
                cft.fork();

                for (CountFileTask t : subTasks) {
                    sum += t.join();
                }
            }else {
                sum++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool =new ForkJoinPool();
        CountFileTask task = new CountFileTask(new File("D:\\tmp"));
        ForkJoinTask<Integer> sum = forkJoinPool.submit(task);

        try {
           Integer sec =  sum.get();
            System.out.println(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
