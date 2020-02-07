### 什么是线程池

    1.线程池类似于线程缓存
    2.线程是一个稀缺资源 如果无限制的创建则会消耗系统资源还会降低系统的稳定性
    3.java中提供线程池会对线程进行统一的分配

#### 多次创建线程的劣势
    
      服务器需要接受并处理请求所以会为一个请求来分配一个线程来进行处理 如果每次请求都新创建一个线程的话
      十分方便但是会存在一些问题：
      1.如果并发的数量大那么每个线程的执行时间很短相应的创建和销毁线程就会很频繁
      2.当很频繁的创建和销毁线程会大大降低系统的效率 可能出现服务器在为每个请求创建新线程和销毁线程上花费的时间
      和消耗的系统资源要比实际用户处理的时间都要多
      
### 什么时候使用线程池

    **并发量大需要处理的任务量比较大**
    **单个任务处理的时间段**  

### 线程池的优势   
  
      1.重用存在的线程减少线程的创建，销毁的开销，能够减少cpu切换提高性能
      2.提高响应速度当任务到达的时候任务可以不需要等待线程创建就能立即执行
      3.提高线程的管理线程是稀缺资源如果无限制的创建，不仅会消耗资源还会降低系统的稳定性，使用线程池进行统一分配
      调优和监控
      
### Executor框架

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/pool100.png)
 
#### 这个是executor的整体结构
 
 executor下面有一个重要的子接口ExecutorService
 
 里面定义的方法为：
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/pool2.png)
 
#### 线程池的重点属性 

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    
    ctl是对线程池的运行状态+++++线程池中有效线程的数量进行控制的一个字段
    他包含俩部分的信息  1.线程池运行状态 runstate 
                     2.线程内的有效线程数量 workerCount 
    可以看到使用了Integer类型来保存高三位保存runstate 低29位保存workcount 
    COUNT_BITS 就是29，CAPACITY就是1左移29位减1（29个1），这个常量表示workerCount的上限值，大约是5亿

#### ctl相关方法

    private static int runStateOf(int c) { return c & ~CAPACITY; }
    private static int workerCountOf(int c) { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
    
    runStateOf：获取运行状态；
    workerCountOf：获取活动线程数；
    ctlOf：获取运行状态和活动线程数的值
    
    线程池存在5种状态
    RUNNING = 1
    << COUNT_BITS; //高3位为111
    SHUTDOWN = 0 << COUNT_BITS; //高3位为000
    STOP = 1 << COUNT_BITS; //高3位为001
    TIDYING = 2 << COUNT_BITS; //高3位为010
    TERMINATED = 3 << COUNT_BITS; //高3位为011
    
    --------------------------状态说明--------------------------------
    1、RUNNING
    (1) 状态说明：线程池处在RUNNING状态时，能够接收新任务，以及对已添加的任务进行
    处理。
    (02) 状态切换：线程池的初始化状态是RUNNING。换句话说，线程池被一旦被创建，就处
    于RUNNING状态，并且线程池中的任务数为0！
    2、 SHUTDOWN
    (1) 状态说明：线程池处在SHUTDOWN状态时，不接收新任务，但能处理已添加的任务。
    (2) 状态切换：调用线程池的shutdown()接口时，线程池由RUNNING -> SHUTDOWN。
    3、STOP
    (1) 状态说明：线程池处在STOP状态时，不接收新任务，不处理已添加的任务，并且会中
    断正在处理的任务。
    (2) 状态切换：调用线程池的shutdownNow()接口时，线程池由(RUNNING or
    SHUTDOWN ) -> STOP。
    4、TIDYING
    (1) 状态说明：当所有的任务已终止，ctl记录的”任务数量”为0，线程池会变为TIDYING
    状态。当线程池变为TIDYING状态时，会执行钩子函数terminated()。terminated()在
    ThreadPoolExecutor类中是空的，若用户想在线程池变为TIDYING时，进行相应的处理；
    可以通过重载terminated()函数来实现。
    (2) 状态切换：当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也
    为空时，就会由 SHUTDOWN -> TIDYING。 当线程池在STOP状态下，线程池中执行的
    任务为空时，就会由STOP -> TIDYING。
    5、 TERMINATED
    (1) 状态说明：线程池彻底终止，就变成TERMINATED状态。
    (2) 状态切换：线程池处在TIDYING状态时，执行完terminated()之后，就会由 TIDYING -
    > TERMINATED。
    进入TERMINATED的条件如下：
    线程池不是RUNNING状态；
    线程池状态不是TIDYING状态或TERMINATED状态；
    如果线程池状态是SHUTDOWN并且workerQueue为空；
    workerCount为0；
    设置TIDYING状态成功
    
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/pool3.png)

