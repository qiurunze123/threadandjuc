#### volatile  

       volatile是什么
       
       volatie 可以保证不同线程之间的交互可见性   
       1.轻量级同步锁（竞争线程不会阻塞）
       2.保证可见性
   	   3.不保证原子性
   	   
   	   
   	   JMM内存模型值之可见性
   	   
   	  JMM关于同步的规定 
      
      线程解锁前 必须把共享变量的值栓回到主内存
      线程加锁前，必须把主内存最新的值更新到自己的内存中
      加锁解锁是同一把锁
      
      
      由于JVM的运行程序的实体是线程 而每个线程创建时JVM都会为其创建一个自己的工作内存，工作内存是每个线程的私有数据区域
      而java内存模型中规定所有的变量都储存在主内存中，主内存是共享内存区域所有的线程都可以访问，但是线程对变量的操作读值赋值等必须在工作内存中进行
      ，首先要将变量从主内存拷贝到自己的工作内存空间，然后对变量进行操作，操作完成后再将变量写回到主内存，不能直接操作主内存中的变量，各个线程中的
      工作内存存储着主内存的变量副本拷贝，因此不同线程无法访问对方的工作内存，线程间的通信传值必须通过主内存来完成！
      
`主内存是共享内存区域所有的线程都可以访问，但是线程对变量的操作读值赋值等必须在工作内存中进行,首先要将变量从主内存拷贝到自己的工作内存空间,
然后对变量进行操作，操作完成后再将变量写回到主内存，不能直接操作主内存中的变量，各个线程中的工作内存存储着主内存的变量副本拷贝`


 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile1.png)
 
#### volatile不保证原子性

VolatileDemo1

运行结果表明，volatile没有保证原子性，出现丢失写值的情况（值覆盖）

VolatileDemo2

atomicInteger 解决原子性问题

#### volatile 禁止指令重排序


 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile3.png)
 
 volatile实现禁止指令重排优化，从而避免多线程环境下程序出现乱序执行的现象
 
 先了解一个概念，内存屏障（Memory Barrier） 又称内存栅栏，是一个CPU指令，它的作用有两个：
 
 一是保证特定操作的执行顺序，
 
 二是保证某些变量的内存可见性（利用该特性实现volatile的内存可见性）
 
 由于编译器和处理器都能执行指令重排优化。如果在指令间插入一条Memory Barrier则会告诉编译器和CPU，
 不管什么指令都不能和这条Memory Barrier指令重排序，也就是说通过插入内存屏障禁止在内存屏障前后的指令执行重排序优化。
 内存屏障另外一个作用就是强制刷出各种CPU的缓存数据，因此任何CPU上的线程都能读取到这些数据的最新版本。

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile4.png)


    工作内存与主内存同步延迟现象导致的可见性问题
    可以使用synchronized或volatile关键字解决，他们都可以是一个线程修改后的变量立刻对其他线程可见
    对于指令重排导致的可见性问题和有序性问题
    可以利用volatile关键字解决，因为volatile的另外一个作用就是禁止重排序优化
    
#### 单例模式在多线程下可能存在的问题以及分析

    
    上述代码采用了DCL（Double Check Lock）的双端检锁机制，但是还是不能保证线程安全，原因是由指令重排序的存在，加入volatile可以禁止指令重排
    
      原因在于某一个线程执行到第一次检测，读取到的instance不为null时，instance的引用对象可能没有完成初始化。
    
    instance = new SingletonDemo();可能会分为以下三步（伪代码）：
    
    memory = allocate();//分配对象内存空间
    	instance(memory); //初始化对象
    	instance = memory;//设置instance执行刚分配的内存地址，此时instance != null
    步骤2和步骤3不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单线程中并没有改变，因此这种重排优化是允许的
    
    memory = allocate();//分配对象内存空间
    	instance = memory;//设置instance执行刚分配的内存地址，此时instance != null，但是对象还没有完成初始化
    	instance(memory); //初始化对象
    但是指令重排只会保证串行语义的执行一致性（单线程），但并不会关心多线程间的语义一致性、
    
    所以当一条线程访问instance不为null时，由于instance实例未必已完成初始化，也就造成了线程安全问题。
    
    所以在  private static volatile SingletonDemo instance =null; 加volatile
    
    
 实现原理:
    
  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew8.png)
    
 happens-before 6大原则：
    
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew9.png)
    
  对于这个valatile 的规则 写先发生于读 保证了可见性 /VolatileExample/VolatileExample  /VolatileExample/Volatile2
    
  i++问题解决方法：加锁！/VolatileExample/AccountingSync
    
 https://juejin.im/post/5ae9b41b518825670b33e6c4 推荐 博客