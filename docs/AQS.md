### AbstractQueuedSynchronizer 详细解析 一切的基础 

共享锁流程 ：  获取共享同步状态
         若获取失败，则生成节点，并入队
         如果前驱为头结点，再次尝试获取共享同步状态
         获取成功则将自己设为头结点，如果后继节点是共享类型的，则唤醒
         若失败，将节点状态设为 SIGNAL，再次尝试。若再次失败，线程进入等待状态
         
         
         
独占锁流程：

调用 tryAcquire 方法尝试获取同步状态
获取成功，直接返回
获取失败，将线程封装到节点中，并将节点入队
入队节点在 acquireQueued 方法中自旋获取同步状态
若节点的前驱节点是头节点，则再次调用 tryAcquire 尝试获取同步状态
获取成功，当前节点将自己设为头节点并返回
获取失败，可能再次尝试，也可能会被阻塞。这里简单认为会被阻塞


#### 什么是AQS

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs0.png)

AQS的全称是AbstractQueuedSynchronizer，它的定位是为Java中几乎所有的锁和其他相关的同步装置提供一个基础框架

AQS是基于FIFO(first in first out )的队列实现的，并且内部维护了一个状态变量state，通过原子更新这个状态变量state即可以实现加锁解锁操作

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqsmind2.png)

只有程序中需要使用condition的时候才会用到condition队列程序中有可能存在多个

    1.使用node实现FIFO队列 可以用于构建锁或者其他同步装置的基础框架
    2.内部维护了一个状态变量state，通过原子更新这个状态变量state即可以实现加锁解锁操作  ReentrantLock 状态 0 表示没有线程取锁 1 表示已经获取锁 >1 表示重入锁数量
    3.AQS设计是一个模板方法的设计模式 子类使用都是继承并通过他的方法管理其状态（acquire 和 release）方法操作其状态
    4.AQS可以同时使用独占锁和共享锁
    
    独占锁和共享锁
    
    锁在一个时间点只能被一个线程占有。根据锁的获取机制，又分为公平锁和非公平锁等待队列中按照FIFO的原则获取锁，等待时间越长的线程越先获取到锁，这就是公平的获取锁，即公平锁。
    而非公平锁，线程获取的锁的时候，无视等待队列直接获取锁。ReentrantLock和ReentrantReadWriteLock.Writelock是独占锁
    
    共享锁：同一个时候能够被多个线程获取的锁，能被共享的锁。JUC包中ReentrantReadWriteLock.ReadLock，CyclicBarrier，CountDownLatch和Semaphore都是共享锁。
#### aqs实现的大致思路

##### aqs独占锁大致流程 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqsmind3.png)

AQS内部维护了一个CHL同步队列 来管理锁 线程首先会尝试获取锁如果失败就把当前线程以及一些等待信息包装成一个node节点加入到同步队列sync里面 接着会不断的循环尝试获取锁

条件是当前节点为head的直接后继才会尝试 如果失败就会阻塞自己 知道自己被唤醒 而当持有锁的线程释放锁的时候 会唤醒队列中的后继线程 基于这个设计思路aqs提供了许多子类

    比如 独占式锁 ReentrantLock ....和共享式锁 countdownlatch , Semaphore 都基于AbstractQueuedSynchronizer一个共同的基类
    根据AQS我们可以很简单的构建出自己的同步器

#### 核心源码

 在CHL中节点（Node）用来保存获取同步状态失败的线程（thread）、等待状态（waitStatus）、前驱节点（prev）和后继节点（next）

      AbstractQueuedSynchronizer 分析
      
      我们可以看到AQS的全称是AbstractQueuedSynchronizer，它本质上是一个抽象类，
      说明它本质上应该是需要子类来实现的，那么子类实现一个同步器需要实现哪些方法呢
      
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadsafe11.png)

      简单demo ExclusiveDemo 

#### AQS同步器结构与设置节点 

##### AQS节点基本结构

  节点（Node）是构成CHL的基础，同步器拥有首节点（head）和尾节点（tail）,没有成功获取同步状态的线程会构建成一个节点并加入到同步器的尾部CHL的基本结构如下

1.CHL基本结构

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs5.png)

2.CHL入列

