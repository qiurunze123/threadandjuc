### JMM 内存模型

 | ID | Problem  |
 | --- | ---   |
 | 001 |JMM是什么,为什么需要JMM|
 | 002 |JMM的重点内容|
 
  
 2.JMM是什么 为什么需要JMM
 
 JMM是什么
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm4.png)

 
 为什么需要JMM ??
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm5.png)

迫切需要一个标准来让我们多线程运行的结果可预期 综上原因JMM这个规范就出来了 各个jvm的实现需要遵守JMM规范
以便于像我们这样的开发者可以利用这些规范更方便的开发多线程的程序

      
 3.JMM的重点内容
 
   1.重排序 ThreadJmm1 ThreadJmm2
   
   为什么要重排序??
   
  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm5.png)
   
   2.可见性 ThreadJmm3 解决ThreadJmm4
   
  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm6.png)
  
   
 为什么会有可见性问题 why可见性??
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm8.png)
 
 从上到下的速度是逐渐变快的 我们之所以不直接从RAM拿内存 主要是因为这样会极大降低我们的速度 但是这样就有可能
 导致内存不同步问题 比如core4 连接L1缓存 然后跑到L2 但是 左边的L2还没反应过来 所以导致同一个值带不同时刻都不一样
 
 JMM怎么解决的??
 
 开辟出来了主内存和工作内存
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm9.png)
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/jmm100.png)
 
       JMM规定了所有的变量都存储在主内存（Main Memory）中。每个线程还有自己的工作内存（Working Memory）, 
       线程的工作内存中保存了该线程使用到的变量的主内存的副本拷贝，线程对变量的所有操作（读取、赋值等）都必须在工作内存中进行，
       而不能直接读写主内存中的变量（volatile变量仍然有工作内存的拷贝，但是由于它特殊的操作顺序性规定，所以看起来如同直接在主内存中读写访问一般）
       不同的线程之间也无法直接访问对方工作内存中的变量，线程之间值的传递都需要通过主内存来完成。

 ### 3.原子性  Happens-Before原则     
   
  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/jmm100.png)
  
   | ID | Problem  |
   | --- | ---   |
   | 001 |什么是Happens-Before原则 |
   | 002 |Happens-Before规则有哪些|
   
   1.Happens-Before是什么
   
   ①happens-before 规则是用来解决可见性问题的 **在时间上A动作在B动作之前发生 B能保证看到A
   这就是happens-before**
   
   ② 俩个操作可以用happens-before来确定他们的执行顺序 **如果一个操作happens-before与另一个操作
   那么我们就说第一个操作对于第二个操作时可见的**
   
   什么不是happens-before
   
   两个线程没有相互配合的机制 所以代码X和代码Y 的执行结果并不能保证总被对方看到这就不具备happens-before
   
   ThreadJmm3
   
   2.Happens-before的规则有哪些
   
   ① 单线程规则
   
   private void change(){
   a =3;
   b= a;
   }
   只要a=3执行完了 那么后面的指令都会看到a=3 但是并不影响重排序
   
   一个线程内保证了语义的串行性
   
   ② 锁操作(synchronized和Lock)
   
   对于在解锁一个锁之前的所有操作 对于加锁这个锁之后的所有操作是可见的（解锁必然发生在随后的加锁前）
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/happens-before1.png)
   
   ③ volatile变量
   
   volatile变量的写先发生于读 这就保证了volatile的可见性
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/happens-before2.png)
   
   近朱者赤 FieldVisibility.java 给b加volatile 会影响全局实现轻量级同步
   
   b 之前写入的代码 对读取b后的代码都可见 所以在writerThread 里面对a的赋值一定会对readerThread里面的读取可见
   所以这里a即使不加valitile 只要b读到3就可以由happens-before的原则保证读取到的都是3而不是1 
   
   ④ 线程启动
   
   Thread对象的start（）方法happen—before此线程的每一个动作
   
   ⑤ 线程join
   
   如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回
   
   ⑥ 传递性
   
   如果操作A happen—before操作B，操作B happen—before操作C，那么可以得出A happen—before操作C
   
   ⑦ 中断
   
   对线程interrupt()方法的调用happen—before发生于被中断线程的代码检测到中断时事件的发生
   
   ⑧ 工具类happens-before原则
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/happens-before.png)

 
####  volatile 如何保证 

  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm7.png)

  ![整体流程](https://github.com/qiurunze123/threadandjuc/blob/master/docs/thread-base-5.md)
  


