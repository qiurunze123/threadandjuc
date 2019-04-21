
**ReentrantLock 讲解**

1.可重入 -- 单线程可以重复进入但是要重复退出

2.可中断 -- lockInterruptibly()
 
3.可限时 -- 超时不能获得锁，就返回false，不会永久等待构成死锁

重入性原理：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew24.png)

加入锁代码逻辑：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew22.png)

释放锁代码逻辑：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew23.png)

/reentrantlock/ReenTrantLock1

2.可中断 /reentrantlock/ReenTrantLock2

在当通过这个方法去获取锁时，如果其他线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态也就使说，当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，假若此时线程A获取到了锁，而线程B只有等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。
  --------------------- 
  
【注意是：等待的那个线程B可以被中断，不是正在执行的A线程被中断】

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew25.png)

3.可限时

超时不能获得锁,就返回false,不会永久等待构成死锁

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew26.png)

4.公平锁

ReentrantLock 默认采用非公平锁，除非在构造方法中传入参数 true 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew27.png)

**公平锁的 lock 方法**

在注释1的位置，有个!hasQueuedPredecessors()条件，意思是说当前同步队列没有前驱节点（也就是没有线程在等待）时才会去

compareAndSetState(0, acquires)

使用CAS修改同步状态变量。所以就实现了公平锁，根据线程发出请求的顺序获取锁。

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew28.png)

**非公平锁的lock方法**

非公平锁的实现在刚进入lock方法时会直接使用一次CAS去尝试获取锁，不成功才会到acquire方法中，如注释2。

而在nonfairTryAcquire方法中并没有判断是否有前驱节点在等待，

直接CAS尝试获取锁，如注释3。由此实现了非公平锁
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew29.png)

区别：

非公平锁和公平锁的两处不同：

非公平锁在调用 lock 后，首先就会调用 CAS 进行一次抢锁，如果这个时候恰巧锁没有被占用，那么直接就获取到锁返回了

非公平锁在 CAS 失败后，和公平锁一样都会进入到 tryAcquire 方法，在 tryAcquire 方法中，如果发现锁这个时候被释放了（state == 0）

<<<<<<< HEAD
非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面 /reenTrantLock/ReenTrantLock4
 =====================================================================================

**Condition**

第一步：一个线程获取锁后，通过调用 Condition 的 await() 方法，会将当前线程先加入到等待队列中，并释放锁。然后就在 await() 中的一个 while 循环中判断节点是否已经在同步队列，是则尝试获取锁，否则一直阻塞。

第二步：当线程调用 signal() 方法后，程序首先检查当前线程是否获取了锁，然后通过 doSignal(Node first) 方法将节点移动到同步队列，并唤醒节点中的线程。

第三步：被唤醒的线程，将从 await() 中的 while 循环中退出来，然后调用 acquireQueued() 方法竞争同步状态。竞争成功则退出 await() 方法，继续执行

await()方法会使当前线程等待,同时释放当前锁,当其他线程中使用signal()时或者signalAll()方法时,线程会重新获得锁并继续执行。或者当线程被中断时,也能跳出等待。这和Object.wait()方法很相似

awaitUninterruptibly()方法与await()方法基本相同,但是它并不会再等待过程中响应中断

singal()方法用于唤醒一个在等待中的线程。相对的singalAll()方法会唤醒所有在等待中的线程。这和Obejct.notify()方法很类似

主要方法：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew30.png)


/condition/Condition1
======================================================================================

**Semaphore信号量**

1.共享锁

2.运行多个线程同时临界区

Semaphore类是一个计数信号量，必须由获取它的线程释放，通常用于限制可以访问某些资源（物理或逻辑的）线程数目，信号量控制的是线程并发的数量,
信号量为1的时候就相当于一把锁

思考：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew31.png)


**读写锁**

1.Java并发库中ReetrantReadWriteLock实现了ReadWriteLock接口并添加了可重入的特性

2.ReetrantReadWriteLock读写锁的效率明显高于synchronized关键字

