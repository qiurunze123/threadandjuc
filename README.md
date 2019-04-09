![互联网 Java 多线程那些事](https://raw.githubusercontent.com/qiurunze123/imageall/master/thread100.png)

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

> QQ群 :

    坚持是一种信仰 专注是一种态度！
    
###  [如要提交代码请先看--提交合并代码规范提交者的后面都会有署名方便大家问问题](/docs/code-criterion.md)
###  [多线程之前更新版本 -- 请进代码路径：com.geek.threadandjuc](/docs/thread-base-1.md)


**第一阶段 基础阶段**

1.什么是线程 什么是进程?? 

进程就好比工厂的车间，他代表着CPU所能执行的任务单元，任意时刻CPU总是运行着一个进程，其他进程处于非运行状态！

线程就好比车间内的工人，一个车间内可以有许多的工人，他们共同完成一个工作，一个进程可以包括多个线程

车间内的空间是工人们共享的，比如许多房间是每个工人都可以进出的，这象征着一个进程的内存空间是共享的，每个线程都可以使用这个共享内存

但是每个房间的大小不同，有的房间只能容纳一个人，厕所 里面有人的时候外面的人则进不去,防止闯入则可以加一把锁

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew1.png)


2.线程基本操作 

线程流转图

  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew2.png)

 NEW ：线程刚刚创建还没有启动
 
 RUNNABLE: 触发了start方法，线程正式启动
 
 BLOCKED: 线程阻塞，等待获取锁，获取到了锁则进行RUNNING状态继续运行
 
 WAITING：表示线程进行无限制等待状态，需要唤醒，需要notify/notifyall等 join需要等待目标线程完成后继续执行，一旦条件满足则进行RUNNING状态继续运行
 
 TIMED_WAITING: 线程进入了一个有限时的等待，如sleep(3000),等待3秒后线程重新进行运行状态
 
 TERMINATED: 表示线程结束进入终止状态
 
**创建线程**

 /create/CreateThread1
 
           public Thread(Runnable target) {
           init(null, target, "Thread-" + nextThreadNum(), 0);
           }
           ------------------------------------
           Thread.run()的实现 target 是Runnable接口
           public void run() { 
           if (target != null) 
           { 
           target.run(); 
           } 
           }
           
 **终止线程**
 
  STOP 方法已经被命令废弃 因为当你在执行多线程的时候突然进行stop 有可能会发生许多错误
  
  yield 注解上明确说 It may be useful for debugging or testing purposes 
  
  public void Thread.interrupt() // 中断线程 
  
  public boolean Thread.isInterrupted()  // 判断是否被中断 
  
  public static boolean Thread.interrupted() // 判断是否被中断，并清除当前中断状态
  
  可取消的阻塞状态中的线程, 比如等待在这些函数上的线程, Thread.sleep(), Object.wait(), Thread.join(),
   
  这个线程收到中断信号后, 会抛出InterruptedException, 同时会把中断状态置回为false.所以在抛出异常后继续
  
  可以适用于停止线程 /stop/
  
  **join 和 yield 线程**
  
  yield 这个方法是想把自己占有的cpu时间释放掉，然后和其他线程一起竞争
  
  join方法的意思是等待其他线程结束 结束后自己在结束运行
  
  join()方法也可以传递一个时间，意为有限期地等待，超过了这个时间就自动唤醒。 当一个线程运行完成终止后，将会调用notifyAll方法去唤醒等待在当前线程实例上的所有线程,这个操作是jvm自己完成的

  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew3.png)

3.线程的优先级和守护线程

