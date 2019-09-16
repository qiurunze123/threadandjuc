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
  
  while循环在try块里，如果try在while循环里时，应该在catch块里重新设置一下中断标示，因为抛出InterruptedException异常后，中断标示位会自动清除

      
 
 
    