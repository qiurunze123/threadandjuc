### 多线程如何正确优雅的中断线程

 我们要使用interrupt来进行通知而非强制 （请求线程停止好处是安全）
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/basethread100.png)
 
 停止一个线程意味着在任务处理完任务之前停掉正在做的操作,有许多方法 首先由已经被废弃掉的stop 
 
 **suspend()方法就是将一个线程挂起(暂停),resume()方法就是将一个挂起线程复活继续 废弃理由 有可能造成大量资源浪费造成内存泄露**
  
 **stop 废弃理由 就是相当于把电脑关机了啥也没保存 会造成很严重的后果**
 
   com.geekagain.stopthread 此为各种方法中断线程的方式
   
   
    结束run函数，run中含退出标志位()
    使用interrupt()方法中断线程
    
    public void interrupt() {  ... }   //中断目标线程
    public boolean isInterrupted{ ... } //返回目标线程的中断状态
    public static boolean interrupted(){ ... } // 清除当前线程的中断状态,并返回它之前的值，这也是清除中断状态的唯一方式
    
  interrupt()方法该怎么用呢？interrupt()其本身并不是一个强制打断线程的方法，其仅仅会修改线程的interrupt标志位，然后让线程自行去读标志位，自行判断是否需要中断
  
  当线程发生阻塞堆积的时候 使用volatile的时候无法终止线程
  
      当线程等待某些事件发生而被阻塞，又会发生什么？当然，如果线程被阻塞，它便不能核查共享变量
      ，也就不能停止。这在许多情况下会发生，例如调用Object.wait()、ServerSocket.accept()和DatagramSocket.receive()时，这里仅举出一些。
      他们都可能永久的阻塞线程。即使发生超时，在超时期满之前持续等待也是不可行和不适当的，
      所以，要使用某种机制使得线程更早地退出被阻塞的状态。下面就来看一下中断阻塞线程技术
  
  **while循环在try块里，如果try在while循环里时，应该在catch块里重新设置一下中断标示，因为抛出InterruptedException异常后，中断标示位会自动清除**

  **如果在执行过程中每次循环都会调用slepp和wait方法 则不需要每次迭代都进行检查则不需要每次都设置中断标记**
 
  另外不要在你的底层代码里捕获InterruptedException异常后不处理，会处理不当，如下：
    
    void mySubTask(){   
        ...   
        try{   
            sleep(delay);   
        }catch(InterruptedException e){}//不要这样做   
        ...   
    }
      
   如果你不知道抛InterruptedException异常后如何处理，那么你有如下好的建议处理方式：
   
   1、在catch子句中，调用Thread.currentThread.interrupt()来设置中断状态（因为抛出异常后中断标示会被清除)
   
   让外界通过判断Thread.currentThread().isInterrupted()标示来决定是否终止线程还是继续下去，应该这样做
    
        void mySubTask() {   
            ...   
            try {   
                sleep(delay);   
            } catch (InterruptedException e) {   
                Thread.currentThread().interrupted();   
            }   
            ...   
        }  
   2、或者，更好的做法就是，不使用try来捕获这样的异常，让方法直接抛出
   
        void mySubTask() throws InterruptedException {   
            ...   
            sleep(delay);   
            ...   
        }  
        

**使用中断信号量中断(非阻塞状态)的线程**

 中断线程最好的，最受推荐的方式是，使用共享变量（shared variable）发出信号，告诉线程必须停止正在运行的任务。线程必须周期性的核查这一变量，然后有秩序地中止任务
 
 1.使用volatile的风险就是有可能终结不了阻塞的状态 如果线程被阻塞，它便不能核查共享变量，也就不能停止。这在许多情况下会发生
 
 2.thread.interrupt()中断阻塞状态线程 volatilego 目录思考 RightWayStopBlockThread   ......等示例
 
 