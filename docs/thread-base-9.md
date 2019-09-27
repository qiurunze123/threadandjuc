### 线程池精讲

| ID | Problem  
| --- | ---   |
| 001 |什么是线程池|
| 002 |线程池核心讲解|
| 003 |常见的四种线程池|
| 003-1 |阻塞队列一览|
| 003-2 |拒绝策略一览|
| 003-3 |线程池运行流程|
| 004 |浅谈线程池生产工作中的应用|
| 004-1|线程池在生产中选择哪种线程池|
| 005 |线程池的核心参数有哪些|
| 006 |线程池生产上如何合理配置线程池|
| 007 |线程池谈一谈拒绝策略 | 


线程池各类的关系：

Executors -- new ThreadPoolExecutor

ThreadPoolExecutor extends AbstractExecutorService
 
AbstractExecutorService(抽象类) implements ExecutorService

interface ExecutorService extends Executor 

#### 什么是线程池 

为了避免重复的创建线程 线程池的出现可以让线程复用 通俗的讲 当有任务来的时候 就会像线程池里面拿一个线程 当工作完成后 不是关闭线程 而是归还线程到线程池中

这样避免了重复开销 这样就会节省性能和时间

#### 线程池的核心讲解
 
核心参数 

corePoolsize : 线程中允许的核心线程数

maximumPoolsize : 该线程所允许的最大线程数

keepAliveTime : 空余线程的存活时间并不会对所有的线程起作用 如果线程数大于corePoolsize  那么这些线程就不会因为被空闲太久而关闭 除非你调用 allowcorethreadtimeout 方法 
这个方法可以使核心线程数也被回收

    只有当线程池中的线程数大于corePoolSize时keepAliveTime才会起作用,知道线程中的线程数不大于corepoolSIze,


unit : 时间单位

workQueue : 阻塞队列 在此的作用就是用来存放线程

threadFatory: 线程工厂 可以为线程池创建新线程

defaultHnadler: 拒绝策略 当线程失败等 如何处理方式 

-----------------------------------------------------------------------------

#### 常见的四种线程池 

1.FixedThreadPool 有固定的线程池 其中corePoolSize =  maxinumPoolSize 且keepalivetime  为0 适合线程稳定的场所

2.singleThreadPool 固定数量的线程池且数量为1  corePoolSize =  maxinumPoolSize= 1 keepaliveTime =0 

3.cachedThreadPool  corePoolSize=0  maxiumPoolSize 不停的创建线程

4.ScheduledThreadPool 具有定期执行任务功能的线程池
 
#### 阻塞队列一览 workQueue


1.数组阻塞队列 ArrayBlockingQueue 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew48.png)

对应线程池队列：

有界的任务队列可以使用ArrayBlockingQueue实现。当使用有界队列时，若有新的任务需要执行，如果线程池的实际线程数小于corePoolSize，
则会优先创建新的线程，若大于corePoolSize，则会将新任务假如等待队列。
若等待队列已满，无法加入，在总线程数，不大于maximumPoolSize的前提下，创建新的进程执行任务。若大于maximumPoolSize，则执行拒绝策略。

2.延迟队列DelayQueue

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew49.png)

3.链阻塞队列 LinkedBlockingQueue

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew50.png)

对应线程池队列：

无界的任务队列可以通过LinkedBlockingQueue类实现。与有界队列相反，除非系统资源耗尽，否则无界的任务队列不存在任务入队失败的情况。
当有新的任务到来，系统的线程数小于corePoolSize时，线程池会产生新的线程执行任务，但当系统的线程数达到corePoolSize后，就会继续增加。
若后续仍有新的任务假如，而又没有空闲的线程资源，则任务直接进入对列等待。若任务创建和处理的速度差异很大，无界队列会保持快速增长，直到耗尽系统内存。

3.同步队列 SynchronousQueue 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew51.png)

SynchronousQueue经常用来,一端或者双端严格遵守"单工"(单工作者)模式的场景,队列的两个操作端分别是productor和consumer.常用于一个productor多个consumer的场景。

在ThreadPoolExecutor中,通过Executors创建的cachedThreadPool就是使用此类型队列.已确保,如果现有线程无法接收任务(offer失败),将会创建新的线程来执行.

#### 拒绝策略

等待队列也已经排满了,再也塞不下新的任务了同时,

线程池的max也到达了,无法接续为新任务服务

