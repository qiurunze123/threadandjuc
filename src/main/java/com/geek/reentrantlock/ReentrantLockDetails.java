//package com.geek.reentrantlock;
//
//import java.util.Collection;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.AbstractQueuedSynchronizer;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.LockSupport;
//
///**
// * @author 邱润泽 bullock
// */
//
//public class ReentrantLockDetails implements Lock, java.io.Serializable {
//    /**
//     * Base of synchronization control for this lock. Subclassed
//     * into fair and nonfair versions below. Uses AQS state to
//     * represent the number of holds on the lock.
//     */
//    private final Sync sync;
//
//    abstract static class Sync extends AbstractQueuedSynchronizer {
//        private static final long serialVersionUID = -5179523762034025860L;
//
//        /**
//         * Performs {@link Lock#lock}. The main reason for subclassing
//         * is to allow fast path for nonfair version.
//         */
//        abstract void lock();
//
//        /**
//         *
//         */
//        final boolean nonfairTryAcquire(int acquires) {
//            final Thread current = Thread.currentThread();
//            int c = getState();
//            if (c == 0) {
//                if (compareAndSetState(0, acquires)) {
//                    setExclusiveOwnerThread(current);
//                    return true;
//                }
//            } else if (current == getExclusiveOwnerThread()) {
//                int nextc = c + acquires;
//                if (nextc < 0) // overflow
//                    throw new Error("Maximum lock count exceeded");
//                setState(nextc);
//                return true;
//            }
//            return false;
//        }
//
//
//        @Override
//        protected final boolean tryRelease(int releases) {
//            int c = getState() - releases;
//            if (Thread.currentThread() != getExclusiveOwnerThread())
//                throw new IllegalMonitorStateException();
//            boolean free = false;
//            if (c == 0) {
//                free = true;
//                setExclusiveOwnerThread(null);
//            }
//            setState(c);
//            return free;
//        }
//
//        protected final boolean isHeldExclusively() {
//            // While we must in general read state before owner,
//            // we don't need to do so to check if current thread is owner
//            return getExclusiveOwnerThread() == Thread.currentThread();
//        }
//
//        final ConditionObject newCondition() {
//            return new ConditionObject();
//        }
//
//        // Methods relayed from outer class
//
//        final Thread getOwner() {
//            return getState() == 0 ? null : getExclusiveOwnerThread();
//        }
//
//        final int getHoldCount() {
//            return isHeldExclusively() ? getState() : 0;
//        }
//
//        final boolean isLocked() {
//            return getState() != 0;
//        }
//
//        /**
//         * Reconstitutes the instance from a stream (that is, deserializes it).
//         */
//        private void readObject(java.io.ObjectInputStream s)
//                throws java.io.IOException, ClassNotFoundException {
//            s.defaultReadObject();
//            setState(0); // reset to unlocked state
//        }
//    }
//
//    /**
//     * Sync object for non-fair locks
//     */
//    static final class NonfairSync extends Sync {
//        private static final long serialVersionUID = 7316153563782823691L;
//
//        /**
//         * Performs lock.  Try immediate barge, backing up to normal
//         * acquire on failure.
//         */
//        @Override
//        final void lock() {
//            if (compareAndSetState(0, 1))
//                setExclusiveOwnerThread(Thread.currentThread());
//            else
//                acquire(1);
//        }
//
//        protected final boolean tryAcquire(int acquires) {
//            return nonfairTryAcquire(acquires);
//        }
//    }
//
//    /**
//     * Sync object for fair locks
//     */
//    static final class FairSync extends Sync {
//        private static final long serialVersionUID = -3000897897090466540L;
//
//
//
//        /**
//         * (01) 先是通过tryAcquire()尝试获取锁。获取成功的话，直接返回；尝试失败的话，再通过acquireQueued()获取锁。
//         * (02) 尝试失败的情况下，会先通过addWaiter()来将“当前线程”加入到"CLH队列"末尾；然后调用acquireQueued()，
//         * 在CLH队列中排序等待获取锁，在此过程中，线程处于休眠状态。直到获取锁了才返回 如果在休眠等待过程中被中断过，
//         * 则调用selfInterrupt()来自己产生一个中断。
//         *
//         *
//         *
//         * Node是CLH队列的节点，代表“等待锁的线程队列”。
//         * (01) 每个Node都会一个线程对应。
//         * (02) 每个Node会通过prev和next分别指向上一个节点和下一个节点，这分别代表上一个等待线程和下一个等待线程。
//         * (03) Node通过waitStatus保存线程的等待状态。
//         * (04) Node通过nextWaiter来区分线程是“独占锁”线程还是“共享锁”线程。如果是“独占锁”线程，
//         * 则nextWaiter的值为EXCLUSIVE；如果是“共享锁”线程，则nextWaiter的值是SHARED。
//         *
//         * @param arg
//         */
//
//
//        @Override
//        final void lock() {
//            //独占锁 锁可以获取得标志 初始的状态 就是 state 0  当初次获取到锁得时候 就变为了1
//            //又因为是可重入 所以每一次 进来获取都会 +1
//            acquire(1);
//
//        }
//
//
//        public final void acquire(int arg) {
//            // 1 tryAcquire 尝试获取锁 如果成功则直接返回
//            // 2 acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) addWaiter(Node.EXCLUSIVE) 加入CLH 线程等待队列
//            // 3 acquireQueued 执行获取锁 根据公平性获取锁
//            // 4 当前线程”在执行acquireQueued()时，会进入到CLH队列中休眠等待，直到获取锁了才返回！
//            //    如果“当前线程”在休眠等待过程中被中断过，acquireQueued 返回true
//            // 5 如果被中断过 selfInterrupt 就会给自己一个中断
//            if (!tryAcquire(arg) &&
//                acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
//                selfInterrupt();
//        }
//
//
//        /**             selfInterrupt
//         *  线程的thread.interrupt()方法是中断线程，将会设置该线程的中断状态位，即设置为true，
//         *  中断的结果线程是死亡、还是等待新的任务或是继续运行至下一步，就取决于这个程序本身。
//         * 线程会不时地检测这个中断标示位，以判断线程是否应该被中断（中断标示值是否为true）。它并不像stop方法那样会中断一个正在运行的线程。
//         *
//         *
//         * 在acquireQueued()中，即使是线程在阻塞状态被中断唤醒而获取到cpu执行权利；
//         * 但是，如果该线程的前面还有其它等待锁的线程，根据公平性原则，该线程依然无法获取到锁。它会再次阻塞！
//         * 该线程再次阻塞，直到该线程被它的前面等待锁的线程锁唤醒；线程才会获取锁，然后“真正执行起来”！
//         * 也就是说，在该线程“成功获取锁并真正执行起来”之前，它的中断会被忽略并且中断标记会被清除！
//         * 因为在parkAndCheckInterrupt()中，我们线程的中断状态时调用了Thread.interrupted()
//         * 该函数不同于Thread的isInterrupted()函数，isInterrupted()仅仅返回中断状态，
//         * 而interrupted()在返回当前中断状态之后，还会清除中断状态。 正因为之前的中断状态被清除了，所以这里需要调用selfInterrupt()重新产生一个中断！
//         *
//         *
//         *
//         * !!!!!!! 为该意义   如果线程在等待过程中被中断过，它是不响应的。只是获取资源后才再进行自我中断selfInterrupt()，将中断补上。
//         * @param mode
//         * @return
//         */
//
//
//        private Node addWaiter(Node mode) {
//            // 创建一个新的节点节点对应当前线程 当前线程模型是mode
//            Node node = new Node(Thread.currentThread(), mode);
//            Node pred = tail;
//            //如果当前线程不为null  则把当前线程添加到CLH末尾
//            if (pred != null) {
//                node.prev = pred;
//                //加入队列
//                if (compareAndSetTail(pred, node)) {
//                    pred.next = node;
//                    return node;
//                }
//            }
//            //若队列为空  则 创建一个新的队列 再将当前线程加入队列
//            enq(node);
//            return node;
//        }
//
//
//        private Node enq(final Node node) {
//            for (;;) {
//                Node t = tail;
//                if (t == null) { // Must initialize
//                    if (compareAndSetHead(new Node()))
//                        tail = head;
//                } else {
//                    node.prev = t;
//                    if (compareAndSetTail(t, node)) {
//                        t.next = node;
//                        return t;
//                    }
//                }
//            }
//        }
//
//
//        final boolean acquireQueued(final Node node, int arg) {
//            boolean failed = true;
//            try {
//                boolean interrupted = false;
//                for (;;) {
//                    // 获取前置节点
//                    final Node p = node.predecessor();
//                    if (p == head && tryAcquire(arg)) {
//                        setHead(node);
//                        p.next = null; // help GC
//                        failed = false;
//                        return interrupted;
//                    }
//                    /**
//                     * houldParkAfterFailedAcquire()通过以下规则，判断“当前线程”是否需要被阻塞。
//                     *
//                     * 规则1：如果前继节点状态为SIGNAL，表明当前节点需要被unpark(唤醒)，此时则返回true。
//                     * 规则2：如果前继节点状态为CANCELLED(ws>0)，说明前继节点已经被取消，则通过先前回溯找到一个有效(非CANCELLED状态)的节点，并返回false。
//                     * 规则3：如果前继节点状态为非SIGNAL、非CANCELLED，则设置前继的状态为SIGNAL，并返回false。
//                     *
//                     *
//                     * parkAndCheckInterrupt
//                     *
//                     * parkAndCheckInterrupt()的作用是阻塞当前线程，并且返回“线程被唤醒之后”的中断状态。
//                     * 它会先通过LockSupport.park()阻塞“当前线程”，然后通过Thread.interrupted()返回线程的中断状态。
//                     *
//                     * 这里介绍一下线程被阻塞之后如何唤醒。一般有2种情况：
//                     * 第1种情况：unpark()唤醒。“前继节点对应的线程”使用完锁之后，通过unpark()方式唤醒当前线程。
//                     * 第2种情况：中断唤醒。其它线程通过interrupt()中断当前线程。
//                     */
//                    if (shouldParkAfterFailedAcquire(p, node) &&
//                            parkAndCheckInterrupt())
//                        interrupted = true;
//                }
//            } finally {
//                if (failed)
//                    cancelAcquire(node);
//            }
//        }
//
//
//        private final boolean parkAndCheckInterrupt() {
//            LockSupport.park(this);
//            return Thread.interrupted(); //返回当前中断状态 并清除 中断状态
//        }
//        /**
//         * Fair version of tryAcquire.  Don't grant access unless
//         * recursive call or no waiters or is first.
//         */
//        protected final boolean tryAcquire(int acquires) {
//            final Thread current = Thread.currentThread();
//            int c = getState();//volatile 修饰资源变量
//            if (c == 0) {
//                if (!hasQueuedPredecessors()//判断当前线程是不是CHL队首 来判断有没有比等待更久得线程
//                        &&
//                        compareAndSetState(0, acquires)) {//如果是当前线程则利用cas获取资源
//                    setExclusiveOwnerThread(current);//设置锁得拥有者为当前线程
//                    return true;
//                }
//            } else if (current == getExclusiveOwnerThread()) {//如果独占锁得拥有者为当前线程 则更新锁得状态 计数
//                int nextc = c + acquires;
//                if (nextc < 0)
//                    throw new Error("Maximum lock count exceeded");
//                setState(nextc); // 可重入  重入多少次需要释放多少次
//                return true;
//            }
//            return false;
//        }
//    }
//    public final boolean hasQueuedPredecessors() {
//        Node t = tail;
//        Node h = head;
//        Node s;
//        return h != t &&
//                ((s = h.next) == null || s.thread != Thread.currentThread());
//    }
//
//
//
//
//
//
//
//    /**
//     * Creates an instance of {@code ReentrantLockDetails}.
//     * This is equivalent to using {@code ReentrantLockDetails(false)}.
//     */
//    public ReentrantLockDetails() {
//        sync = new NonfairSync();
//    }
//
//    /**
//     * Creates an instance of {@code ReentrantLockDetails} with the
//     * given fairness policy.
//     *
//     * @param fair {@code true} if this lock should use a fair ordering policy
//     */
//    public ReentrantLockDetails(boolean fair) {
//        sync = fair ? new FairSync() : new NonfairSync();
//    }
//
//    /**
//     * Acquires the lock.
//     *
//     * <p>Acquires the lock if it is not held by another thread and returns
//     * immediately, setting the lock hold count to one.
//     *
//     * <p>If the current thread already holds the lock then the hold
//     * count is incremented by one and the method returns immediately.
//     *
//     * <p>If the lock is held by another thread then the
//     * current thread becomes disabled for thread scheduling
//     * purposes and lies dormant until the lock has been acquired,
//     * at which time the lock hold count is set to one.
//     */
//    public void lock() {
//        sync.lock();
//    }
//
//    /**
//     * Acquires the lock unless the current thread is
//     * {@linkplain Thread#interrupt interrupted}.
//     *
//     * <p>Acquires the lock if it is not held by another thread and returns
//     * immediately, setting the lock hold count to one.
//     *
//     * <p>If the current thread already holds this lock then the hold count
//     * is incremented by one and the method returns immediately.
//     *
//     * <p>If the lock is held by another thread then the
//     * current thread becomes disabled for thread scheduling
//     * purposes and lies dormant until one of two things happens:
//     *
//     * <ul>
//     *
//     * <li>The lock is acquired by the current thread; or
//     *
//     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
//     * current thread.
//     *
//     * </ul>
//     *
//     * <p>If the lock is acquired by the current thread then the lock hold
//     * count is set to one.
//     *
//     * <p>If the current thread:
//     *
//     * <ul>
//     *
//     * <li>has its interrupted status set on entry to this method; or
//     *
//     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring
//     * the lock,
//     *
//     * </ul>
//     * <p>
//     * then {@link InterruptedException} is thrown and the current thread's
//     * interrupted status is cleared.
//     *
//     * <p>In this implementation, as this method is an explicit
//     * interruption point, preference is given to responding to the
//     * interrupt over normal or reentrant acquisition of the lock.
//     *
//     * @throws InterruptedException if the current thread is interrupted
//     */
//    public void lockInterruptibly() throws InterruptedException {
//        sync.acquireInterruptibly(1);
//    }
//
//    /**
//     * Acquires the lock only if it is not held by another thread at the time
//     * of invocation.
//     *
//     * <p>Acquires the lock if it is not held by another thread and
//     * returns immediately with the value {@code true}, setting the
//     * lock hold count to one. Even when this lock has been set to use a
//     * fair ordering policy, a call to {@code tryLock()} <em>will</em>
//     * immediately acquire the lock if it is available, whether or not
//     * other threads are currently waiting for the lock.
//     * This &quot;barging&quot; behavior can be useful in certain
//     * circumstances, even though it breaks fairness. If you want to honor
//     * the fairness setting for this lock, then use
//     * {@link #tryLock(long, TimeUnit) tryLock(0, TimeUnit.SECONDS) }
//     * which is almost equivalent (it also detects interruption).
//     *
//     * <p>If the current thread already holds this lock then the hold
//     * count is incremented by one and the method returns {@code true}.
//     *
//     * <p>If the lock is held by another thread then this method will return
//     * immediately with the value {@code false}.
//     *
//     * @return {@code true} if the lock was free and was acquired by the
//     * current thread, or the lock was already held by the current
//     * thread; and {@code false} otherwise
//     */
//    public boolean tryLock() {
//        return sync.nonfairTryAcquire(1);
//    }
//
//    /**
//     * Acquires the lock if it is not held by another thread within the given
//     * waiting time and the current thread has not been
//     * {@linkplain Thread#interrupt interrupted}.
//     *
//     * <p>Acquires the lock if it is not held by another thread and returns
//     * immediately with the value {@code true}, setting the lock hold count
//     * to one. If this lock has been set to use a fair ordering policy then
//     * an available lock <em>will not</em> be acquired if any other threads
//     * are waiting for the lock. This is in contrast to the {@link #tryLock()}
//     * method. If you want a timed {@code tryLock} that does permit barging on
//     * a fair lock then combine the timed and un-timed forms together:
//     *
//     * <pre> {@code
//     * if (lock.tryLock() ||
//     *     lock.tryLock(timeout, unit)) {
//     *   ...
//     * }}</pre>
//     *
//     * <p>If the current thread
//     * already holds this lock then the hold count is incremented by one and
//     * the method returns {@code true}.
//     *
//     * <p>If the lock is held by another thread then the
//     * current thread becomes disabled for thread scheduling
//     * purposes and lies dormant until one of three things happens:
//     *
//     * <ul>
//     *
//     * <li>The lock is acquired by the current thread; or
//     *
//     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
//     * the current thread; or
//     *
//     * <li>The specified waiting time elapses
//     *
//     * </ul>
//     *
//     * <p>If the lock is acquired then the value {@code true} is returned and
//     * the lock hold count is set to one.
//     *
//     * <p>If the current thread:
//     *
//     * <ul>
//     *
//     * <li>has its interrupted status set on entry to this method; or
//     *
//     * <li>is {@linkplain Thread#interrupt interrupted} while
//     * acquiring the lock,
//     *
//     * </ul>
//     * then {@link InterruptedException} is thrown and the current thread's
//     * interrupted status is cleared.
//     *
//     * <p>If the specified waiting time elapses then the value {@code false}
//     * is returned.  If the time is less than or equal to zero, the method
//     * will not wait at all.
//     *
//     * <p>In this implementation, as this method is an explicit
//     * interruption point, preference is given to responding to the
//     * interrupt over normal or reentrant acquisition of the lock, and
//     * over reporting the elapse of the waiting time.
//     *
//     * @param timeout the time to wait for the lock
//     * @param unit    the time unit of the timeout argument
//     * @return {@code true} if the lock was free and was acquired by the
//     * current thread, or the lock was already held by the current
//     * thread; and {@code false} if the waiting time elapsed before
//     * the lock could be acquired
//     * @throws InterruptedException if the current thread is interrupted
//     * @throws NullPointerException if the time unit is null
//     */
//    public boolean tryLock(long timeout, TimeUnit unit)
//            throws InterruptedException {
//        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
//    }
//
//    /**
//     * Attempts to release this lock.
//     *
//     * <p>If the current thread is the holder of this lock then the hold
//     * count is decremented.  If the hold count is now zero then the lock
//     * is released.  If the current thread is not the holder of this
//     * lock then {@link IllegalMonitorStateException} is thrown.
//     *
//     * @throws IllegalMonitorStateException if the current thread does not
//     *                                      hold this lock
//     */
//    public void unlock() {
//        sync.release(1);
//    }
//
//    /**
//     * Returns a {@link Condition} instance for use with this
//     * {@link Lock} instance.
//     *
//     * <p>The returned {@link Condition} instance supports the same
//     * usages as do the {@link Object} monitor methods ({@link
//     * Object#wait() wait}, {@link Object#notify notify}, and {@link
//     * Object#notifyAll notifyAll}) when used with the built-in
//     * monitor lock.
//     *
//     * <ul>
//     *
//     * <li>If this lock is not held when any of the {@link Condition}
//     * {@linkplain Condition#await() waiting} or {@linkplain
//     * Condition#signal signalling} methods are called, then an {@link
//     * IllegalMonitorStateException} is thrown.
//     *
//     * <li>When the condition {@linkplain Condition#await() waiting}
//     * methods are called the lock is released and, before they
//     * return, the lock is reacquired and the lock hold count restored
//     * to what it was when the method was called.
//     *
//     * <li>If a thread is {@linkplain Thread#interrupt interrupted}
//     * while waiting then the wait will terminate, an {@link
//     * InterruptedException} will be thrown, and the thread's
//     * interrupted status will be cleared.
//     *
//     * <li> Waiting threads are signalled in FIFO order.
//     *
//     * <li>The ordering of lock reacquisition for threads returning
//     * from waiting methods is the same as for threads initially
//     * acquiring the lock, which is in the default case not specified,
//     * but for <em>fair</em> locks favors those threads that have been
//     * waiting the longest.
//     *
//     * </ul>
//     *
//     * @return the Condition object
//     */
//    public Condition newCondition() {
//        return sync.newCondition();
//    }
//
//    /**
//     * Queries the number of holds on this lock by the current thread.
//     *
//     * <p>A thread has a hold on a lock for each lock action that is not
//     * matched by an unlock action.
//     *
//     * <p>The hold count information is typically only used for testing and
//     * debugging purposes. For example, if a certain section of code should
//     * not be entered with the lock already held then we can assert that
//     * fact:
//     *
//     * <pre> {@code
//     * class X {
//     *   ReentrantLockDetails lock = new ReentrantLockDetails();
//     *   // ...
//     *   public void m() {
//     *     assert lock.getHoldCount() == 0;
//     *     lock.lock();
//     *     try {
//     *       // ... method body
//     *     } finally {
//     *       lock.unlock();
//     *     }
//     *   }
//     * }}</pre>
//     *
//     * @return the number of holds on this lock by the current thread,
//     * or zero if this lock is not held by the current thread
//     */
//    public int getHoldCount() {
//        return sync.getHoldCount();
//    }
//
//    /**
//     * Queries if this lock is held by the current thread.
//     *
//     * <p>Analogous to the {@link Thread#holdsLock(Object)} method for
//     * built-in monitor locks, this method is typically used for
//     * debugging and testing. For example, a method that should only be
//     * called while a lock is held can assert that this is the case:
//     *
//     * <pre> {@code
//     * class X {
//     *   ReentrantLockDetails lock = new ReentrantLockDetails();
//     *   // ...
//     *
//     *   public void m() {
//     *       assert lock.isHeldByCurrentThread();
//     *       // ... method body
//     *   }
//     * }}</pre>
//     *
//     * <p>It can also be used to ensure that a reentrant lock is used
//     * in a non-reentrant manner, for example:
//     *
//     * <pre> {@code
//     * class X {
//     *   ReentrantLockDetails lock = new ReentrantLockDetails();
//     *   // ...
//     *
//     *   public void m() {
//     *       assert !lock.isHeldByCurrentThread();
//     *       lock.lock();
//     *       try {
//     *           // ... method body
//     *       } finally {
//     *           lock.unlock();
//     *       }
//     *   }
//     * }}</pre>
//     *
//     * @return {@code true} if current thread holds this lock and
//     * {@code false} otherwise
//     */
//    public boolean isHeldByCurrentThread() {
//        return sync.isHeldExclusively();
//    }
//
//    /**
//     * Queries if this lock is held by any thread. This method is
//     * designed for use in monitoring of the system state,
//     * not for synchronization control.
//     *
//     * @return {@code true} if any thread holds this lock and
//     * {@code false} otherwise
//     */
//    public boolean isLocked() {
//        return sync.isLocked();
//    }
//
//    /**
//     * Returns {@code true} if this lock has fairness set true.
//     *
//     * @return {@code true} if this lock has fairness set true
//     */
//    public final boolean isFair() {
//        return sync instanceof FairSync;
//    }
//
//    /**
//     * Returns the thread that currently owns this lock, or
//     * {@code null} if not owned. When this method is called by a
//     * thread that is not the owner, the return value reflects a
//     * best-effort approximation of current lock status. For example,
//     * the owner may be momentarily {@code null} even if there are
//     * threads trying to acquire the lock but have not yet done so.
//     * This method is designed to facilitate construction of
//     * subclasses that provide more extensive lock monitoring
//     * facilities.
//     *
//     * @return the owner, or {@code null} if not owned
//     */
//    protected Thread getOwner() {
//        return sync.getOwner();
//    }
//
//    /**
//     * Queries whether any threads are waiting to acquire this lock. Note that
//     * because cancellations may occur at any time, a {@code true}
//     * return does not guarantee that any other thread will ever
//     * acquire this lock.  This method is designed primarily for use in
//     * monitoring of the system state.
//     *
//     * @return {@code true} if there may be other threads waiting to
//     * acquire the lock
//     */
//    public final boolean hasQueuedThreads() {
//        return sync.hasQueuedThreads();
//    }
//
//    /**
//     * Queries whether the given thread is waiting to acquire this
//     * lock. Note that because cancellations may occur at any time, a
//     * {@code true} return does not guarantee that this thread
//     * will ever acquire this lock.  This method is designed primarily for use
//     * in monitoring of the system state.
//     *
//     * @param thread the thread
//     * @return {@code true} if the given thread is queued waiting for this lock
//     * @throws NullPointerException if the thread is null
//     */
//    public final boolean hasQueuedThread(Thread thread) {
//        return sync.isQueued(thread);
//    }
//
//    /**
//     * Returns an estimate of the number of threads waiting to
//     * acquire this lock.  The value is only an estimate because the number of
//     * threads may change dynamically while this method traverses
//     * internal data structures.  This method is designed for use in
//     * monitoring of the system state, not for synchronization
//     * control.
//     *
//     * @return the estimated number of threads waiting for this lock
//     */
//    public final int getQueueLength() {
//        return sync.getQueueLength();
//    }
//
//    /**
//     * Returns a collection containing threads that may be waiting to
//     * acquire this lock.  Because the actual set of threads may change
//     * dynamically while constructing this result, the returned
//     * collection is only a best-effort estimate.  The elements of the
//     * returned collection are in no particular order.  This method is
//     * designed to facilitate construction of subclasses that provide
//     * more extensive monitoring facilities.
//     *
//     * @return the collection of threads
//     */
//    protected Collection<Thread> getQueuedThreads() {
//        return sync.getQueuedThreads();
//    }
//
//    /**
//     * Queries whether any threads are waiting on the given condition
//     * associated with this lock. Note that because timeouts and
//     * interrupts may occur at any time, a {@code true} return does
//     * not guarantee that a future {@code signal} will awaken any
//     * threads.  This method is designed primarily for use in
//     * monitoring of the system state.
//     *
//     * @param condition the condition
//     * @return {@code true} if there are any waiting threads
//     * @throws IllegalMonitorStateException if this lock is not held
//     * @throws IllegalArgumentException     if the given condition is
//     *                                      not associated with this lock
//     * @throws NullPointerException         if the condition is null
//     */
//    public boolean hasWaiters(Condition condition) {
//        if (condition == null)
//            throw new NullPointerException();
//        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
//            throw new IllegalArgumentException("not owner");
//        return sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject) condition);
//    }
//
//    /**
//     * Returns an estimate of the number of threads waiting on the
//     * given condition associated with this lock. Note that because
//     * timeouts and interrupts may occur at any time, the estimate
//     * serves only as an upper bound on the actual number of waiters.
//     * This method is designed for use in monitoring of the system
//     * state, not for synchronization control.
//     *
//     * @param condition the condition
//     * @return the estimated number of waiting threads
//     * @throws IllegalMonitorStateException if this lock is not held
//     * @throws IllegalArgumentException     if the given condition is
//     *                                      not associated with this lock
//     * @throws NullPointerException         if the condition is null
//     */
//    public int getWaitQueueLength(Condition condition) {
//        if (condition == null)
//            throw new NullPointerException();
//        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
//            throw new IllegalArgumentException("not owner");
//        return sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject) condition);
//    }
//
//    /**
//     * Returns a collection containing those threads that may be
//     * waiting on the given condition associated with this lock.
//     * Because the actual set of threads may change dynamically while
//     * constructing this result, the returned collection is only a
//     * best-effort estimate. The elements of the returned collection
//     * are in no particular order.  This method is designed to
//     * facilitate construction of subclasses that provide more
//     * extensive condition monitoring facilities.
//     *
//     * @param condition the condition
//     * @return the collection of threads
//     * @throws IllegalMonitorStateException if this lock is not held
//     * @throws IllegalArgumentException     if the given condition is
//     *                                      not associated with this lock
//     * @throws NullPointerException         if the condition is null
//     */
//    protected Collection<Thread> getWaitingThreads(Condition condition) {
//        if (condition == null)
//            throw new NullPointerException();
//        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
//            throw new IllegalArgumentException("not owner");
//        return sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject) condition);
//    }
//
//    /**
//     * Returns a string identifying this lock, as well as its lock state.
//     * The state, in brackets, includes either the String {@code "Unlocked"}
//     * or the String {@code "Locked by"} followed by the
//     * {@linkplain Thread#getName name} of the owning thread.
//     *
//     * @return a string identifying this lock, as well as its lock state
//     */
//    public String toString() {
//        Thread o = sync.getOwner();
//        return super.toString() + ((o == null) ?
//                "[Unlocked]" :
//                "[Locked by thread " + o.getName() + "]");
//    }
//}
//
//}
