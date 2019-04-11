
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

 线程分为两类： User Thread(用户线程)、Daemon Thread(守护线程) 只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
 Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 GC (垃圾回收器)，它就是一个很称职的守护者
 如果用户线程都结束了那么守护线程也会都结束！
 
 三点注意：
 (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 
 (2) 在Daemon线程中产生的新线程也是Daemon的。 
 
 (3) 不要认为所有的应用都可以分配给Daemon来进行服务，比如读写操作或者计算逻辑
 
 =====================================
 线程优先级：
 
 高优先级的线程比低优先级的线程有更高的几率得到执行 更容易竞争到！
 
 系统线程组的最大优先级默认为 Thread.MAX_PRIORITY
 
 创建线程组时最大优先级默认为父线程组（如果未指定父线程组，则其父线程组默认为当前线程所属线程组）的最大优先级
 
 可以通过 setPriority 更改最大优先级，但无法超过父线程组的最大优先级
 /daemon   /priority
 
 4.基本的线程同步操作
 
  (1) 对给定对象加锁，进入同步代码前要获得给定对象的锁
  
  (2) 直接作用于实例方法：相当于对当前实例加锁，进入同步代码前要获得当前实例的锁
  
  (3) 直接作用于静态方法：相当于对当前类加锁，进入同步代码前要获得当前类的锁
  
  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew4.png)

  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew6.png)
  
===================================  
  A. 无论synchronized关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；
  如果synchronized作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁。
  
  B. 每个对象只有一个锁（lock）与之相关联，谁拿到这个锁谁就可以运行它所控制的那段代码。
  
  C. 实现同步是要很大的系统开销作为代价的，甚至可能造成死锁，所以尽量避免无谓的同步控制。
 
  `http://www.importnew.com/21866.html 此博客更为精细`  /create/sync
  
  5.wait/notify
  
  1.wait和notify 必须实在同步代码块中使用 经常与synchronized 搭配使用 synchronized修饰的同步代码块或方法里面调用wait() 与  notify/notifyAll()方法。
  
  2.由于 wait() 与  notify/notifyAll() 是放在同步代码块中的，因此线程在执行它们时，肯定是进入了临界区中的，即该线程肯定是获得了锁的
  
  当线程执行wait()时，会把当前的锁释放，然后让出CPU，进入等待状态。
  
  当执行notify/notifyAll方法时，会唤醒一个处于等待该 对象锁 的线程，然后继续往下执行，直到执行完退出对象锁锁住的区域（synchronized修饰的代码块）后再释放锁。
  从这里可以看出，notify/notifyAll()执行后，并不立即释放锁，而是要等到执行完临界区中代码后，再释放。故，在实际编程中，
  我们应该尽量在线程调用notify/notifyAll()后，
  立即退出临界区。即不要在notify/notifyAll()后面再写一些耗时的代码。
  
  3.执行notify不会立马释放对象锁，需等该同步方法或同步块执行完。注意是同步的内容执行完，而不是该线程的run方法执行完
  
  /create/waitnotify