这时我们需要拒绝策略机制合理的处理这个问题.

AbortPolicy:直接抛出异常组织系统正常工作

CallerRunPolicy：只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务

DiscardOldestPolicy：丢弃最老的一个请隶，尝试再次提交当前任务

DiscardPolicy:直接丢弃任务不予处理也不抛出异常，这是最好的拒绝策略 

如果需要自定义拒绝簽略可以实现RejectdExceutionHandler接口

已上的内置策略均实现了rejectExcutionHandler接口


#### 线程池运行流程

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadpool101.png)

    1.在创建线程池后 等待提交过来的任务请求
    
    2.当调用execute()方法添加一个请求任务时线程池会做如下判断：
    
       ① 如果正在运行线程数量小于corePoolSize 那么马上创建线程运行这个任务
       ② 如果正在运行的线程数量大于或者等于corePoolSize 那么将任务放入队列
       ③ 如果这时候队列满了且正在运行的线程数量还小于maximumPoolSize 那么还是要创建非核心线程来立刻运行这个任务
       ④ 如果队列满了且正在运行的线程数量大于或者等于maximumPoolSize 那么线程池会启动饱和拒绝策略来执行
       
    3.当一个线程完成任务时 他会从队列中取下一个任务
    4.当一个线程无事可做超过一定时间keepAliveTime 时 线程池会判断： 如果当前运行的线程大于corePoolSize那么这个线程就会被停掉

#### 线程池在生产中选择哪种

1.在生产中我们JDK自带的线程池 一个不用 我们需要自己创建线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。 
    
    说明：使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源的开销，
    解决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题
    
2.线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
  
  说明：Executors返回的线程池对象的弊端如下：
  
  1）FixedThreadPool和SingleThreadPool:允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
  
  2）CachedThreadPool和ScheduledThreadPool:允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。
  
#### 如何合理的配置线程池

分为cpu密集型和io密集型

    cpu密集型的意思就是该任务需要大量的计算 而没有阻塞 cpu一直全速运行 CPU密集型任务只有在真正的多核CPU上才能得到加速
    而在真正的cpu上 无论你开你个线程模拟该任务都不可能得到加速 因为cpu运算能力就那些
    cpu 密集型任务配置尽可能少的线程数量 一般公式
    cpu+1个线程的线程池

io密集型
    
    由于io密集型任务线程并不是一直在执行 则应配置尽可能多的线程 如cpu*2
    io密集型 即该任务需要大量io 及大量的阻塞
    在单线程上运行IO密集型的任务会导致大量的cpu运算能力浪费在等待
    所以在IO密集型任务中使用多线程可以大大的加速程序运行 即使在单核CPU上 这种加速主要是为了利用被浪费掉阻塞时间
    IO密集型 大部分线程都阻塞 故需要多配置线程
    参考公式 CPU核数/1 -阻塞系数 阻塞系数在0。8-0.9之间
    比如 8核cpu: 8/1-0.9 = 80个线程数





   corePoolSize在很多地方被翻译成核心池大小，其实我的理解这个就是线程池的大小。举个简单的例子：

　　假如有一个工厂，工厂里面有10个工人，每个工人同时只能做一件任务。

　　因此只要当10个工人中有工人是空闲的，来了任务就分配给空闲的工人做；

　　当10个工人都有任务在做时，如果还来了任务，就把任务进行排队等待；

　　如果说新任务数目增长的速度远远大于工人做任务的速度，那么此时工厂主管可能会想补救措施，比如重新招4个临时工人进来；

　　然后就将任务也分配给这4个临时工人做；

　　如果说着14个工人做任务的速度还是不够，此时工厂主管可能就要考虑不再接收新的任务或者抛弃前面的一些任务了。

　　当这14个工人当中有人空闲时，而新任务增长的速度又比较缓慢，工厂主管可能就考虑辞掉4个临时工了，只保持原来的10个工人，毕竟请额外的工人是要花钱的。
 
　　这个例子中的corePoolSize就是10，而maximumPoolSize就是14（10+4）。

　　也就是说corePoolSize就是线程池大小，maximumPoolSize在我看来是线程池的一种补救措施，即任务量突然过大时的一种补救措施。

　　不过为了方便理解，在本文后面还是将corePoolSize翻译成核心池大小。

　　largestPoolSize只是一个用来起记录作用的变量，用来记录线程池中曾经有过的最大线程数目，跟线程池的容量没有任何关系