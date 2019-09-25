
**valatile 解析**

| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |volatile是什么 | [解决思路](/docs/========) |
| 000-1 |volatile 使用场合 | [解决思路](/docs/volatile.md) |
| 001 |JMM内存模型值之可见性 | [解决思路](/docs/volatile.md) |
| 002 |可见性验证说明 | [解决思路](/docs/volatile.md) |
| 003 |volatile不保证原子性 | [解决思路](/docs/volatile.md) |
| 004 |volatile指令重构排序 | [解决思路](/docs/volatile.md) |
| 005 |单例模式在多线程下可能存在的问题 | [解决思路](/docs/volatile.md) |
| 006 |单例模式volatile分析| [解决思路](/docs/volatile.md) |



### volatile 是什么 ?

 1.volatile 是一种同步机制 必synchronized和lock更轻量 因为 volatile并不会发生上下文切换等开销很大的行为
 
 2.如果一个变量被修饰成volatile 那么JVM就知道这个变量可能会被并发修改
 
 3.能力小 volatile 做不到synchronized那样的原子性 
 
### volatile 使用的场合

1.不适用a++等操作 why ? 因为volatile不保证原子性 例子：VolatileDemo1 运行结果表明，volatile没有保证原子性，出现丢失写值的情况（值覆盖）

2.适用场合 boolean flag 如果一个变量共享 自始至终被多个线程赋值 而没有其他操作 因为赋值自身是有原子性的 而volatile 又保证了可见性 所以线程安全 com.geekagain.volatilego.uservolatile
  
  **但是不依赖于该变量的上一个状态**
  
3.作为刷新之前变量的触发器 FieldVisibility

                synchronized                              | volatile
             
       比较: 阻塞式同步，在线程竞争激烈的情况下会升级为重量级锁 | java虚拟机提供的最轻量级的同步机制
       
       各个线程会将共享变量从主内存中拷贝到工作内存，然后执行引擎会基于工作内存中的数据进行操作处理。
       线程在工作内存进行操作后何时会写到主内存中？这个时机对普通变量是没有规定的，而针对volatile修饰的变量给java虚拟机特殊的约定，
       线程对volatile变量的修改会立刻被其他线程所感知，即不会出现数据脏读的现象，从而保证数据的“可见性”。
       
被volatile修饰的变量能够保证每个线程能够获取该变量的最新值，从而避免出现数据脏读的现象


实现原理:

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew8.png)

happens-before 6大原则：

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew9.png)

对于这个valatile 的规则 写先发生于读 保证了可见性 /VolatileExample/VolatileExample  /VolatileExample/Volatile2

i++问题解决方法：加锁！/VolatileExample/AccountingSync

https://juejin.im/post/5ae9b41b518825670b33e6c4 推荐 博客