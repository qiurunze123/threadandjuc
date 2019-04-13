
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