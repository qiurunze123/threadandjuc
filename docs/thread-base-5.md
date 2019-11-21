
**valatile 解析**

| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |volatile是什么 | [解决思路](/docs/========) |
| 000-1 |volatile 使用场合 | [解决思路](/docs/volatile.md) |
| 001 | volatile 的两点作用 | [解决思路](/docs/volatile.md) |
| 001-1 |volatile保证可见性 | [解决思路](/docs/volatile.md) |
| 001-2 |volatile禁止指令重构排序 | [解决思路](/docs/volatile.md) |
| 001-3 |volatile禁止指令重构排序原理 | [解决思路](/docs/volatile.md) |
| 003 |volatile与synchronized | [解决思路](/docs/volatile.md) |
| 004 |volatile小结| [解决思路](/docs/volatile.md) |

### volatile 是什么 ?

 1.volatile 是一种同步机制 必synchronized和lock更轻量 因为 volatile并不会发生上下文切换等开销很耗时的行为
 
 2.如果一个变量被修饰成volatile 那么JVM就知道这个变量可能会被并发修改
 
 3.能力小 volatile 做不到synchronized那样的原子性 
 
### volatile 使用的场合

1.不适用a++等操作 why ? 因为volatile不保证原子性 例子：NoVolatile 运行结果表明，volatile没有保证原子性，出现丢失写值的情况（值覆盖）

2.适用场合 boolean flag 如果一个变量共享 自始至终被多个线程赋值 而没有其他操作 因为赋值自身是有原子性的 而volatile 又保证了可见性 

所以线程安全 com.geekagain.volatilego.uservolatile 前提是不依赖上一次的状态 

举例：

NoVolatile2
  
3.作为刷新之前变量的触发器 FieldVisibilityMind


### volatile 的两点作用

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile100.png)

1.volatile 可见性

       各个线程会将共享变量从主内存中拷贝到工作内存，然后执行引擎会基于工作内存中的数据进行操作处理。
       线程在工作内存进行操作后何时会写到主内存中？这个时机对普通变量是没有规定的，而针对volatile修饰的变量给java虚拟机特殊的约定，
       线程对volatile变量的修改会立刻被其他线程所感知，即不会出现数据脏读的现象，从而保证数据的“可见性”。

2.volatile 禁止指令重排序

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile3.png)

 volatile实现禁止指令重排优化，从而避免多线程环境下程序出现乱序执行的现象


### volatile 禁止指令重排序原理

volatile实现禁止指令重排优化，从而避免多线程环境下程序出现乱序执行的现象

先了解一个概念，内存屏障（Memory Barrier） 又称内存栅栏，是一个CPU指令，它的作用有两个：

一是保证特定操作的执行顺序，

**二是保证某些变量的内存可见性（利用该特性实现volatile的内存可见性）**

由于编译器和处理器都能执行指令重排优化。如果在指令间插入一条Memory Barrier则会告诉编译器和CPU,
不管什么指令都不能和这条Memory Barrier指令重排序，也就是说通过插入内存屏障禁止在内存屏障前后的指令执行重排序优化。
 内存屏障另外一个作用就是强制刷出各种CPU的缓存数据，因此任何CPU上的线程都能读取到这些数据的最新版本。


 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile4.png)

    工作内存与主内存同步延迟现象导致的可见性问题
    可以使用synchronized或volatile关键字解决，他们都可以是一个线程修改后的变量立刻对其他线程可见
    对于指令重排导致的可见性问题和有序性问题
    可以利用volatile关键字解决，因为volatile的另外一个作用就是禁止重排序优化
    
### volatile与synchronized的区别

  synchronized 阻塞式同步在线程竞争激烈的情况下会升级为重量级锁 | java虚拟机提供的最轻量级的同步机制
  
  可以把volatile看作是轻量级的synchronized：如果一个变量自始至终只被各个线程赋值而没有其他的操作那么可以用
  volatilela来代替synchronized或者代替原子变量 因为赋值自身s是由原子性的 而volatile又保证可见性 所以保证线程安全
      

### volatile小结

1.volatile 使用场景： 某个属性被多线程共享 其中有一个线程修改了此属性其他线程可以立刻得到值
比如boolean flag 或者作为触发器 实现轻量级同步

2.volatile 属性的读和写都是无锁的 他不能替代synchronized 因为它没有原子性和互斥性

3.提供可可见性

4.提供happens-before保证