#### 线程池的具体实现

    ThreadPoolExecutor 默认线程池
    ScheduledThreadPoolExecutor 定时线程池
    
### 线程池的具体创建

    public ThreadPoolExecutor(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
            if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
                throw new IllegalArgumentException();
            if (workQueue == null || threadFactory == null || handler == null)
                throw new NullPointerException();
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.workQueue = workQueue;
            this.keepAliveTime = unit.toNanos(keepAliveTime);
            this.threadFactory = threadFactory;
            this.handler = handler;
        }
     
    任务提交 1.public void execute() //提交任务无返回值
            2.public Future<?> submit() // 任务执行后有返回值
            
#### 线程任务操作流程

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/pool4.png)

#### 线程池参数解释 
    
    1.corePoolSize 线程池中的核心线程数，当提交一个任务时，线程池创建一个新线程执行任务，直到当前线程数等于corePoolSize 
    如果当前线程数等于corePoolSize 继续提交的任务被保存到阻塞队列中等待被执行 如果执行到了prestartAllCoreThreads()方法
    线程池会提前创建并启动当前线程
    2.maximumPoolSize 线程池中允许的最大线程数如果阻塞队列满了则需要创建新的线程执行任务 前提是当前线程小于maximumPoolSize
    3.keepAliveTime 线程池维护线程所允许的空闲时间 当线程池的线程大于corePoolSize 的时候 这个时候如果没有新的任务提交 核心线程
    外的线程不会销毁而是会等待直到等待的时间超过了keepAliveTime
    4.unit keepAliveTime单位
    5.workQueue 用来保存被执行等待任务的队列 且任务必须实现了 runnable接口 在JDK中提供了如下阻塞队列：
       1、ArrayBlockingQueue：基于数组结构的有界阻塞队列，按FIFO排序任务；
       2、LinkedBlockingQuene：基于链表结构的阻塞队列，按FIFO排序任务，吞
       吐量通常要高于ArrayBlockingQuene；
       3、SynchronousQuene：一个不存储元素的阻塞队列，每个插入操作必须等到
       另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于
       LinkedBlockingQuene；
       4、priorityBlockingQuene：具有优先级的无界阻塞队列
    6.threadFactory  它是ThreadFactory类型的变量，用来创建新线程。默认使用
                     Executors.defaultThreadFactory() 来创建线程。使用默认的ThreadFactory来创建线程
                     时，会使新创建的线程具有相同的NORM_PRIORITY优先级并且是非守护线程，同时也设
                     置了线程的名称
    7.handler
      线程池的饱和策略，当阻塞队列满了，且没有空闲的工作线程，如果继续提交任务，必
      须采取一种策略处理该任务，线程池提供了4种策略：
      1、AbortPolicy：直接抛出异常，默认策略；
      2、CallerRunsPolicy：用调用者所在的线程来执行任务；
      3、DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
      4、DiscardPolicy：直接丢弃任务；
      上面的4种策略都是ThreadPoolExecutor的内部类。
      当然也可以根据应用场景实现RejectedExecutionHandler接口，自定义饱和策略，如
      记录日志或持久化存储不能处理的任务。