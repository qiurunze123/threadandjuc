### 前言
大家好,线程在现在的工作中使用的频率较高，自己构建了一个小的demo，来和大家分享下自己对线程的理解，希望和大家分享一下，希望大家能从中收益，如果有意见和好的想法请加我！
 ＱＱ:3341386488
 邮箱：QiuRunZe_key@163.com

我会不断完善，希望大家有好的想法拉一个分支提高，一起合作！


    觉得不错对您有帮助，麻烦右上角点下star以示鼓励！长期维护不易 多次想放弃 坚持是一种信仰 专注是一种态度！


## 线程可见性

1.共享变量在线程间的可见性<br>
2.synchronized 实现可见性<br>
3.volitile 实现可见性<br>
　　指令重排序
　　as-if-serial语义
　　volatile使用注意事项<br>
synchronized和volitile比较<br>

代码测试

## 线程池讲解

1.线程池的好处：　减少对象的开销，消亡的开销，性能提高<br>
2.可以控制最大并发线程数，提高资源利用率，同时可以避免过多资源竞争，避免阻塞<br>
3.提供定时执行，定期执行，并发数控制等<br>

代码测试

## 线程安全－并发容器JUC--原理以及分析

1.arrayList --copyonWriteArraylist 优缺点<br>
2.HashSet,TreeSet -- CopyONWriteArraySet,ConcurrentSkipListSet<br>
3.hashMap , treeMap -- ConcurrentHashMap,ConcurentSkipListMap(key有序，支持更高并发)<br>

Concurrent同步工具类 

countDownLatch<br>
CyclicBarrier<br>
Semaphore<br>
Exchanger<br>
ReenTrantLock<br>
ReentrantReadWriteLock<br>

四种线程池与自定义线程池，底层代码
设计模式： 单例，Future Master-Worker,Producer-Consumer的模式原理和实现
===============================================================
CountDownLatch是一个辅助的工具类，它允许一个或者多个线程等待一系列指定的操作的完成.
以一个给定的数量初始化,countDown()每被调用一次，这一数量就减一.通过调用await()方法之一
线程可以阻塞等待这一数量到达0

三辆危险燃品汽车全部检查完毕后在发车, countdownlatch 初始值为3 每检查一辆减一 如果三辆车
都没有问题则发车  三辆车会等待 都检查完毕后才可以发车


CyclicBarrier一个同步辅助类,它允许一组线程互相等待,直到到达某
个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的
程序中,这些线程必须不时地互相等待,此时 CyclicBarrier 很有用。
因为该 barrier 在释放等待线程后可以重用,所以称它为循环的barrier


起跑例子 ：  有两种用法 一种必须全部等待 另一种有超时时间  在等待某个时间后
可以独自起跑 
====================================================
Semaphore一个计数信号量。信号量维护了一个许可集合; 通过acquire()
和release()来获取和释放访问许可证。只有通过acquire获取了许可证的线
程才能执行,否则阻塞。通过release释放许可证其他线程才能进行获取.


公平性:没有办法保证线程能够公平地可从信号量中获得许可。也就是
说,无法担保掉第一个调用 acquire() 的线程会是第一个获得一个许可的
线程。如果第一个线程在等待一个许可时发生阻塞,而第二个线程前来
索要一个许可的时候刚好有一个许可被释放出来,那么它就可能会在第
一个线程之前获得许可。如果你想要强制公平,Semaphore 类有一个具
有一个布尔类型的参数的构造子,通过这个参数以告知 Semaphore 是否
要强制公平。强制公平会影响到并发性能,所以除非你确实需要它否则
不要启用它
===================================================

Exchanger Exchanger 类表示一种两个线程可以进行互相交换对象的会
和点。
只能用于两个线程之间,并且两个线程必须都到达汇合点才会进行数
据交换






 