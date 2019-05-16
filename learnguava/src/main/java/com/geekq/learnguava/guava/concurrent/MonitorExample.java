package com.geekq.learnguava.guava.concurrent;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

public class MonitorExample
{

    public static void main(String[] args)
    {
        final MonitorGuard mg = new MonitorGuard();

        final AtomicInteger COUNTER = new AtomicInteger(0);

        for (int i = 0; i <= 3; i++)
        {
            new Thread(() ->
            {
                for (; ; )
                    try
                    {
                        int data = COUNTER.getAndIncrement();
                        System.out.println(currentThread() + " offer " + data);
                        mg.offer(data);
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
            }).start();
        }

        for (int i = 0; i <= 2; i++)
        {
            new Thread(() ->
            {
                for (; ; )
                    try
                    {
                        int data = mg.take();
                        System.out.println(currentThread() + " take " + data);
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
            }).start();
        }
    }

    static class MonitorGuard
    {
        private final LinkedList<Integer> queue = new LinkedList<>();

        private final int MAX = 10;

        private final Monitor monitor = new Monitor();

        private final Monitor.Guard CAN_OFFER = monitor.newGuard(() -> queue.size() < MAX);

        private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

        public void offer(int value)
        {
            try
            {
                monitor.enterWhen(CAN_OFFER);
                queue.addLast(value);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } finally
            {
                monitor.leave();
            }
        }

        public int take()
        {
            try
            {
                monitor.enterWhen(CAN_TAKE);
                return queue.removeFirst();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            } finally
            {
                monitor.leave();
            }
        }

    }

    static class LockCondition
    {
        private final ReentrantLock lock = new ReentrantLock();

        private final Condition FULL_CONDITION = lock.newCondition();

        private final Condition EMPTY_CONDITION = lock.newCondition();

        private final LinkedList<Integer> queue = new LinkedList<>();

        private final int MAX = 10;

        public void offer(int value)
        {
            try
            {
                lock.lock();
                while (queue.size() >= MAX)
                {
                    FULL_CONDITION.await();
                }

                queue.addLast(value);
                EMPTY_CONDITION.signalAll();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } finally
            {
                lock.unlock();
            }
        }

        public int take()
        {

            Integer value = null;
            try
            {
                lock.lock();
                while (queue.isEmpty())
                {
                    EMPTY_CONDITION.await();
                }

                value = queue.removeFirst();
                FULL_CONDITION.signalAll();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } finally
            {
                lock.unlock();
            }

            return value;
        }
    }

    static class Synchronized
    {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value)
        {
            synchronized (queue)
            {
                while (queue.size() >= MAX)
                {
                    try
                    {
                        queue.wait();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                queue.addLast(value);
                queue.notifyAll();
            }
        }

        public int take()
        {
            synchronized (queue)
            {
                while (queue.isEmpty())
                {
                    try
                    {
                        queue.wait();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                Integer value = queue.removeFirst();
                queue.notifyAll();
                return value;
            }
        }
    }
}
