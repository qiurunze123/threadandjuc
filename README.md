![互联网 Java 多线程那些事](https://raw.githubusercontent.com/qiurunze123/imageall/master/thread100.png)

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

> QQ群 :

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/qq.png)
 
 多线程从基础到进阶，分析入坑出坑，以及工作实操,最后会分享一个项目，针对如何进行大数据量（经测试几亿数据完全搞的定）进行安全高可用的策略，
 示例为高可用高可靠高性能 三高导入系统 DEMO分析 ,如何进行数据分片,数据导入,计算,多线程策略等等 本文属于进阶系列，有问题或者更好的想法可以一起探讨！ 
 一点小建议：学习本系列知识之前，如果你完全没接触过 `SpringBoot`、`CountDownLatch`、`线程池`、`工作队列`、`工作窃取等` 等，那么我建议你可以先在网上搜一下每一块知识的快速入门， 也可以下载本项目边做边学习，
 我的项目完全是实战加讲解不想写一堆的文章，浪费我们的生命，你还不懂内层含义，想要明白就边实际操作边学习，效果会更好！加油💪💪
 
 
 [![Travis](https://img.shields.io/badge/language-Java-yellow.svg)](https://github.com/qiurunze123)
    
###  [如要提交代码请先看--提交合并代码规范提交者的后面都会有署名方便大家问问题](/docs/code-criterion.md)
###  [多线程之前更新版本 -- 请进代码路径：com.geek.threadandjuc](/docs/thread-base-1.md)

####  [java并行程序基础](/docs/thread-base-3.md)

####  [java内存模型和线程安全](/docs/thread-base-4.md)

####  [valatile 专题解析](/docs/thread-base-5.md)

####  [无锁类 CAS , ABA , Atomic ......](/docs/thread-base-6.md)

####  [JDK并发包 reentrantlock , condition , semaphone , readwritelock ,CountDownLatch,BlockingQueue.....](/docs/thread-base-7.md)

####  [JDK并发包 ConcurrentHashMap 精度分析](/docs/thread-base-8.md)

####  [JDK 线程池高度解析 ](/docs/thread-base-9.md)

####  [JDK ForkJoin 模式精度分析  ](/docs/thread-base-10.md)

####  [JDK 单例模式 不变模式 Future模式 生产者 消费者 .......  ](/docs/thread-base-11.md)

####  [NIO AIO 详解  ](/docs/thread-base-12.md)

####  [并发断点调试 JDK新特性 .....  ](/docs/thread-base-13.md)

=========================================================================


##### 高可用高可靠高性能 三高导入系统 DEMO分析 
| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |数据如何分片 | [解决思路](/docs/code-solve.md) |
| 001 |如何实现高可用 | [解决思路](/docs/jemter-solve.md) |
| 003 |如何实现高性能|[解决思路](/docs/code-solve.md)  |
| 003 |如何实现高可靠 |[解决思路](/docs/code-solve.md)  |
| 004 |如何自定义线程池以及使用与导入🙋🐓 |[解决思路](/docs/code-solve.md)  |
| 005 |如何利用CountDownLatch使用与导入|[解决思路](/docs/code-solve.md)  |
| 006 |表的设计与思考 |[解决思路](/docs/code-solve.md)  |
| 007 |如何控制数据一致性 |[解决思路](/docs/code-solve.md)  |
| 008 |如何是实现幂等性 |[解决思路](/docs/code-solve.md)  |
| 009 |如何使用ForkJoin进行使用与导入 |[解决思路](/docs/code-solve.md)  |
| 010 |如何使用栅栏进行使用与导入 |[解决思路](/docs/code-solve.md)  |
| 011 |数据的导入性能数据 |[解决思路](/docs/code-solve.md)  |
| 012 |Apollo 的安装与使用 |[解决思路](/docs/code-solve.md)  |
| 013 |为什么需要一个环境隔离系统来维护环境数据 |[解决思路](/docs/code-solve.md)  |
| 014 |定时任务的开启与一些小的技巧|[解决思路](/docs/code-solve.md)  |
| 015 |服务波动怎么解决数据不丢失 |[解决思路](/docs/code-solve.md) |





