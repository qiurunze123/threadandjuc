package com.geekq.learnguava.guava.concurrent;

import com.google.common.util.concurrent.*;

import javax.annotation.Nullable;
import java.util.concurrent.*;

public class ListenableFutureExample
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        ExecutorService service = Executors.newFixedThreadPool(2);

        /*Future<Integer> future = service.submit(() ->
        {
            try
            {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return 10;
        });

        Object result = future.get();
        System.out.println(result);*/

 /*       ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);
        ListenableFuture<Integer> future = listeningExecutorService.submit(() ->
        {
            try
            {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return 100;
        });

//        future.addListener(() -> System.out.println("I am finished"), service);

        Futures.addCallback(future, new MyCallBack(), service);
        System.out.println("=============");*/

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            try
            {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return 100;
        }, service).whenComplete((v, t) -> System.out.println("I am finished and the result is " + v));
    }

    static class MyCallBack implements FutureCallback<Integer>
    {

        @Override
        public void onSuccess(@Nullable Integer result)
        {
            System.out.println("I am finished and the result is " + result);
        }

        @Override
        public void onFailure(Throwable t)
        {
            t.printStackTrace();
        }
    }
}