当一个线程无法获取到锁的时候就会被构造成一个Node节点（包含当前线程.等待队列）来加入同步队列中等待得到锁的线程释放锁

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqsmind7.png)

      从数据结构上出发，入列是比较简单的，无非就是当前队列中的尾节点指向新节点，
      新节点的prev指向队列中的尾节点，然后将同步器的tail节点指向新节点
      
入列代码分析：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqsmind6.png)


3.CHL出列

    同步队列遵循FIFO规范，首节点的线程在释放同步状态后，将会唤醒后继节点的线程
    并且后继节点的线程在获取到同步状态后将会将自己设置为首节点
    因为设置首节点是通过获取同步状态成功的线程来完成的，因此设置头结点的方法并不需要使用CAS来保证
    因为只有一个线程能获取到同步状态

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqsmind5.png)


    要注意下面的enq()方法体里面有一个for死循环，这个就是自旋，一直做for循环，直到CAS,执行成功return t为止
    ①因为存在争用，可能多个线程入队，所以CAS操作来保证入队的原子性
    ②tai节点又是volatile的，保证可见性

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs7.png)


#### AQS独占式同步状态获取与释放

    AQS提供提供的独占式获取同步状态和释放的模板方法有：
    1.acquire(int arg);
    2.acquireInterruptibly(int arg)
    3.tryAcquireNanos(int arg, long nanosTimeout)
    4.release(int arg)
    5.tryRelease(int arg)
    
##### AQS独占式状态获取
 acquire(int args) 方法的作用是独占式的获取同步状态，
    该方法对中断不敏感，也就是说当线程获取同步状态失败后进入到CHL中，后续对线程进行中断时，线程不会从CHL中移除
    
    public final void acquire(int arg) {
         if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
             selfInterrupt();
    }

简单来写就是：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqsmind8.png)

    tryAcquire(arg)：尝试去获取同步状态，如果获取成功返回true,否则返回false。该方法是自定义同步器自己实现的方法，并且一定要保证线程安全。
    addWaiter(Node.EXCLUSIVE)：以独占的模式创建节点，并将节点添加到CHL的尾部。
    acquireQueued(addWaiter(Node.EXCLUSIVE), arg)：以死循环的方式获取同步状态

因为tryAcquire需要自定义同步器自己实现 所以 我们来看下 addWaiter(Node.EXCLUSIVE)方法和acquireQueued(final Node node, int arg)方法


            private Node addWaiter(Node mode) {
                 // 构建节点
                 Node node = new Node(Thread.currentThread(), mode);
                 // 尝试快速在尾部添加节点
                Node pred = tail;
                 if (pred != null) {
                     node.prev = pred;
                     if (compareAndSetTail(pred, node)) {
                         pred.next = node;
                        return node;
                    }
                }
                enq(node);
                return node;
            }

        private Node enq(final Node node) {
            for (;;) {
                Node t = tail;
                /**
                * 当CHL队列为空的时候，构建一个空节点作为头结点
                */
                if (t == null) {
                    if (compareAndSetHead(new Node()))
                        tail = head;
                } else {
                    // 将node 节点添加到CHL尾部
                    node.prev = t;
                    if (compareAndSetTail(t, node)) {
                        t.next = node;
                        return t;
                    }
                }
            }
        }
        // 自旋锁获取同步状态
        final boolean acquireQueued(final Node node, int arg) {
            boolean failed = true;
            try {
                boolean interrupted = false;
                for (;;) {
                    final Node p = node.predecessor();
                    // 只有当节点的前驱节点是同步器中个的head时,才有机会获取同步状态
                    if (p == head && tryAcquire(arg)) {
                        setHead(node);
                        p.next = null; // help GC
                        failed = false;
                        return interrupted;
                    }
                    if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                        interrupted = true;
                }
            } finally {
                if (failed)
                    cancelAcquire(node);
            }
        }
    
  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs7.png)
  
    上述的方法通过使用compareAndSetTail(pred, node)方法来确保节点能够被线程安全的添加到CHL尾部。
    在这里线程安全的添加到CHL是很重要的，如果不是线程安全的向CHL中添加节点，那么在一个线程获取到同步状态后，
    其它线程因为获取同步状态失败而并发的向CHL中添加节点时，CHL就不能保证数据的正确性了。
    acquireQueued(final Node node, int arg) 方法可以看出当前线程是“死循环”的尝试获取同步状态
    并且只有首节点才能获取同步状态。如果当前线程不是首节点则调用shouldParkAfterFailedAcquire(p, node)方法
    若果该方法返回true,则线程进入阻塞状态，知道线程被唤醒才会继续运行。我们来看下shouldParkAfterFailedAcquire(p, node)的源码
      
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        // 获取当前节点前驱节点的等待状态
        int ws = pred.waitStatus;
        /**
         * 如果前驱节点的状态值为-1，则返回true。标识当前node节点中的线程直接进入等待状态
         * 前面提到过 Node.SIGNAL的意思是当前驱节点释放同步状态后需要唤醒当前节点
         */
        if (ws ==  Node.SIGNAL)
            return true;
       /**
       * ws > 0 时，为Node.CANCLE，这个值标识当前节点因为中断或者取消，需要从CHL队列
       * 中移除，即将node的前面所有被标记为CANCLE状态的节点从CHL中移除
       */
       
       if (ws > 0) {
           do {
               node.prev = pred = pred.prev;
           } while (pred.waitStatus > 0);
          pred.next = node;
       } else {
           // CAS方式更新前驱节点的状态值为SIGNAL
           compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
       }
       return false;
    }  
      
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs3.png)

      
##### AQS独占式状态释放

