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

AQS的全称是AbstractQueuedSynchronizer，它的定位是为Java中几乎所有的锁和同步器提供一个基础框架。

AQS是基于FIFO的队列实现的，并且内部维护了一个状态变量state，通过原子更新这个状态变量state即可以实现加锁解锁操作

比如 独占式锁 ReentrantLock ....和共享式锁 countdownlatch , Semaphore 都基于AbstractQueuedSynchronizer一个共同的基类
根据AQS我们可以很简单的构建出自己的同步器

#### AQS锁类别与在使用者 

独占锁和共享锁

锁在一个时间点只能被一个线程占有。根据锁的获取机制，又分为公平锁和非公平锁等待队列中按照FIFO的原则获取锁，等待时间越长的线程越先获取到锁，这就是公平的获取锁，即公平锁。
而非公平锁，线程获取的锁的时候，无视等待队列直接获取锁。ReentrantLock和ReentrantReadWriteLock.Writelock是独占锁

共享锁：同一个时候能够被多个线程获取的锁，能被共享的锁。JUC包中ReentrantReadWriteLock.ReadLock，CyclicBarrier，CountDownLatch和Semaphore都是共享锁。

#### 核心源码

      AbstractQueuedSynchronizer 分析
      
      我们可以看到AQS的全称是AbstractQueuedSynchronizer，它本质上是一个抽象类，
      说明它本质上应该是需要子类来实现的，那么子类实现一个同步器需要实现哪些方法呢

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadsafe11.png)

    1.AQS是Java中几乎所有锁和同步器的一个基础框架，这里说的是“几乎”，因为有极个别确实没有通过AQS来实现
    
    2.AQS中维护了一个队列，这个队列使用双链表实现，用于保存等待锁排队的线程
    
    3.AQS中维护了一个状态变量，控制这个状态变量就可以实现加锁解锁操作了


#### AQS同步器结构与设置节点 

1.获取首节点原来的首节点释放并唤醒后继节点（同步队列获取头结点）

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs5.png)

1.为获取锁的线程加入同步队列（同步队列获取头结点）

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs6.png)

当一个线程无法获取到锁的时候就会被构造成一个Node节点（包含当前线程.等待队列）来加入同步队列中等待得到锁的线程释放锁
#### AQS队列结构节点和同步队列 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/aqs1.png)

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
