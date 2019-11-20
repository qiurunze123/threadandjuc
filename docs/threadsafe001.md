### 1.什么是线程安全???

    当多个线程访问某个方法时，不管你通过怎样的调用方式或者说这些线程如何交替的执行，
    我们在主程序中不需要去做任何的同步，都不需要进行额外的处理,这个类的结果行为都是我们设想的正确行为
    那么我们就可以说这个类时线程安全的
    
### 2.一共有几类线程安全的问题???

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadsafe001.png)
   
#### 1.运行结果错误 a++ 多线程下出现不准的请求现象
     
     threadsaft -- ThreadError1
     
     解决:内存可见 JUC原子类操作
     
![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threaderror1.png)
     
#### 2.活跃性问题: 死锁 活锁 饥饿锁 ......
     
![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threaderror2.png)
     
     threadsaft -- ThreadError2
         
#### 3.对象发布和初始化的时候安全问题
  
![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadsafe002-1.png)
  
     举例：1.在构造函数中未初始化完毕就this赋值
          3.构造函数中运行线程
          
     threadsaft -- ThreadError3 ThreadError4

### 3.各种需要考虑线程的安全情况 
 
 总结： 1.访问共享的变量或者资源会有并发风险 比如对象的属性 静态变量 共享缓存 数据库等
 
 2.所有依赖时序的操作 即使每一步操作都是线程安全的 还是存在并发问题：read-modify-write ,check-then-act （检查后行动）
 
 3.不同数据之间存在捆绑关系的时候 （ip和端口号 进行原子合并操作）
 
 4.我们使用其他类的时候 如果对方没有声明自己是线程安全的  比如 hashmap 就提醒是线程不安全的 我们就知道要使用安全的map ConcurrentHashMap
   
### 4.为什么会有线程性能问题
   
   由于线程需要协作所以引起调度  当你线程的数量大于cpu的数量的时候系统就会进行分配与调度
   
   1.调度就会引起上下文切换
   
   2.什么是上下文?
   
   操作系统的核心在CPU上对于进程进行以下的活动 挂起一个线程将这个进程在CPU上对于进程包括线程进行以下活动
   
   ① 挂起一个线程 将这个进程在CPU的中的状态存储在内存的某处 
   
   ② 在内存中检索下一个进程 的上下文并在cpu的寄存器恢复
   
   ③ 跳到程序计数器所指的位置 恢复该进程
   
### 5.何时会导致密集的上下文切换
 
 频繁竞争锁 或者 IO读写阻塞 