当前线程在获取到同步状态并且执行完相关逻辑后，需要释放同步状态，并唤醒后继节点获取同步状态。release(int arg)方法定义如下：

        public final boolean release(int arg) {
            // 尝试释放同步状态
            if (tryRelease(arg)) {
                Node h = head;
                if (h != null && h.waitStatus != 0)
                    // 唤醒后继节点
                    unparkSuccessor(h);
                return true;
            }
           return false;
          }

tryRelease(arg)方法是自定义同步器实现的方法，如果释放同步状态成功，则通过unparkSuccessor(h)方法来唤醒后续

    
    private void unparkSuccessor(Node node) {
    //当前线程所在节点
            int ws = node.waitStatus;
            if (ws < 0)//当前线程所在节点状态允许失败
                compareAndSetWaitStatus(node, ws, 0);

        Node s = node.next;//找到下一个需要唤醒的节点
        if (s == null || s.waitStatus > 0) {如果为null 或者状态 取消
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);//唤醒
    }


总结一下 ：用unpark()唤醒等待队列中最前边的那个未放弃线程 ！  之后它还会进去acquireQueued 来搞事情 

    在多线程同时获取同步状态时，同步器会维护一个同步队列。线程在访问acquire(int arg)方法时会调用tryAcquire(int arg)方法，
    tryAcquire(int arg)方法是自定义同步器自己实现的一个线程安全的方法 ，所有只能有一个线程能够获取到同步状态，
    其余获取同步状态失败的线程将被包装成节点加入到同步队列中。并且同步队列中的所有节点全部是自旋的方式判断当前节点的前驱节点是否是首节点，
    如果是首节点则不停的获取同步状态，
    若果获取同步状态成功，则退出同步队列，当线程执行完相应逻辑后，会释放同步状态，释放后会唤醒其后继节点



#### AQS共享锁获取与释放

