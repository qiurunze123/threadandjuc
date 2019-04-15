
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