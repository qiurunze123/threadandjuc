#### 什么是CAS ? 

CAS 是 compareAndSwap的缩写  比较交换 类似于java中的乐观锁

乐观锁与悲观所的区别在于 -- 乐观锁趋向于不加锁来处理资源比如给记录加入version这种方法 记录版本号 他将内存地址的内容和定值相比较
只有在相同的情况下，才会更新期望值如果已经被另一个线程操作更新则更新失败 ！ 会返回boolean来判断

### CAS的底层原理 ??

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/casunsafe.png)
 
底层大部分都是有unsafe完成，unsafe自己属于JDK-- sun包下的，在我查看源码发现unsafe是 是CAS的核心类 
由于Java 方法无法直接访问底层 ,需要通过本地(native)方法来访问,UnSafe相当于一个后门,基于该类可以直接操作特定的内存数据.
UnSafe类在于sun.misc包中,其内部方法操作可以向C的指针一样直接操作内存,因为Java中CAS操作的执行依赖于UNSafe类的方法.

`**注意UnSafe类中所有的方法都是native修饰的,也就是说UnSafe类中的方法都是直接调用操作底层资源执行响应的任务**` 
  
  ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/casunsafe2.png)
  
  valueoff是该变量在内存中的偏移地址 ，unsafe就是根据内存偏移地址来获取数据的！

  变量value 被volatile修饰 保证了多线程的可见性  
  
  在多处理器情况下必须使用lock指令加锁来完成。从这个例子就可以比较清晰的了解CAS的底层实现了，
  当然不同的操作系统和处理器的实现会有所不同，大家可以自行了解。 `计算机并发原语`是叫这个吧哈哈！！
  
  
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/casunsafe3.png)
   
   此处为jdk实现 
   
       public final int getAndAddInt(Object var1, long var2, int var4) {
              int var5;
              do {
                  //兄弟们告诉我var1 和var2 定位到的内存地址的值是多少
                  var5 = this.getIntVolatile(var1, var2);
              } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
      
              return var5;
          }
          
          1.var1 就是 AtomicInteger new 的本身
          var2 该对象的引用地址 就是偏移量 valueoff
          var4 需要变动的数量
          var5是通过var1和var2找到的主内存中的真实值
          用该对象当前的值与var5比较 如果相同 更新var5+var4并返回true 如果不同继续循环比较直至更新完成！


  假设线程A和线程B两个线程同时执行getAndAddInt操作(分别在不同的CPU上):
 
1.AtomicInteger里面的value原始值为3,即主内存中AtomicInteger的value为3,根据JMM模型,线程A和线程B各自持有一份值为3的value的副本分别到各自的工作内存.
 
2.线程A通过getIntVolatile(var1,var2) 拿到value值3,这是线程A被挂起.
 
3.线程B也通过getIntVolatile(var1,var2) 拿到value值3,此时刚好线程B没有被挂起并执行compareAndSwapInt方法比较内存中的值也是3 成功修改内存的值为4 线程B打完收工 一切OK.
 
 4.这是线程A恢复,执行compareAndSwapInt方法比较,发现自己手里的数值和内存中的数字4不一致,说明该值已经被其他线程抢先一步修改了,那A线程修改失败,只能重新来一遍了.
 
 5.线程A重新获取value值,因为变量value是volatile修饰,所以其他线程对他的修改,线程A总是能够看到,线程A继续执行compareAndSwapInt方法进行比较替换,直到成功.

  
      CAS并发原语提现在Java语言中就是sun.miscUnSaffe类中的各个方法.调用UnSafe类中的CAS方法,
      JVM会帮我实现CAS汇编指令.这是一种完全依赖于硬件 功能,通过它实现了原子操作,再次强调,
      由于CAS是一种系统原语,原语属于操作系统用于范畴,是由若干条指令组成,
      用于完成某个功能的一个过程,并且原语的执行必须是连续的,在执行过程中不允许中断,也即是说CAS是一条原子指令
      ,不会造成所谓的数据不一致的问题.
  
### CAS缺点 

    1.类似于自旋锁的循环等待更新可能有些时刻很耗资源
    2.产生ABA问题

ABA 的问题，就是一个值从A变成了B又变成了A，使用CAS操作不能发现这个值发生变化了，尽管线程操作成功但是实际上仍然被处理过，处理方式是可以使用携带类似时间戳的版本AtomicStampedReference

### 解决方法 

 时间原子自引用
 
 AtomicStampedReference 案例ABAdemo
 
 
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/casunsafe5.png)