##### acquireShared
    比于独占式同一时刻只能有一个线程获取到同步状态，共享式在同一时刻可以有多个线程获取到同步状态。
    例如读写文件，读文件的时候可以多个线程同时访问，但是写文件的时候，同一时刻只能有一个线程进行写操作，其它线程将被阻塞
    AQS提供了acquireShared(int arg)模板方法来共享的获取同步状态，方法定义如下：
    
    public final void acquireShared(int arg) {
       if (tryAcquireShared(arg) < 0)
               // 获取同步状态失败，执行下面方法。
            doAcquireShared(arg);
    }
    
    方法首先调用了tryAcquireShared(arg) 方法尝试去获取同步状态，tryAcquireShared(arg) 方法的返回值是一个int值，
    当返回值大于等于0时，表示获取同步状态成功，
    否则获取同步状态失败。如果尝试获取同步状态失败，则调用 doAcquireShared(arg)自旋方法。自旋式获取同步状态方法的定义如下：
    
    private void doAcquireShared(int arg) {
         // 共享式节点,并添加到CHL尾部
         final Node node = addWaiter(Node.SHARED);
         boolean failed = true;
         try {
             boolean interrupted = false;
             for (;;) {
                 // 获取当前节点的前驱节点
                 final Node p = node.predecessor();
                // 判断前驱节点是否是头节点
               if (p == head) {
                    // 尝试获取同步状态
                    int r = tryAcquireShared(arg);
                    // 获取同步状态成功
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                       return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
    
    从上面自旋式获取同步状态的定义中，可以看到只有是当前节点的前驱节点是头节点时，才尝试获取同步状态，
    当返回值大于等于0时，表示获取到了同步状态并从自旋的过程中退出。acquireShared(int arg)方法对中断不敏感，
    与独占式相似也提供了响应中断的共享式获取同步状态方法acquireSharedInterruptibly(int arg)和超时等待共享获取同步状态方法 
    tryAcquireSharedNanos(int arg, long nanosTimeout)和独占式逻辑差不多 就不说了

##### releaseShared

与独占式一样，共享式也需要释放获取的同步状态。共享式释放同步状态的方法定义如下：

    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }
    
    该方法在释放同步状态后，将会唤醒后续处于等待状态的节点。
    因为存在多个线程同时释放同步状态，因此tryReleaseShared(arg)方法必须保证线程安全，一般是通过循环和CAS来完成的
    
#### ReentrantLock 锁讲解 独占锁

ReentrantLock独有得功能

            1.可指定式公平锁还是非公平锁
            2.提供了Condition类 可以分组唤醒需要唤醒得线程
            3.提供中断线程得机制lock.lockInterruptibly()
            
            
##### 流程

以ReentrantLock重入锁为例state初始化为0 表示未锁定状态 A线程lock()会调用tryAcquire 独占该锁并将state+1

以后 其他线程在tryAcquire()时就会失效 知道A线程unlock()到state=0（一直释放锁）为止 其他线程才有机会获取该锁

当然 释放锁之前 A线程自己是可以重复获取此锁得state会累加 这就是可重入概念 但是要注意获取多少次就要释放多少次才能回到0

##### 底层分析

###### 获取
首先我们看到得是一个sync这么一个抽象类 既然是抽象类那么一定有他得实现 FairSync(公平锁) NonfairSync(非公平锁)
    //transient不透明的 别的线程也就没法看见
    private transient Thread exclusiveOwnerThread;
     ==========================================================================
     1. tryAcquire先尝试获取"锁",获取了就不进入后续流程2. 
    获取AQS中的state变量,代表抽象概念的锁.
    
         protected final boolean tryAcquire(int acquires) {
                final Thread current = Thread.currentThread();
                //查看状态
                int c = getState();
                if (c == 0) {
               //值为0,那么当前独占性变量还未被线程占有
               //如果当前阻塞队列上没有先来的线程在等待,UnfairSync这里的实现就不一致 就不排队了 不直接扔到AQS队列 直接搞一梭子哪个线程
               CPU优先分给他了 就优先去操作 谁成功了谁就获得这把锁 设置成独占
               
               hasQueuedPredecessors
               加入了同步队列中当前节点是否有前驱节点的判断，如果该方法返回true，
               则表示有线程比当前线程更早地请求获取锁，因此需要等待前驱线程获取并释放锁之后才能继续获取锁。
               
                    if (!hasQueuedPredecessors() &&
                                //成功cas,那么代表当前线程获得该变量的所有权,也就是说成功获得锁
                        compareAndSetState(0, acquires)) {
                                    // setExclusiveOwnerThread将本线程设置为独占性变量所有者线程
                        setExclusiveOwnerThread(current);
                        return true;
                    }
                }
                
                //主要说明重入功能
                
                  //如果该线程已经获取了独占性变量的所有权,那么根据重入性
                   //原理,将state值进行加1,表示多次lock
                   //由于已经获得锁,该段代码只会被一个线程同时执行,所以不需要
                  //进行任何并行处理
                else if (current == getExclusiveOwnerThread()) {
                    int nextc = c + acquires;
                    if (nextc < 0)
                        throw new Error("Maximum lock count exceeded");
                    setState(nextc);
                    return true;
                }
                return false;
        }
        
        
###### 释放        
        
         protected final boolean tryRelease(int releases) {
                    int c = getState() - releases;
                    if (Thread.currentThread() != getExclusiveOwnerThread())
                        throw new IllegalMonitorStateException();
                    boolean free = false;
                    if (c == 0) {
                        free = true;
                        setExclusiveOwnerThread(null);
                    }
                    setState(c);
                    return free;
                }
                
                
#### CountDownLatch详解 共享锁

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/countdownlatch1.png)

##### 获取
    protected int tryAcquireShared(int acquires) {
                return (getState() == 0) ? 1 : -1;
            }
    判断 state 值是否为0，为0 返回1，否则返回 -1

private volatile int state;
在 CountDownLatch 中这个 state 值就是计数器，在调用 await 方法的时候，将值赋给 state

    
    private void doAcquireSharedInterruptibly(int arg)
            throws InterruptedException {
            //加入等待队列                      
            final Node node = addWaiter(Node.SHARED);
            boolean failed = true;
            // 进入 CAS 循环
            try {
                for (;;) {
                    //当一个节点(关联一个线程)进入等待队列后， 获取此节点的 prev 节点 
                    final Node p = node.predecessor();
                    // 如果获取到的 prev 是 head，也就是队列中第一个等待线程
                    if (p == head) {
                        // 再次尝试申请 反应到 CountDownLatch 就是查看是否还有线程需要等待(state是否为0)
                        int r = tryAcquireShared(arg);
                        // 如果 r >=0 说明 没有线程需要等待了 state==0
                        if (r >= 0) {
                            //尝试将第一个线程关联的节点设置为 head 
                            setHeadAndPropagate(node, r);
                            p.next = null; // help GC
                            failed = false;
                            return;
                        }
                    }
                    //经过自旋tryAcquireShared后，state还不为0，就会到这里，第一次的时候，waitStatus是0
                    那么node的waitStatus就会被置为SIGNAL，第二次再走到这里，就会用LockSupport的park方法把当前线程阻塞住
                    if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                        throw new InterruptedException();
                }
            } finally {
                if (failed)
                    cancelAcquire(node);
            }
        }
        
##### 释放

当执行 CountDownLatch 的 countDown（）方法，将计数器减一，也就是state减一，当减到0的时候
待队列中的线程被释放。是调用 AQS 的 releaseShared 方法来实现的
    
    // AQS类
    public final boolean releaseShared(int arg) {
            // arg 为固定值 1
            // 如果计数器state 为0 返回true，前提是调用 countDown() 之前不能已经为0
            if (tryReleaseShared(arg)) {
                // 唤醒等待队列的线程
                doReleaseShared();
                return true;
            }
            return false;
        }
    
    // CountDownLatch 重写的方法
    protected boolean tryReleaseShared(int releases) {
                // Decrement count; signal when transition to zero
                // 依然是循环+CAS配合 实现计数器减1
                for (;;) {
                    int c = getState();
                    if (c == 0)
                        return false;
                    int nextc = c-1;
                    if (compareAndSetState(c, nextc))
                        return nextc == 0;
                }
            }
    
    /// AQS类
     private void doReleaseShared() {
            for (;;) {
                Node h = head;
                if (h != null && h != tail) {
                    int ws = h.waitStatus;
                    // 如果节点状态为SIGNAL，则他的next节点也可以尝试被唤醒
                    if (ws == Node.SIGNAL) {
                        if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                            continue;            // loop to recheck cases
                        unparkSuccessor(h);
                    }
                    // 将节点状态设置为PROPAGATE，表示要向下传播，依次唤醒
                    else if (ws == 0 &&
                             !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                        continue;                // loop on failed CAS
                }
                if (h == head)                   // loop if head changed
                    break;
            }
        }
因为这是共享型的，当计数器为 0 后，会唤醒等待队列里的所有线程，所有调用了 await() 方法的线程都被唤醒，并发执行

这种情况对应到的场景是，有多个线程需要等待一些动作完成，比如一个线程完成初始化动作，其他5个线程都需要用到初始化的结果

那么在初始化线程调用 countDown 之前，其他5个线程都处在等待状态。一旦初始化线程调用了 countDown ，其他5个线程都被唤醒，开始执行。

总结
1、AQS 分为独占模式和共享模式，CountDownLatch 使用了它的共享模式。

2、AQS 当第一个等待线程（被包装为 Node）要入队的时候，要保证存在一个 head 节点，这个 head 节点不关联线程，也就是一个虚节点。

3、当队列中的等待节点（关联线程的，非 head 节点）抢到锁，将这个节点设置为 head 节点。

4、第一次自旋抢锁失败后，waitStatus 会被设置为 -1（SIGNAL），第二次再失败，就会被 LockSupport 阻塞挂起。

5、如果一个节点的前置节点为 SIGNAL 状态，则这个节点可以尝试抢占锁。