> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

> QQ群 : [453259026](453259026) 

> 转载等操作请联系我！ 我更希望你在我的基础上重新自己写一版

 #### :couple: 三高导入 兵马未动粮草先行 
 
  ### [three-high-import 高可用 高可靠 高性能 三高多线程导入系统（该项目意义为理论贯通)](/docs/code-solve.md)
   [![Travis](https://img.shields.io/badge/language-Java-yellow.svg)](https://github.com/qiurunze123)

  three-high-import 项目意义在于利用多线程进行千万级别导入,实现`可扩展`,`高性能`,`高可用`,`高可靠`三个高，本项目可以在千万级别数据实现无差别高性能数据上报
  与导入,与普通导入相比性能提高10倍左右,而且规避风险在偶尔的机器宕机，网络波动等情况出现时，仍能够实现`数据一致`，`数据可靠`，`数据重试`，`数据报警`等功能,在一些重要数据
  例如： 对账 ， 账户金额，账单等，需要每日定时任务而且有高风险的数据实现数据无错误！
  多线程从基础到进阶，分析入坑出坑，以及工作实操,最后会分享一个项目，针对如何进行大数据量（经测试几亿数据完全搞的定）进行安全高可用的策略，
  示例为高可用高可靠高性能 三高导入系统 DEMO分析 ,如何进行数据分片,数据导入,计算,多线程策略等等 本文属于进阶系列，有问题或者更好的想法可以一起探讨！ 
  一点小建议：学习本系列知识之前，如果你完全没接触过 `SpringBoot`、`CountDownLatch`、`线程池`、`工作队列`、`工作窃取等` 等，那么我建议你可以先在网上搜一下每一块知识的快速入门， 也可以下载本项目边做边学习，
  我的项目完全是实战加讲解不想写一堆的文章，浪费我们的生命，你还不懂内层含义，想要明白就边实际操作边学习 加油💪💪
 
 - 三高导入项目
     - [表设计](/docs/code-solve.md)<br>
     - [数据如何分片](/docs/code-solve.md)
     - [如何实现高可用](/docs/code-solve.md)<br>
     - [如何实现高性能](/docs/code-solve.md)<br>
     - [如何实现高可靠](/docs/code-solve.md)
     - [如何自定义线程池以及使用与导入](/docs/code-solve.md)
     - [数据的导入性能(之前的老版本三高导入10万数据大概13s左右新版本还在开发)](/docs/code-solve.md)
     - [进行线程拆分分配资源 ](/docs/code-solve-split.md)
 
###  [多线程之前更新版本 -- 请进代码路径：com.geek.threadandjuc](/docs/thread-base-1.md)
 
## 目录
#### :couple: Java多线程基础

  - 多线程基础
    - [你需要知道的几个概念](/docs/thread-base-0000.md)<br>
    - [多线程到底有几种实现方式](/docs/thread-base-000.md)
    - [多线程使用runnable与继承Thread类有什么区别](/docs/thread-base-001.md)<br>
    - [多线程如何正确优雅的中断线程](/docs/thread-base-002.md)<br>
    - [多线程的生命周期状态纽机流转错误错误说法](/docs/thread-base-003-1.md)
    - [多线程的生命周期状态纽机流转](/docs/thread-base-003.md)
    - [Thread和Object类](/docs/thread-base-003-2.md)
    - [多线程重要属性](/docs/thread-base-004.md)
    - [多线程异常处理机制](/docs/thread-base-005.md)
    - [多线程wait notify notifyall join sleep yield作用与方法详细解读](/docs/thread-base-006.md)
    - [多线程可能会带来什么问题](/docs/thread-base-007.md)
    - [多种单例模式](/docs/thread-base-008.md)
  - 并发容器
    - [map简介](/docs/bfrq.md)<br>
    - [vector和HashTable](/docs/bfrq.md)<br>
    - [ConcurrentHashMap](/docs/bfrq.md)<br>
    - [CopyOnWriteArrayList](/docs/bfrq.md)
    - [BlockingQueue](/docs/bfrq.md)<br>
    
#### :couple: 搞定ThreadLocal
  - 搞定ThreadLocal
    - [ThreadLocal定性使用场景介绍](/docs/ThreadLocal.md)<br>
    - [ThreadLocal一些方法使用](/docs/ThreadLocal.md)<br>
    - [ThreadLocal源码分析](/docs/ThreadLocal.md)<br>
    - [ThreadLocal内存泄露](/docs/ThreadLocal.md)<br>
    - [ThreadLocal如果避免内存泄露](/docs/ThreadLocal.md)<br>
    - [ThreadLocal在spring中的应用](/docs/ThreadLocal.md)<br>

#### :couple: 线程池

  - 线程池
    - [JDK 线程池高度解析](/docs/thread-base-9.md)<br>
    - [线程池是什么](/docs/threadpool0001.md)
    - [多次创建线程的劣势](/docs/threadpool0001.md)<br>
    - [什么时候使用线程池](/docs/threadpool0001.md)<br>
    - [线程池的优势](/docs/threadpool0001.md)
    - [Executor框架](/docs/threadpool0001.md)
    - [如何使用钩子函数来进行线程池操作](/docs/threadpool0001.md)
    - [线程池的重点属性](/docs/threadpool0001.md)
    - [多线程重要属性](/docs/threadpool0001.md)
    - [线程池的具体实现](/docs/threadpool0001.md)
    - [线程池的创建](/docs/threadpool0001.md)Z
    - [线程池参数解释](/docs/threadpool0001.md)
    - [线程池监控](/docs/threadpool0001.md)
    - [线程池的源码分析](/docs/threadpool0001.md)
       - [execute方法](/docs/threadpool0001.md)
       - [addWorker方法](/docs/threadpool0001.md)
       - [Worker类](/docs/threadpool0001.md)
       - [runWorker方法](/docs/threadpool0001.md)
       - [getTask方法](/docs/threadpool0001.md)
       - [processWorkerExit方法](/docs/threadpool0001.md)
       - [小结](/docs/threadpool0001.md)
       - [processWorkerExit方法](/docs/threadpool0001.md)
       - [手写线程池代码](/docs/threadpool0001.md)

#### :couple: 多线程进阶更新

  - 线程池
    - [多线程安全问题](/docs/JMM.md)<br>
    - [JMM多线程内存模型](/docs/JMM.md)
    - [JMM多线程内存模型](/docs/JMM-1.md)<br>
    - [死锁专题分析](/docs/dlock.md)<br>
    - [AQS--一切的基础 Doug Lee 是个天才](/docs/AQS.md)
    - [Future模式精度分析 Future 模式](/docs/Future.md)
    - [java并行程序基础](/docs/thread-base-3.md)
    - [java内存模型和线程安全](/docs/thread-base-4.md)
    - [valatile 专题解析](/docs/thread-base-5.md)
    - [无锁类 CAS , ABA , Atomic ......](/docs/thread-base-6.md)
    - [公平锁,非公平锁，可重入锁，递归锁，自旋锁](/docs/lock.md)
    - [阻塞队列.](/docs/blocking.md)
    - [ synchronized和lock 的区别 .](/docs/sandl.md)
    - [JDK并发包reentrantlock,condition , semaphone ，readwritelock ](/docs/thread-base-7.md)
    - [countDownLatch](/docs/CountDownLatch.md)
    - [reentrantlock 源码分析 ReentrantLockDetails](/docs/thread-base-7.md)
    - [JDK并发包 ConcurrentHashMap 精度分析](/docs/thread-base-8.md)
    - [JDK ForkJoin 模式精度分析](/docs/thread-base-10.md)
    - [JDK 单例模式 不变模式 Future模式 生产者 消费者](/docs/thread-base-11.md)
    - [NIO AIO 详解](/docs/thread-base-12.md)
    - [并发断点调试 JDK新特性](/docs/thread-base-13.md)
    - [锁优化 ](/docs/thread-base-13.md)
    - [数据伪共享 false—shareing disruptor](/docs/false-shareing.md)
    - [原子性小讲](/docs/atom.md)

     更新此问题的出发点是 **disruptor框架和百度的分布式id生成策略** 
     https://github.com/baidu/uid-generator/blob/master/README.zh_cn.md


#### :couple: 多线程安全专题

  - 线程池
    - [一共有几类线程安全的问题](/docs/threadsafe001.md)<br>
    - [哪些场景需要额外注意线程安全问题](/docs/threadsafe001.md)
    - [多线程切换上下文](/docs/threadsafe001.md)<br>
    - [死锁特辑](/docs/threadsafe001.md)<br>

### 多线程juc 分享 

 https://github.com/qiurunze123/threadandjuc/blob/master/docs/threadinterview.md


 #### 不知不觉 更新了小半年 从基础到并发到项目直接应用 许多地方不是很好但是是对一个人的考验 从明白到应用到写出来是一个繁琐的过程
 #### 耗时耗力不过很庆幸自己没有放弃终究是完成了 最后希望得到大家的指正与意见也希望帮助更多的人 
 
      千岩万壑不辞劳 远看方知出处高 溪涧岂能留得住 终归大海做波涛 2019/9/24
