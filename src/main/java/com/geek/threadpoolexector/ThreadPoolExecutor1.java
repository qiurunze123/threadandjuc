/*
package com.geek.threadpoolexector;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

*/
/**
 * 线程池实现类1
 *//*

    //
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ThreadPoolExecutor1 extends AbstractExecutorService {
        private final AtomicInteger ctl;
        private static final int COUNT_BITS = 29;
        private static final int CAPACITY = 536870911;
        private static final int RUNNING = -536870912;
        private static final int SHUTDOWN = 0;
        private static final int STOP = 536870912;
        private static final int TIDYING = 1073741824;
        private static final int TERMINATED = 1610612736;
        private final BlockingQueue<Runnable> workQueue;
        private final ReentrantLock mainLock;
        private final HashSet<java.util.concurrent.ThreadPoolExecutor.Worker> workers;
        private final Condition termination;
        private int largestPoolSize;
        private long completedTaskCount;
        private volatile ThreadFactory threadFactory;
        private volatile RejectedExecutionHandler handler;
        private volatile long keepAliveTime;
        private volatile boolean allowCoreThreadTimeOut;
        private volatile int corePoolSize;
        private volatile int maximumPoolSize;
        private static final RejectedExecutionHandler defaultHandler = new java.util.concurrent.ThreadPoolExecutor.AbortPolicy();
        private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");
        private final AccessControlContext acc;
        private static final boolean ONLY_ONE = true;

        private static int runStateOf(int var0) {
            return var0 & -536870912;
        }

        private static int workerCountOf(int var0) {
            return var0 & 536870911;
        }

        private static int ctlOf(int var0, int var1) {
            return var0 | var1;
        }

        private static boolean runStateLessThan(int var0, int var1) {
            return var0 < var1;
        }

        private static boolean runStateAtLeast(int var0, int var1) {
            return var0 >= var1;
        }

        private static boolean isRunning(int var0) {
            return var0 < 0;
        }

        private boolean compareAndIncrementWorkerCount(int var1) {
            return this.ctl.compareAndSet(var1, var1 + 1);
        }

        private boolean compareAndDecrementWorkerCount(int var1) {
            return this.ctl.compareAndSet(var1, var1 - 1);
        }

        private void decrementWorkerCount() {
            while(!this.compareAndDecrementWorkerCount(this.ctl.get())) {
                ;
            }

        }

        private void advanceRunState(int var1) {
            int var2;
            do {
                var2 = this.ctl.get();
            } while(!runStateAtLeast(var2, var1) && !this.ctl.compareAndSet(var2, ctlOf(var1, workerCountOf(var2))));

        }

        final void tryTerminate() {
            while(true) {
                int var1 = this.ctl.get();
                if (isRunning(var1) || runStateAtLeast(var1, 1073741824) || runStateOf(var1) == 0 && !this.workQueue.isEmpty()) {
                    return;
                }

                if (workerCountOf(var1) != 0) {
                    this.interruptIdleWorkers(true);
                    return;
                }

                ReentrantLock var2 = this.mainLock;
                var2.lock();

                try {
                    if (!this.ctl.compareAndSet(var1, ctlOf(1073741824, 0))) {
                        continue;
                    }

                    try {
                        this.terminated();
                    } finally {
                        this.ctl.set(ctlOf(1610612736, 0));
                        this.termination.signalAll();
                    }
                } finally {
                    var2.unlock();
                }

                return;
            }
        }

        private void checkShutdownAccess() {
            SecurityManager var1 = System.getSecurityManager();
            if (var1 != null) {
                var1.checkPermission(shutdownPerm);
                ReentrantLock var2 = this.mainLock;
                var2.lock();

                try {
                    Iterator var3 = this.workers.iterator();

                    while(var3.hasNext()) {
                        java.util.concurrent.ThreadPoolExecutor.Worker var4 = (java.util.concurrent.ThreadPoolExecutor.Worker)var3.next();
                        var1.checkAccess(var4.thread);
                    }
                } finally {
                    var2.unlock();
                }
            }

        }

        private void interruptWorkers() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            try {
                Iterator var2 = this.workers.iterator();

                while(var2.hasNext()) {
                    java.util.concurrent.ThreadPoolExecutor.Worker var3 = (java.util.concurrent.ThreadPoolExecutor.Worker)var2.next();
                    var3.interruptIfStarted();
                }
            } finally {
                var1.unlock();
            }

        }

        private void interruptIdleWorkers(boolean var1) {
            ReentrantLock var2 = this.mainLock;
            var2.lock();

            try {
                Iterator var3 = this.workers.iterator();

                while(var3.hasNext()) {
                    java.util.concurrent.ThreadPoolExecutor.Worker var4 = (java.util.concurrent.ThreadPoolExecutor.Worker)var3.next();
                    Thread var5 = var4.thread;
                    if (!var5.isInterrupted() && var4.tryLock()) {
                        try {
                            var5.interrupt();
                        } catch (SecurityException var15) {
                            ;
                        } finally {
                            var4.unlock();
                        }
                    }

                    if (var1) {
                        break;
                    }
                }
            } finally {
                var2.unlock();
            }

        }

        private void interruptIdleWorkers() {
            this.interruptIdleWorkers(false);
        }

        final void reject(Runnable var1) {
            this.handler.rejectedExecution(var1, this);
        }

        void onShutdown() {
        }

        final boolean isRunningOrShutdown(boolean var1) {
            int var2 = runStateOf(this.ctl.get());
            return var2 == -536870912 || var2 == 0 && var1;
        }

        private List<Runnable> drainQueue() {
            BlockingQueue var1 = this.workQueue;
            ArrayList var2 = new ArrayList();
            var1.drainTo(var2);
            if (!var1.isEmpty()) {
                Runnable[] var3 = (Runnable[])var1.toArray(new Runnable[0]);
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Runnable var6 = var3[var5];
                    if (var1.remove(var6)) {
                        var2.add(var6);
                    }
                }
            }

            return var2;
        }

        private boolean addWorker(Runnable var1, boolean var2) {
            while(true) {
                int var3 = this.ctl.get();
                int var4 = runStateOf(var3);
                if (var4 >= 0 && (var4 != 0 || var1 != null || this.workQueue.isEmpty())) {
                    return false;
                }

                while(true) {
                    int var5 = workerCountOf(var3);
                    if (var5 >= 536870911 || var5 >= (var2 ? this.corePoolSize : this.maximumPoolSize)) {
                        return false;
                    }

                    if (this.compareAndIncrementWorkerCount(var3)) {
                        boolean var18 = false;
                        boolean var19 = false;
                        java.util.concurrent.ThreadPoolExecutor.Worker var20 = null;

                        try {
                            var20 = new java.util.concurrent.ThreadPoolExecutor.Worker(var1);
                            Thread var6 = var20.thread;
                            if (var6 != null) {
                                ReentrantLock var7 = this.mainLock;
                                var7.lock();

                                try {
                                    int var8 = runStateOf(this.ctl.get());
                                    if (var8 < 0 || var8 == 0 && var1 == null) {
                                        if (var6.isAlive()) {
                                            throw new IllegalThreadStateException();
                                        }

                                        this.workers.add(var20);
                                        int var9 = this.workers.size();
                                        if (var9 > this.largestPoolSize) {
                                            this.largestPoolSize = var9;
                                        }

                                        var19 = true;
                                    }
                                } finally {
                                    var7.unlock();
                                }

                                if (var19) {
                                    var6.start();
                                    var18 = true;
                                }
                            }
                        } finally {
                            if (!var18) {
                                this.addWorkerFailed(var20);
                            }

                        }

                        return var18;
                    }

                    var3 = this.ctl.get();
                    if (runStateOf(var3) != var4) {
                        break;
                    }
                }
            }
        }

        private void addWorkerFailed(java.util.concurrent.ThreadPoolExecutor.Worker var1) {
            ReentrantLock var2 = this.mainLock;
            var2.lock();

            try {
                if (var1 != null) {
                    this.workers.remove(var1);
                }

                this.decrementWorkerCount();
                this.tryTerminate();
            } finally {
                var2.unlock();
            }

        }

        private void processWorkerExit(java.util.concurrent.ThreadPoolExecutor.Worker var1, boolean var2) {
            if (var2) {
                this.decrementWorkerCount();
            }

            ReentrantLock var3 = this.mainLock;
            var3.lock();

            try {
                this.completedTaskCount += var1.completedTasks;
                this.workers.remove(var1);
            } finally {
                var3.unlock();
            }

            this.tryTerminate();
            int var4 = this.ctl.get();
            if (runStateLessThan(var4, 536870912)) {
                if (!var2) {
                    int var5 = this.allowCoreThreadTimeOut ? 0 : this.corePoolSize;
                    if (var5 == 0 && !this.workQueue.isEmpty()) {
                        var5 = 1;
                    }

                    if (workerCountOf(var4) >= var5) {
                        return;
                    }
                }

                this.addWorker((Runnable)null, false);
            }

        }

        private Runnable getTask() {
            boolean var1 = false;

            while(true) {
                int var2 = this.ctl.get();
                int var3 = runStateOf(var2);
                if (var3 >= 0 && (var3 >= 536870912 || this.workQueue.isEmpty())) {
                    this.decrementWorkerCount();
                    return null;
                }

                int var4 = workerCountOf(var2);
                boolean var5 = this.allowCoreThreadTimeOut || var4 > this.corePoolSize;
                if (var4 <= this.maximumPoolSize && (!var5 || !var1) || var4 <= 1 && !this.workQueue.isEmpty()) {
                    try {
                        Runnable var6 = var5 ? (Runnable)this.workQueue.poll(this.keepAliveTime, TimeUnit.NANOSECONDS) : (Runnable)this.workQueue.take();
                        if (var6 != null) {
                            return var6;
                        }

                        var1 = true;
                    } catch (InterruptedException var7) {
                        var1 = false;
                    }
                } else if (this.compareAndDecrementWorkerCount(var2)) {
                    return null;
                }
            }
        }

        final void runWorker(java.util.concurrent.ThreadPoolExecutor.Worker var1) {
            Thread var2 = Thread.currentThread();
            Runnable var3 = var1.firstTask;
            var1.firstTask = null;
            var1.unlock();
            boolean var4 = true;

            try {
                while(var3 != null || (var3 = this.getTask()) != null) {
                    var1.lock();
                    if ((runStateAtLeast(this.ctl.get(), 536870912) || Thread.interrupted() && runStateAtLeast(this.ctl.get(), 536870912)) && !var2.isInterrupted()) {
                        var2.interrupt();
                    }

                    try {
                        this.beforeExecute(var2, var3);
                        Object var5 = null;

                        try {
                            var3.run();
                        } catch (RuntimeException var28) {
                            var5 = var28;
                            throw var28;
                        } catch (Error var29) {
                            var5 = var29;
                            throw var29;
                        } catch (Throwable var30) {
                            var5 = var30;
                            throw new Error(var30);
                        } finally {
                            this.afterExecute(var3, (Throwable)var5);
                        }
                    } finally {
                        var3 = null;
                        ++var1.completedTasks;
                        var1.unlock();
                    }
                }

                var4 = false;
            } finally {
                this.processWorkerExit(var1, var4);
            }

        }

        public ThreadPoolExecutor1(int var1, int var2, long var3, TimeUnit var5, BlockingQueue<Runnable> var6) {
            this(var1, var2, var3, var5, var6, Executors.defaultThreadFactory(), defaultHandler);
        }

        public ThreadPoolExecutor1(int var1, int var2, long var3, TimeUnit var5, BlockingQueue<Runnable> var6, ThreadFactory var7) {
            this(var1, var2, var3, var5, var6, var7, defaultHandler);
        }

        public ThreadPoolExecutor1(int var1, int var2, long var3, TimeUnit var5, BlockingQueue<Runnable> var6, RejectedExecutionHandler var7) {
            this(var1, var2, var3, var5, var6, Executors.defaultThreadFactory(), var7);
        }

        public ThreadPoolExecutor1(int var1, int var2, long var3, TimeUnit var5, BlockingQueue<Runnable> var6, ThreadFactory var7, RejectedExecutionHandler var8) {
            this.ctl = new AtomicInteger(ctlOf(-536870912, 0));
            this.mainLock = new ReentrantLock();
            this.workers = new HashSet();
            this.termination = this.mainLock.newCondition();
            if (var1 >= 0 && var2 > 0 && var2 >= var1 && var3 >= 0L) {
                if (var6 != null && var7 != null && var8 != null) {
                    this.acc = System.getSecurityManager() == null ? null : AccessController.getContext();
                    this.corePoolSize = var1;
                    this.maximumPoolSize = var2;
                    this.workQueue = var6;
                    this.keepAliveTime = var5.toNanos(var3);
                    this.threadFactory = var7;
                    this.handler = var8;
                } else {
                    throw new NullPointerException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        }

        public void execute(Runnable var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                int var2 = this.ctl.get();
                if (workerCountOf(var2) < this.corePoolSize) {
                    if (this.addWorker(var1, true)) {
                        return;
                    }

                    var2 = this.ctl.get();
                }

                if (isRunning(var2) && this.workQueue.offer(var1)) {
                    int var3 = this.ctl.get();
                    if (!isRunning(var3) && this.remove(var1)) {
                        this.reject(var1);
                    } else if (workerCountOf(var3) == 0) {
                        this.addWorker((Runnable)null, false);
                    }
                } else if (!this.addWorker(var1, false)) {
                    this.reject(var1);
                }

            }
        }

        public void shutdown() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            try {
                this.checkShutdownAccess();
                this.advanceRunState(0);
                this.interruptIdleWorkers();
                this.onShutdown();
            } finally {
                var1.unlock();
            }

            this.tryTerminate();
        }

        public List<Runnable> shutdownNow() {
            ReentrantLock var2 = this.mainLock;
            var2.lock();

            List var1;
            try {
                this.checkShutdownAccess();
                this.advanceRunState(536870912);
                this.interruptWorkers();
                var1 = this.drainQueue();
            } finally {
                var2.unlock();
            }

            this.tryTerminate();
            return var1;
        }

        public boolean isShutdown() {
            return !isRunning(this.ctl.get());
        }

        public boolean isTerminating() {
            int var1 = this.ctl.get();
            return !isRunning(var1) && runStateLessThan(var1, 1610612736);
        }

        public boolean isTerminated() {
            return runStateAtLeast(this.ctl.get(), 1610612736);
        }

        public boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException {
            long var4 = var3.toNanos(var1);
            ReentrantLock var6 = this.mainLock;
            var6.lock();

            boolean var7;
            try {
                while(!runStateAtLeast(this.ctl.get(), 1610612736)) {
                    if (var4 <= 0L) {
                        var7 = false;
                        return var7;
                    }

                    var4 = this.termination.awaitNanos(var4);
                }

                var7 = true;
            } finally {
                var6.unlock();
            }

            return var7;
        }

        protected void finalize() {
            SecurityManager var1 = System.getSecurityManager();
            if (var1 != null && this.acc != null) {
                PrivilegedAction var2 = () -> {
                    this.shutdown();
                    return null;
                };
                AccessController.doPrivileged(var2, this.acc);
            } else {
                this.shutdown();
            }

        }

        public void setThreadFactory(ThreadFactory var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                this.threadFactory = var1;
            }
        }

        public ThreadFactory getThreadFactory() {
            return this.threadFactory;
        }

        public void setRejectedExecutionHandler(RejectedExecutionHandler var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                this.handler = var1;
            }
        }

        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return this.handler;
        }

        public void setCorePoolSize(int var1) {
            if (var1 < 0) {
                throw new IllegalArgumentException();
            } else {
                int var2 = var1 - this.corePoolSize;
                this.corePoolSize = var1;
                if (workerCountOf(this.ctl.get()) > var1) {
                    this.interruptIdleWorkers();
                } else if (var2 > 0) {
                    int var3 = Math.min(var2, this.workQueue.size());

                    while(var3-- > 0 && this.addWorker((Runnable)null, true) && !this.workQueue.isEmpty()) {
                        ;
                    }
                }

            }
        }

        public int getCorePoolSize() {
            return this.corePoolSize;
        }

        public boolean prestartCoreThread() {
            return workerCountOf(this.ctl.get()) < this.corePoolSize && this.addWorker((Runnable)null, true);
        }

        void ensurePrestart() {
            int var1 = workerCountOf(this.ctl.get());
            if (var1 < this.corePoolSize) {
                this.addWorker((Runnable)null, true);
            } else if (var1 == 0) {
                this.addWorker((Runnable)null, false);
            }

        }

        public int prestartAllCoreThreads() {
            int var1;
            for(var1 = 0; this.addWorker((Runnable)null, true); ++var1) {
                ;
            }

            return var1;
        }

        public boolean allowsCoreThreadTimeOut() {
            return this.allowCoreThreadTimeOut;
        }

        public void allowCoreThreadTimeOut(boolean var1) {
            if (var1 && this.keepAliveTime <= 0L) {
                throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
            } else {
                if (var1 != this.allowCoreThreadTimeOut) {
                    this.allowCoreThreadTimeOut = var1;
                    if (var1) {
                        this.interruptIdleWorkers();
                    }
                }

            }
        }

        public void setMaximumPoolSize(int var1) {
            if (var1 > 0 && var1 >= this.corePoolSize) {
                this.maximumPoolSize = var1;
                if (workerCountOf(this.ctl.get()) > var1) {
                    this.interruptIdleWorkers();
                }

            } else {
                throw new IllegalArgumentException();
            }
        }

        public int getMaximumPoolSize() {
            return this.maximumPoolSize;
        }

        public void setKeepAliveTime(long var1, TimeUnit var3) {
            if (var1 < 0L) {
                throw new IllegalArgumentException();
            } else if (var1 == 0L && this.allowsCoreThreadTimeOut()) {
                throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
            } else {
                long var4 = var3.toNanos(var1);
                long var6 = var4 - this.keepAliveTime;
                this.keepAliveTime = var4;
                if (var6 < 0L) {
                    this.interruptIdleWorkers();
                }

            }
        }

        public long getKeepAliveTime(TimeUnit var1) {
            return var1.convert(this.keepAliveTime, TimeUnit.NANOSECONDS);
        }

        public BlockingQueue<Runnable> getQueue() {
            return this.workQueue;
        }

        public boolean remove(Runnable var1) {
            boolean var2 = this.workQueue.remove(var1);
            this.tryTerminate();
            return var2;
        }

        public void purge() {
            BlockingQueue var1 = this.workQueue;

            try {
                Iterator var2 = var1.iterator();

                while(var2.hasNext()) {
                    Runnable var8 = (Runnable)var2.next();
                    if (var8 instanceof Future && ((Future)var8).isCancelled()) {
                        var2.remove();
                    }
                }
            } catch (ConcurrentModificationException var7) {
                Object[] var3 = var1.toArray();
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Object var6 = var3[var5];
                    if (var6 instanceof Future && ((Future)var6).isCancelled()) {
                        var1.remove(var6);
                    }
                }
            }

            this.tryTerminate();
        }

        public int getPoolSize() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            int var2;
            try {
                var2 = runStateAtLeast(this.ctl.get(), 1073741824) ? 0 : this.workers.size();
            } finally {
                var1.unlock();
            }

            return var2;
        }

        public int getActiveCount() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            try {
                int var2 = 0;
                Iterator var3 = this.workers.iterator();

                while(var3.hasNext()) {
                    java.util.concurrent.ThreadPoolExecutor.Worker var4 = (java.util.concurrent.ThreadPoolExecutor.Worker)var3.next();
                    if (var4.isLocked()) {
                        ++var2;
                    }
                }

                int var8 = var2;
                return var8;
            } finally {
                var1.unlock();
            }
        }

        public int getLargestPoolSize() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            int var2;
            try {
                var2 = this.largestPoolSize;
            } finally {
                var1.unlock();
            }

            return var2;
        }

        public long getTaskCount() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            try {
                long var2 = this.completedTaskCount;
                Iterator var4 = this.workers.iterator();

                while(var4.hasNext()) {
                    java.util.concurrent.ThreadPoolExecutor.Worker var5 = (java.util.concurrent.ThreadPoolExecutor.Worker)var4.next();
                    var2 += var5.completedTasks;
                    if (var5.isLocked()) {
                        ++var2;
                    }
                }

                long var9 = var2 + (long)this.workQueue.size();
                return var9;
            } finally {
                var1.unlock();
            }
        }

        public long getCompletedTaskCount() {
            ReentrantLock var1 = this.mainLock;
            var1.lock();

            try {
                long var2 = this.completedTaskCount;

                java.util.concurrent.ThreadPoolExecutor.Worker var5;
                for(Iterator var4 = this.workers.iterator(); var4.hasNext(); var2 += var5.completedTasks) {
                    var5 = (java.util.concurrent.ThreadPoolExecutor.Worker)var4.next();
                }

                long var9 = var2;
                return var9;
            } finally {
                var1.unlock();
            }
        }

        public String toString() {
            ReentrantLock var5 = this.mainLock;
            var5.lock();

            long var1;
            int var3;
            int var4;
            try {
                var1 = this.completedTaskCount;
                var4 = 0;
                var3 = this.workers.size();
                Iterator var6 = this.workers.iterator();

                while(var6.hasNext()) {
                    java.util.concurrent.ThreadPoolExecutor.Worker var7 = (java.util.concurrent.ThreadPoolExecutor.Worker)var6.next();
                    var1 += var7.completedTasks;
                    if (var7.isLocked()) {
                        ++var4;
                    }
                }
            } finally {
                var5.unlock();
            }

            int var11 = this.ctl.get();
            String var12 = runStateLessThan(var11, 0) ? "Running" : (runStateAtLeast(var11, 1610612736) ? "Terminated" : "Shutting down");
            return super.toString() + "[" + var12 + ", pool size = " + var3 + ", active threads = " + var4 + ", queued tasks = " + this.workQueue.size() + ", completed tasks = " + var1 + "]";
        }

        protected void beforeExecute(Thread var1, Runnable var2) {
        }

        protected void afterExecute(Runnable var1, Throwable var2) {
        }

        protected void terminated() {
        }

        public static class DiscardOldestPolicy implements RejectedExecutionHandler {
            public DiscardOldestPolicy() {
            }

            public void rejectedExecution(Runnable var1, java.util.concurrent.ThreadPoolExecutor var2) {
                if (!var2.isShutdown()) {
                    var2.getQueue().poll();
                    var2.execute(var1);
                }

            }
        }

        public static class DiscardPolicy implements RejectedExecutionHandler {
            public DiscardPolicy() {
            }

            public void rejectedExecution(Runnable var1, java.util.concurrent.ThreadPoolExecutor var2) {
            }
        }

        public static class AbortPolicy implements RejectedExecutionHandler {
            public AbortPolicy() {
            }

            public void rejectedExecution(Runnable var1, java.util.concurrent.ThreadPoolExecutor var2) {
                throw new RejectedExecutionException("Task " + var1.toString() + " rejected from " + var2.toString());
            }
        }

        public static class CallerRunsPolicy implements RejectedExecutionHandler {
            public CallerRunsPolicy() {
            }

            public void rejectedExecution(Runnable var1, java.util.concurrent.ThreadPoolExecutor var2) {
                if (!var2.isShutdown()) {
                    var1.run();
                }

            }
        }

        private final class Worker extends AbstractQueuedSynchronizer implements Runnable {
            private static final long serialVersionUID = 6138294804551838833L;
            final Thread thread;
            Runnable firstTask;
            volatile long completedTasks;

            Worker(Runnable var2) {
                this.setState(-1);
                this.firstTask = var2;
                this.thread = java.util.concurrent.ThreadPoolExecutor.this.getThreadFactory().newThread(this);
            }

            public void run() {
                java.util.concurrent.ThreadPoolExecutor.this.runWorker(this);
            }

            protected boolean isHeldExclusively() {
                return this.getState() != 0;
            }

            protected boolean tryAcquire(int var1) {
                if (this.compareAndSetState(0, 1)) {
                    this.setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                } else {
                    return false;
                }
            }

            protected boolean tryRelease(int var1) {
                this.setExclusiveOwnerThread((Thread)null);
                this.setState(0);
                return true;
            }

            public void lock() {
                this.acquire(1);
            }

            public boolean tryLock() {
                return this.tryAcquire(1);
            }

            public void unlock() {
                this.release(1);
            }

            public boolean isLocked() {
                return this.isHeldExclusively();
            }

            void interruptIfStarted() {
                if (this.getState() >= 0) {
                    Thread var1 = this.thread;
                    if (this.thread != null && !var1.isInterrupted()) {
                        try {
                            var1.interrupt();
                        } catch (SecurityException var3) {
                            ;
                        }
                    }
                }

            }
        }
    }



}
*/
