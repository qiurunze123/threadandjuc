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
