### JMM 内存模型

#### 为什么需要JMM
1.从java代码到CPU指令

 ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm1.png)
     
     1.最开始我们编译的是java代码是*.java文件
     2.在编译（javac命令）后从刚才的*.java文件变成一个新的java字节码文件（*.class）
     3.JVM执行刚才生成的字节码（*.class）把字节码文件转化为机器指令之后就与平台无关了
     （jvm会根据不同的操作系统和平台的不同把字节码翻译成不同的机器指令）
     4.机器指令可以直接在CPU上运行 也就是最终指令
     
2. java内存模型应运而出?

     不同的CPU平台的机器指令有千差万别无法保证并发安全效果一致
     在最开始 是会发生这一系列问题的 存在不同的平台运行起来效果不一样 即使你的指令一样但是也会出现不一致的效果
     这个时候就出现了java内存模型来进行统一规范

#### 1.JMM是什么 为什么需要JMM ??
 
 全称java memory model 是一组规范  
 
 ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm4.png)
 
迫切需要一个标准来让我们多线程运行的结果可预期 综上原因JMM这个规范就出来了 各个jvm的实现需要遵守JMM规范
以便于像我们这样的开发者可以利用这些规范更方便的开发多线程的程序

### 3.JMM的重点内容

  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/jmm100.png)

 ##### 1.重排序 
   
   ThreadJmm1 ThreadJmm2
   
  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm120.jpg)
   
   为什么要重排序??
   
  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm5.jpg)
  
  可以提高处理速度
  
  重排序的三种情况:
  
  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm121.jpg)

  2.可见性 ThreadJmm3 解决ThreadJmm4
   
  ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm6.png)
  
  3.volatile 如何解决
  
  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/volatile100.png)

 ##### 2.可见性
 
 ###### 1.为什么会有可见性问题 why可见性??
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm8.png)
 
 从内存到cpu 是有多层缓存的 从上到下的速度是逐渐变快的 我们之所以不直接从RAM拿内存 主要是因为这样会极大降低我们的速度 但是这样就有可能
 导致内存不同步问题 比如core4 连接L1缓存 然后跑到L2 但是 左边的L2还没反应过来 所以导致同一个值带不同时刻都不一样
 
  总结一下：
  1.高速缓存的容量比主内存小 但是速度仅次于寄存器 所以在cpu 与主内存之间就多了缓存层
  
  2.线程之间的共享变量的可先行问题不是直接有多核引起的 而是由多缓存引起的
  
  3.假如所有核心都用一个缓存 那么也就不存在内存可见性问题
  
  4. 但是每个核心都会将自己需要的数据读取到缓存中 数据修改后也是写入缓存中 等待刷新主内存 所以会导致有些核心读取的只是过期的值
 ###### 2.JMM怎么解决的??
 
 开辟出来了主内存和工作内存
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm9.png)
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm102.png)
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/jmm100.png)
 
总结一下：

1.所有的变量都存储在主内存中同时每个线程也有自己的独立的工作内存 工作内存中的变量内容都是主内存中的拷贝

2. 线程不能直接读取主内存中的值 而是只能操作自己工作内存中的变量 然后同步到主内存中

3.主内存时多个线程共享的 但线程间不共享工作内存 如果线程需要通信 必须借助主内存中转完成

-- 所有共享变量存在于主内存中 但线程间不共享工作内存 如果线程间需要通信必须借助主内存中转完成

 ###### 3.Happens-Before原则     
   | ID | Problem  |
   | --- | ---   |
   | 001 |什么是Happens-Before原则 |
   | 002 |Happens-Before规则有哪些|
   
   1.Happens-Before是什么
   
   ①happens-before 规则是用来解决可见性问题的 **在时间上A动作在B动作之前发生 B能保证看到A
   这就是happens-before**
   
   ② 俩个操作可以用happens-before来确定他们的执行顺序 **如果一个操作happens-before于另一个操作
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
   
   Thread对象的start（）方法启动起来就能可能这个线程启动之前的每一个动作
   
   ⑤ 线程join
   
   join()之后的语句一定可以看到那个等待线程的的所有语句保证刚才的语句一定能执行完毕 一定可以看得到 等你真正运行完毕 我在执行后面的语句
   
   ⑥ 传递性
   
   如果操作A happen—before操作B，操作B happen—before操作C，那么可以得出A happen—before操作C
   
   ⑦ 中断
   
一个线程如果被其他线程interrupt是 那么就检测中断（isInterrupted）或者抛出InterruptedException 一定能看到
   
   ⑧ 工具类happens-before原则
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/happens-before.png)

CountDownLatch 比如我释放之前你必须达到释放的条件

 
### volatile 如何保证 可见性的呢??

  ![整体流程](https://github.com/qiurunze123/threadandjuc/blob/master/docs/thread-base-5.md)
 
  ### 3.原子性
    
    原子性 大家都知道要么都执行要么都不执行 是不可分割的 i++就不是原子性的
    
   ![整体流程](https://github.com/qiurunze123/threadandjuc/blob/master/docs/threadjmm123.md)
    
    i++ 一行代码有三个动作 目前i等于几 然后加上去 然后再写进去 这是三个指令 对于这三个指令而言是可以随意乱序的 
    比如我第一个线程执行完第一个指令 执行第二个指令之后 突然跑过去第二个线程执行第一个第二个指令 然后再跑回来执行i=2
    此时又跑回去对于第二个线程而言 一开始i=1的时候 像读取当前i的值 但是 那个时候 线程1还没有把i写回去 所以线程2当时读的i
    就是=1于是经过 这么俩个乱序的操作 俩个线程都会把2写回去实际上就少加了1 synchorized 可以 解决
    
  #### 原子操作有哪些??                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               dc r
    
  ##### java 中原子操作有哪些?? 默认的原子操作很少
    
    除了long和double之外的基本类型（int byte .....）
   
    所有引用reference的赋值操作 不管是32位机器还是64位机器
   
    java.concurrent.Atomic.* 包中所有类的原子操作
    
   为什么long 和double 不是原子性的??
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm10.png)

总结 ： 在32位得JVM中 long 和 double 操作不是原子的 但是在64位jvm上是原子得
       
       不必担心 商用Java虚拟机 不回出现 已经自动保证了原子性 

   
   

