### JMM 内存模型

 | ID | Problem  |
 | --- | ---   |
 | 001 |为什么需要JMM|
 | 002 |JMM是什么|
 | 003 |JMM的重点内容|
 
 1.为什么需要JMM
 
     ① 依赖处理器不同处理器处理的结果可能不一样
     ② 没有办法保证并发的安全
     ③ 需要一个标准让多线程可预期
 2.JMM是什么
 
      ① Java memory model 需要各个JVM来遵守JMM规范以便开发者利用这些规范来开发多线程的程序
       如果没有这样的一个JMM内存模型来规范 那么可能经过不同的规则重排序之后导致不同虚拟机
       上运行的结果不一样 产生很大的问题
       
      ② volatile synchornized lock 等原理都是JMM 如果没有JMM那就是需要我们自己指定什么时候让
      主内存与工作内存拷贝 
 
 3.JMM的重点内容
 
   1.重排序
   
   2.可见性
   
   3.原子性

### Happens-Before原则   

   | ID | Problem  |
   | --- | ---   |
   | 001 |什么是Happens-Before原则 |
   | 002 |Happens-Before规则有哪些|
   
   1.Happens-Before是什么
   
   ①happens-before 规则是用来解决可见性问题的 **在时间上A动作在B动作之前发生 B能保证看到A
   这就是happens-before**
   
   ② 俩个操作可以用happens-before来确定他们的执行顺序 **如果一个操作happens-before与另一个操作
   那么我们就说第一个操作对于第二个操作时可见的**
   
   2.Happens-before的规则有哪些
   
   ① 单线程规则
   
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
   
   对线程interrupt（）方法的调用happen—before发生于被中断线程的代码检测到中断时事件的发生
   
   ⑧ 构造方法
   
   ⑨ 工具类happens-before原则
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/happens-before.png)
