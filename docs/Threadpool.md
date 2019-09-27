### 导航

| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |为什么使用线程池又什么好处和优势| [解决思路](/docs/Threadpool.md) |
| 001 |线程池使用 | [解决思路](/docs/Threadpool.md) |
| 002 |手写线程池 | [解决思路](/docs/Threadpool.md) |
| 003 |线程池参数介绍 | [解决思路](/docs/Threadpool.md) |
| 004 |线程池生产上如何合理配置线程池| [解决思路](/docs/Threadpool.md) |
| 005 |线程池谈一谈拒绝策略 | [解决思路](/docs/Threadpool.md) |
| 006 |如何自定义线程池 | [解决思路](/docs/Threadpool.md) |
| 007 |在工作中你是如何配置线程池的 | [解决思路](/docs/Threadpool.md) |


#### 为什么使用线程池以及优势

线程池做的工作主要是控制运行的线程的数量,处理过程中将任务加入队列,然后在线程创建后启动这些任务,
如果先生超过了最大数量,超出的数量的线程排队等候,等其他线程执行完毕,再从队列中取出任务来执行.

他的主要特点为:线程复用:控制最大并发数:管理线程.

第一:降低资源消耗.通过重复利用自己创建的线程降低线程创建和销毁造成的消耗.

第二: 提高响应速度.当任务到达时,任务可以不需要等到线程和粗昂就爱你就能立即执行.

第三: 提高线程的可管理性.线程是稀缺资源,如果无限的创阿金,不仅会消耗资源,还会较低系统的稳定性,使用线程池可以进行统一分配,调优和监控.

#### 设计图示

   线程池是通过Executor框架实现的,该框架中用到了Executor,Executors,ExecutorService,ThreadPoolExecutor这几个类.
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/tp1.png)

#### 线程池运行流程

 **线程池运行流程**
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/theradpool8.png)
#### 线程池7大参数解析

 1.corepoolsize 线程池常驻核心线程数 
 
     在创建了线程池后,当有请求任务来之后,就会安排池中的线程去执行请求任务,近视理解为今日当值线程
     
     当线程池中的线程数目达到corePoolSize后,就会把到达的任务放入到缓存队列当中. 
     
 2.maximumPoolSize 线程池能够容乃同时执行的最大线程数 >=1
 
 3.keepaliveTime 多余的空闲线程的存活时间当空闲线程时间达到keepAliveTime的值时多余线程会被销毁
 
     只有当线程池中的线程数大于corePoolSize时keepAliveTime才会起作用,知道线程中的线程数不大于corepoolSIze,
 
 4.unit keepAliveTime 的单位
 
 5.workQueue 任务队列 被提交但是尚未被执行的任务
 
 6.threadFactory 表示生成线程池中工作线程的线程工厂用户创建新线程一般用默认的即可
 
 7.handler 拒绝策略当线程池满了并且工作线程数大于等于线程池的最大显示数时来如何拒绝
 
#### 底层工作原理

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/tp2.png)
 
#### 谈一谈拒绝策略 

等待队列也已经排满了,再也塞不下新的任务了同时,

线程池的max也到达了,无法接续为新任务服务

这时我们需要拒绝策略机制合理的处理这个问题.

AbortPolicy:直接抛出异常组织系统正常工作

CallerRunPolicy：只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务

DiscardOldestPolicy：丢弃最老的一个请隶，尝试再次提交当前任务

DiscardPolicy:直接丢弃任务不予处理也不抛出异常，如果需要自定义拒绝簽略可以实现RejectdExceutionHandler接口

已上的内置策略均实现了rejectExcutionHandler接口