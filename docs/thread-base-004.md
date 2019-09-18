### 线程的重要属性

    ①编号（ID）：long,标识不同的线程。
    
    ②名称（name）：有默认值，但可以设置，主要是给人看的，用于调试和定位问题
    
    ③是否是守护线程（Daemon）:是否为守护线程，这个属性的默认值和相应线程的父线程的该属性值相同，
    setDaemon()只能在start前调用。守护线程通常用于执行一些重要性不是很高的任务，相比于非守护线程（用户线程），他不会影响到虚拟机的停止，
    正常停止，则虚拟机会等用户线程都运行完才会停止。
    如果虚拟机进程是直接被kill掉，则用户线程则影响不到了，毕竟老大都没了。
    默认值与相应线程的父线程该属性值相同，该属性必须在线程启动前设置！否则会报错
    
    ④优先级 
    类型int，该属性本质上是给线程调度器的提示，用于表示应用程序那个线程优先运行。java定义了1~10的10个优先级别。默认值为5（普通优先级别）。对应一个具体的线程而言，优先级别的默认值与父线程相同。
    线程并不保证执行顺序按优先级进行！优先级使用不当可能导致某些线程用于无法得到运行！一般情况下不设置即可

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase004.png)

#### 线程编号ID

     线程编号 因为开始的是++ 最开始为0 所以 main函数为1 
     
     private static synchronized long nextThreadID() {
             return ++threadSeqNumber;
      }
      
      为什么新建的线程为10几因为 还有许多别的线程初始化了
      
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase004-1.png)
   
   
#### 线程名称

 默认名称： Thread-
 
       Thread(Runnable target, AccessControlContext acc) {
             init(null, target, "Thread-" + nextThreadNum(), 0, acc);
         }
     
         //自增synchronized 可以保证线程没有重名的情况
         private static synchronized int nextThreadNum() {
             return threadInitNumber++;
         }
         
  1.使用Thread类中的方法setName(名字) void setName(String name) 改变线程名称，使之与参数 name 相同
  
  2.创建一个带参数的构造方法,参数传递线程的名称;调用父类的带参构造方法,把线程名称传递给父类,让父类(Thread)给子线程起一个名字 Thread(String name) 分配新的 Thread 对象。

#### 守护线程

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase004-2.png)

为什么不提倡使用守护线程?

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase004-3.png)

#### 线程优先级

    线程的优先级说明在程序中该线程的重要性。系统会根据优先级决定首先使用哪个线程，
    但这并不意味着优先级低的线程得不到运行，只是它运行的几率比较小而已，比如垃圾回收机制
    给线程设置的优先级的意图是希望高优先级的线程被优先执行，但是线程优先级的执行情况是高度依赖于操作系统的
    Java的10个线程的优先级会被映射到操作系统的优先级上，不同的操作系统的优先级个数也许更多，也许更少
    有的线程特别勤奋他可能会越过线程优先级去为它分配时间因此我们不能把线程依赖于优先级