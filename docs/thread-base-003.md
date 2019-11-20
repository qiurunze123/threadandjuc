###  多线程的生命周期状态纽机流转

https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/Thread.State.html 官方地址

线程大概分为六种状态: NEW RUNNABLE BLOCKED WAITING TIMED_WAITING TERMINATED 
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase003.png)
 
 此为准确流转图 
 
 NEW 状态表示 新创建但是还没有启动的线程 
 
 Runnable 状态表示 一旦调用start() 便直接从NEW状态编程可运行的状态 (既可以是可运行的也可以是实际运行中的 都可以表示)
 
 Blocked（一定是synchronize修饰的） 当一个线程进入到被synchronize修饰代码块的 但是该锁已经被其他线程拿走了 也就是该monitor锁被其他线程拿走了
 
 ...... 看图说明一切
 
 LockSupport 很多底层锁的原理
 
 阻塞状态： 一般习惯而言 把blocked(被阻塞)  waiting 等待 Time_waiting(计时等待) 都被称为阻塞状态
 
 //========================== 异常情况（请看下章 object wait 和 notify ） ===================================
 
 1.   如果发生异常，可以直接跳到终止TERMINATED状态，不必再遵循路径，比如可以从WAITING直接到TERMINATED。
 
 2.   从Object.wait()刚被唤醒时，通常不能立刻抢到monitor锁，那就会从WAITING先进入BLOCKED状态，抢到锁后再转换到RUNNABLE状态。
 
 