3.ReetrantReadWriteLock读写锁的实现中，读锁使用共享模式；写锁使用独占模式，换句话说，读锁可以在没有写锁的时候被多个线程同时持有，写锁是独占的

4.ReetrantReadWriteLock读写锁的实现中，需要注意的，当有读锁时，写锁就不能获得；而当有写锁时，除了获得写锁的这个线程可以获得读锁外，其他线程不能获得读锁

https://www.jianshu.com/p/9cd5212c8841  写的不错




 
=======
非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面

**CountDownLatch 详解**

原理详解：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew40.png)

        //Main thread start
        //Create CountDownLatch for N threads
        //Create and start N threads
        //Main thread wait on latch
        //N threads completes there tasks are returns
        //Main thread resume execution
        
构造器中的计数值（count）实际上就是闭锁需要等待的线程数量。这个值只能被设置一次，而且CountDownLatch没有提供任何机制去重新设置这个计数值。

与CountDownLatch的第一次交互是主线程等待其他线程。主线程`必须在启动其他线程后立即调用CountDownLatch.await()`方法。这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。

其他N 个线程必须引用闭锁对象，因为他们需要通知CountDownLatch对象，他们已经完成了各自的任务。
这种`通知机制是通过 CountDownLatch.countDown()方法来完成的；每调用一次这个方法，在构造函数中初始化的count值就减1`。
所以当N个线程都调 用了这个方法，count的值等于0，然后主线程就能通过await()方法，恢复执行自己的任务。

使用的一些场景：

1.实现最大的并行性：有时我们想同时启动多个线程，实现最大程度的并行性。例如，我们想测试一个单例类。如果我们创建一个初始计数为1的CountDownLatch，并让所有线程都在这个锁上等待，那么我们可以很轻松地完成测试。我们只需调用 一次countDown()方法就可以让所有的等待线程同时恢复执行。

2.开始执行前等待n个线程完成各自任务：例如应用程序启动类要确保在处理用户请求前，所有N个外部系统已经启动和运行了。

3.死锁检测：一个非常方便的使用场景是，你可以使用n个线程访问共享资源，在每次测试阶段的线程数目是不同的，并尝试产生死锁。

例如火箭发射，必须是 各种检查完成后才能发射


**CyclicBarrier详解**

说明：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew42.png)


代码详解：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew41.png)

1.士兵陆续前来集合

2.士兵集合完毕

3.barrierAction1: 打印"司令：[士兵10个，集合完毕!]"

4.士兵陆续完成任务

5.所有士兵的任务都执行完毕

6。barrierAction2: 打印"司令：[士兵10个, 任务完成!]"

barrierAction每次都是由一个线程执行的，而这个线程一般就是最后到达的那个线程

**栅栏损坏**

1.有一个线程发生中断或者超时，而当前线程正在等待（await），则当前线程会抛出BrokenBarrierException

2.该CyclicBarrier对象被调用了reset方法

3.该CyclicBarrier对象被调用await时，状态已经是"broken"了

4.barrierAction抛出了未捕获的异常

源码:(就不贴了 有兴趣自己看吧)

CountDownLatch不同，CyclicBarrier不是基于AQS实现，而是应用ReentrantLock实现的，它的同步靠的是两个成员变量（分别是一个ReentrantLock以及从中引申出的Condition）

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew43.png)

        https://blog.csdn.net/qq_33256688/article/details/85241557 不错
        
**LockSupport 阻塞线程**

方法：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew44.png)

区别：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew45.png)

与suspend()比较 不容易引起线程冻结

能够响应中断，但不抛出异常。

中断响应的结果是，park()函数的返回，可以从Thread.interrupted()得到中断标志

**ReentrantLock 源码分析**

http://www.cnblogs.com/leesf456/p/5383609.html  

**BlockingQueue 阻塞队列**

http://www.importnew.com/28053.html
>>>>>>> d8fa6e47085b616b3751e0a91d5f6ce3147d5504
