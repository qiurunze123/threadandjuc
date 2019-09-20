### 多线程wait notify notifyall join sleep yield作用与方法详细解读

1.wait notify notifyall 解读

```diff
   ! 1.wait() notify() notifyall() 方法是Object的本地final方法 无法被重写
   ! 2.wait() 使当前的线程阻塞 前提是必须获取到锁 一般配合synchronized 关键字使用 即一般在synchronized里面 使用wait notify notifyall
   ! 3.由于wait() notify() notifyall() 在synchronized里面执行 那么说明 当前线程一定是获取锁了
   ! 4. 当线程执行wait的时候会释放当前锁让出CPU资源进入等待状态
   ! 5. 当执行notify()/notifyall()的时候会唤醒一个或者多个处于正在等待的线程 然后继续执行知道执行完毕synchronized或者再次遇到wait
```
**生产者消费者model (ProducerAndConsumerModel)**

1.为什么要使用生产者消费者模式

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase004-6.png)

消费方和生产方进行解耦更好配合

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase004-7.png)
   
**代码解析： 手写生产者消费者设计模式**

com.geekagain.waitnotify.producerandconsumer2

com.geekagain.waitnotify.producerandconsumer


彩蛋：

用两个线程交替打印1--100的奇偶

PrintOddEvenTwoThread synochronized  实现

PrintOddEvenTwoThreadVersion2 wait notify实现

#### sleep 

    相同点：
    1.	Wait和sleep方法都可以使线程阻塞，对应线程状态是Waiting或Time_Waiting
    2.	wait和sleep方法都可以响应中断Thread.interrupt()
    不同点：
    1.	wait方法的执行必须在同步方法中进行，而sleep则不需要。 
    2.	在同步方法里执行sleep方法时，不会释放monitor锁，但是wait方法会释放monitor锁
    3.	sleep 方法短暂休眠之后会主动退出阻塞，而没有指定时间的wait方法则需要被其他线程中断后才能退出阻塞。
    4.	wait()和notify(),notifyAll()是Object类的方法，sleep()和yield()是Thread类的方法

************************
sleep 方法可以让线程进入Waiting状态 并且不占用CPU资源但是不会释放锁 直到规定时间后在执行 休眠期间如果被中断会抛出异常并清楚中断状态


#### join 作用和方法

新的线程加入我们 我们要等待他执行完再出发

join 期间 线程的状态是 WAITING 状态 com.geekagain.joingo


#### yield 作用和方法

使当前线程从执行状态（运行状态）变为可执行态（就绪状态）。cpu会从众多的可执行态里选择，
也就是说，当前也就是刚刚的那个线程还是有可能会被再次执行到的，并不是说一定会执行其他线程而该线程在下一次中不会执行到了

com.geekagain.yieldgo


