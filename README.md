![互联网 Java 多线程那些事](https://raw.githubusercontent.com/qiurunze123/imageall/master/thread100.png)

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

> QQ群（秒杀群）:

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/qq.png)
 
 > QQ群（高并发多线程）:
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/高并发.png)

 
 **重点**
 ### [three-high-import 高可用 高可靠 高性能 三高多线程导入系统（该项目意义为理论贯通)](/docs/code-solve.md)

 three-high-import 项目意义在于利用多线程进行千万级别导入,实现`可扩展`,`高性能`,`高可用`,`高可靠`三个高，本项目可以在千万级别数据实现无差别高性能数据上报
 与导入,与普通导入相比性能提高10倍左右,而且规避风险在偶尔的机器宕机，网络波动等情况出现时，仍能够实现`数据一致`，`数据可靠`，`数据重试`，`数据报警`等功能,在一些重要数据
 例如： 对账 ， 账户金额，账单等，需要每日定时任务而且有高风险的数据实现数据无错误！
 
 多线程从基础到进阶，分析入坑出坑，以及工作实操,最后会分享一个项目，针对如何进行大数据量（经测试几亿数据完全搞的定）进行安全高可用的策略，
 示例为高可用高可靠高性能 三高导入系统 DEMO分析 ,如何进行数据分片,数据导入,计算,多线程策略等等 本文属于进阶系列，有问题或者更好的想法可以一起探讨！ 
 一点小建议：学习本系列知识之前，如果你完全没接触过 `SpringBoot`、`CountDownLatch`、`线程池`、`工作队列`、`工作窃取等` 等，那么我建议你可以先在网上搜一下每一块知识的快速入门， 也可以下载本项目边做边学习，
 我的项目完全是实战加讲解不想写一堆的文章，浪费我们的生命，你还不懂内层含义，想要明白就边实际操作边学习，效果会更好！加油💪💪
 
 
 [![Travis](https://img.shields.io/badge/language-Java-yellow.svg)](https://github.com/qiurunze123)
 
 
###  [如要提交代码请先看--提交合并代码规范提交者的后面都会有署名方便大家问问题](/docs/code-criterion.md)
###  [多线程之前更新版本 -- 请进代码路径：com.geek.threadandjuc](/docs/thread-base-1.md)
 
### 多线程基础与进阶更新
 
 | ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 重点 |AQS--一切的基础 Doug Lee 是个天才 (太大等待更新) | [解决思路](/docs/AQS.md) |
 | 000 |java并行程序基础(已完成) | [解决思路](/docs/thread-base-3.md) |
 | 001 |java内存模型和线程安全(已完成) | [解决思路](/docs/thread-base-4.md) |
 | 003 |valatile 专题解析(已完成) |[解决思路](/docs/thread-base-5.md)  |
 | 003 |无锁类 CAS , ABA , Atomic ......(已完成) |[解决思路](/docs/thread-base-6.md)  |
 | 004 |公平锁,非公平锁，可重入锁，递归锁，自旋锁等的理解..(已完成) |[解决思路](/docs/lock.md)  |
 | 005 |阻塞队列..(已完成) |[解决思路](/docs/blocking.md)  |
 | 006 |插播 synchronized和lock 的区别 彻底分析..(已完成) |[解决思路](/docs/sandl.md)  |
 | 007 |风骚的线程池..(已完成) |[解决思路](/docs/Threadpool.md)  |
 | 008 |AQS .....(有待分析) |[解决思路](/docs/thread-base-14.md)  |
 | 009 |JDK并发包reentrantlock,condition , semaphone , readwritelock ,CountDownLatch...(粗略版已完成) |[解决思路](/docs/thread-base-7.md)  |
 | 010 |CountDownLatch |[解决思路](/docs/CountDownLatch.md)  |
 | 011 |reentrantlock 源码分析 com.geek.reentrantlock.ReentrantLockDetails  |[解决思路](/docs/thread-base-7.md)  |
 | 012 |JDK并发包 ConcurrentHashMap 精度分析(粗略版已完成) |[解决思路](/docs/thread-base-8.md)  |
 | 013 |JDK 线程池高度解析(粗略版已完成)|[解决思路](/docs/thread-base-9.md)  |
 | 014 |JDK ForkJoin 模式精度分析(粗略版已完成) |[解决思路](/docs/thread-base-10.md)  |
 | 015 |JDK 单例模式 不变模式 Future模式 生产者 消费者 ....... (粗略版已完成)） |[解决思路](/docs/thread-base-11.md)  |
 | 016 |NIO AIO 详解 (粗略版已完成)|[解决思路](/docs/thread-base-12.md)  |
 | 017 |并发断点调试 JDK新特性 .....  (粗略版已完成) |[解决思路](/docs/thread-base-13.md)  |
 | 018 |锁优化 ..... (有待分析)   |[解决思路]((/docs/thread-base-13.md))  |
 | 019 |数据伪共享 false—shareing disruptor前传..... )   |[解决思路]((/docs/false-shareing.md))  |
     更新此问题的出发点是 **disruptor框架和百度的分布式id生成策略** 
     https://github.com/baidu/uid-generator/blob/master/README.zh_cn.md