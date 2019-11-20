### AbstractQueuedSynchronizer 详细解析 一切的基础 

| ID | Problem  |
| --- | ---   |
| 重点 |什么是AQS|
| 基础 |AQS锁类别与在使用者|
| 了解 |AQS同步器的结构与设置节点||
| 000 |AQS队列结构节点和同步队列|
| 001 |AQS使用方式和设计模式|
| 002 |AQS独占锁共享锁子类覆盖流程|
| 003 |AQS独占式同步状态获取与释放|
| 004 |AQS方法解读|
| 005 |AQS节点在同步队列的增加和移出|
| 006 |ReentrantLock分析(等待更新)|
| 007 |ReadAndWriteLoak分析(等待更新)|
| 008 |Condition分析(等待更新)|


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
    


#### AQS队列结构节点和同步队列 

使用volatile修饰保证线程可见性

AQS == AbstractQueuedSynchronizer, 队列同步器，它是Java并发用来构建锁和其他同步组件的基础框架 在内部有一个FIFO（先进先出队列）

同步器应用 子类通过继承同步器来实现一些抽象方法，子类主要利用同步器提供的方法来进行状态 state 修改

修改状态的三种方法：

**getState() 获取当前的同步状态**

**setState(int newState) 设置当前同步状态**

**compareAndSetState(int expect,int update) 使用CAS设置当前状态该方法能够保证状态设置的原子性**

#### AQS使用方式和设计模式 

AQS使用的是模板模式（父类定义一个骨架子类去实现在调用的时候调用父类的方法就可以了）子类继承父类然后重写指定的方法

AQS中需要重写的方法有哪些呢？

    protected boolean tryAcquire(int arg) : 独占式获取同步状态，试着获取，成功返回true，反之为false
    protected boolean tryRelease(int arg) ：独占式释放同步状态，等待中的其他线程此时将有机会获取到同步状态；
    protected int tryAcquireShared(int arg) ：共享式获取同步状态，返回值大于等于0，代表获取成功；反之获取失败；
    protected boolean tryReleaseShared(int arg) ：共享式释放同步状态，成功为true，失败为false
    protected boolean isHeldExclusively() ： 是否在独占模式下被线程占用。
 
#### AQS独占锁共享锁子类覆盖流程

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs2.png)

已reentrantLock为例 子类继承AbstractQueuedSynchronizer 之后来实现对应需要重写的方法

#### AQS独占式同步状态获取与释放

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs4.png)

此图为独占式同步状态获取释放的全部流程 

#### 独占式锁的获取 图示 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs7.png)

整体流程： 

调用同步器的acquire(int arg)方法可以获取同步状态，该方法对中断不敏感，即线程获取同步状态失败后进入同步队列，后续对线程进行中断操作时，线程不会从同步队列中移除。

    (1) 当前线程实现通过tryAcquire()方法尝试获取锁，获取成功的话直接返回，如果尝试失败的话，
    进入等待队列排队等待，可以保证线程安全（CAS）的获取同步状态。

    (2) 如果尝试获取锁失败的话，构造同步节点（独占式的Node.EXCLUSIVE），通过addWaiter(Node node,int args)方法
    ,将节点加入到同步队列的队列尾部。

    (3) 最后调用acquireQueued(final Node node, int args)方法，使该节点以死循环的方式获取同步状态，
    如果获取不到，则阻塞节点中的线程。acquireQueued方法当前线程在死循环中获取同步状态，
    而只有前驱节点是头节点的时候才能尝试获取锁（同步状态）（ p == head && tryAcquire(arg)）。





![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs6.png)
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs1.png)