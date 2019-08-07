### 导航

| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |synchronized和lock 的区别| [解决思路](/docs/sandl.md) |
#### synchronized 和 lock 区别 

    1.synchronized 是关键字属于JVM层面 
     Java -c monitorenter (底层是通过monitor对象来完成的 
     其实wait和notify等方法也依赖于monitor对象只有在同步块或方法中才能调用wait和notify等方法)  
     monitorenter 
    
    lock是具体类java.util.concurrent.locks.lock 是 api层面的锁
    
    2.synchronized 不需要用户去手动释放锁,当synchronized 代码执行完后系统会自动让线程释放对锁的占用
      ReentrantLock 则需要用户去手动释放锁若没有主动释放锁就有可能导致出现死锁现象 需要lock和unlock tryfinally 语句块来完成
      
    3.等待是否可中断
    
    synchronized不可中断除非抛出异常或者正常运行完成
    ReentrantLock 可中断 1.设置超时时间 trylock（long timeout,TimeUnit unit）
                        2.lockInterruptibly() 放代码块中调用interrupt()方法可中断
      
    4.加锁是否公平
    
      synchronized 非公平锁
      ReentrantLock 两者都可以，默认非公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁
      
    5.锁绑定多个条件condition
    
    synchronized 没有
    ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个要么全部唤醒
    
### 例子 多线程按顺序调用实现A->>B->>-C 要求 为 A线程打印五次 B线程打印10次 C线程打印 15次 D线程打印 20次 执行10轮

