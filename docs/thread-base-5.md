
**valatile 解析**


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