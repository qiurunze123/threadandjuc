### 多线程如何正确优雅的中断线程

 我们要使用interrupt来进行通知而非强制 （请求线程停止好处是安全）
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/basethread100.png)
 
 停止一个线程意味着在任务处理完任务之前停掉正在做的操作,有许多方法 首先由已经被废弃掉的stop 
 
 **suspend()方法就是将一个线程挂起(暂停),resume()方法就是将一个挂起线程复活继续 废弃理由 有可能造成大量资源浪费造成内存泄露**
  
 **stop 废弃理由 就是相当于把电脑关机了啥也没保存 会造成很严重的后果**
 
   com.geekagain.stopthread 此为各种方法中断线程的方式
 
 
    