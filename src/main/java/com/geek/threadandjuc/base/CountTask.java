package com.geek.threadandjuc.base;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 50;

    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {//进行分割
            //分成100个小任务
            long step = (start + end) / 20;//小任务数组
            ArrayList<CountTask> subTasks = new ArrayList<>();
//
//            //任务大于阈值, 分裂为2个任务
////            int middle = (start + end) / 2;
//            CountTask countTask1 = new CountTask(start, middle);
//            CountTask countTask2 = new CountTask(middle + 1, end);
//
//            countTask1.fork();
//            countTask2.fork();
//            invokeAll(countTask1, countTask2);
//
//            Long join1 = countTask1.join();
//            Long join2 = countTask2.join();
//
//            // 结果合并
//            sum = join1 + join2;

            long pos = start;
            for (int i = 0; i < 100; i++) {
                //当前任务界限
                long lastOne = pos + step;
                if(lastOne > end) {
                    //到达最后
                    lastOne = end;
                }
                    CountTask subTask = new CountTask(pos, lastOne);
                    subTasks.add(subTask);
                    //fork 后将开启一个新线程 需注意性能
                    subTask.fork();
                pos += step + 1;

            }

            for (CountTask t : subTasks) {
                sum += t.join();
            }
            return sum;
        }
        return sum;
    }
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0,100L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long   res = result.get();
            System.out.println("sum = "+res);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
    }
}