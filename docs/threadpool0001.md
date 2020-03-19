### 什么是线程池

    线程池类似于一个线程缓存 就是事先创建若干个可执行得线程放入一个池容器中 需要的时候从池中获取线程不用
    自己创建使用完毕后不需要销毁线程而是放回池中从而减少创建和销毁线程得开销
    
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
    RUNNING = -1<< COUNT_BITS; //高3位为111
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
    
    newFixedThreadPool
    return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    
    newSingleThreadExecutor
    new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
    
    newCachedThreadPool
    new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    
    newWorkStealingPool
    new ForkJoinPool(Runtime.getRuntime().availableProcessors(),ForkJoinPool.defaultForkJoinWorkerThreadFactory,null, true);
    
#### 如何使用钩子函数来进行线程池操作

com.executor.PauseableThreadPool 如何在使用线程池前后添加钩子函数来进行数据监控
    
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
      记录日志或持久化存储不能处理的任务

#### 线程池监控

    public long getTaskCount() //线程池已执行与未执行的任务总数
    public long getCompletedTaskCount() //已完成的任务数
    public int getPoolSize() //线程池当前的线程数
    public int getActiveCount() //线程池中正在执行任务的线程数量
    
### 线程池源码分析
#### execute 方法 

    public void execute(Runnable command) {
    //如果任务为空则抛异常
            if (command == null)
                throw new NullPointerException();
            //clt记录着runState和workerCount
            int c = ctl.get();
            
            //workerCountOf方法取出低29位的值，表示当前活动的线程数
            //如果当前活动线程数小于corePoolSize，则新建一个线程放入线程池中
            //并把任务添加到该线程中
            
            if (workerCountOf(c) < corePoolSize) {
            
            //addWorker中的第二个参数表示限制添加线程的数量是根据corePoolSize来判断还是maximumPoolSize来判断
            //如果为true，根据corePoolSize来判断 如果为false，则根据maximumPoolSize来判断
            
                if (addWorker(command, true))
                    return;
                //如果添加失败，则重新获取ctl值
                c = ctl.get();
            }
            
            //如果当前线程池是运行状态并且任务添加到队列成功
            if (isRunning(c) && workQueue.offer(command)) {
            //重新检查状态 如果不是运行状态 由于之前已经入队那么需要移除该command 
            //执行移除和检查成功后 -- handler使用拒绝策略 对该任务进行处理 整个方法返回
            
                int recheck = ctl.get();
                if (! isRunning(recheck) && remove(command))
                    reject(command);
                    
             获取线程池中的有效线程数，如果数量是0，则执行addWorker方法
             * 这里传入的参数表示：
             * 1. 第一个参数为null，表示在线程池中创建一个线程，但不去启动；
             * 2. 第二个参数为false，将线程池的有限线程数量的上限设置为
             maximumPoolSize，添加线程时根据maximumPoolSize来判断
             
                else if (workerCountOf(recheck) == 0)
                    addWorker(null, false);
            }
            
            //如果线程执行到这里那么说明
            //* 1. 线程池已经不是RUNNING状态；
              * 2. 线程池是RUNNING状态，但workerCount >= corePoolSize并且
              workQueue已满。
              * 这时，再次调用addWorker方法，但第二个参数传入为false，将线程池的有限
              线程数量的上限设置为maximumPoolSize；
              * 如果失败则拒绝该任务
            else if (!addWorker(command, false))
                reject(command);
        }
    
#### 总结execute 方法

    简单来说，在执行execute() 方法时如果状态是Running 在执行过程如下所示
    1. 如果workerCount < corePoolSize，则创建并启动一个线程来执行新提交的任
    务；
    2. 如果workerCount >= corePoolSize，且线程池内的阻塞队列未满，则将任务添
    加到该阻塞队列中；
    3. 如果workerCount >= corePoolSize && workerCount <
    maximumPoolSize，且线程池内的阻塞队列已满，则创建并启动一个线程来执行新
    提交的任务；
    4. 如果workerCount >= maximumPoolSize，并且线程池内的阻塞队列已满, 则根
    据拒绝策略来处理该任务, 默认的处理方式是直接抛异常。
    这里要注意一下addWorker(null, false);
    也就是创建一个线程，但并没有传入任务，因为
    任务已经被添加到workQueue中了，所以worker在执行的时候，会直接从workQueue中
    获取任务。所以，在workerCountOf(recheck) == 0时执行addWorker(null, false)
    也是为了保证线程池在RUNNING状态下必须要有一个线程来执行任务
    
    
    
#### addWorker方法解析


    addWorker方法的主要工作是在线程池中创建一个新的线程并执行，firstTask参数 用
    于指定新增的线程执行的第一个任务，core参数为true表示在新增线程时会判断当前活动线
    程数是否少于corePoolSize，false表示新增线程前需要判断当前活动线程数是否少于
    maximumPoolSize

启动线程也是在这里面 

    private boolean addWorker(Runnable firstTask, boolean core) {
           retry:
           for (;;) {
               int c = ctl.get();
               //获取运行状态
               int rs = runStateOf(c);
   
             /*
             * 这个if判断
             * 如果rs >= SHUTDOWN，则表示此时不再接收新任务；
             * 接着判断以下3个条件，只要有1个不满足，则返回false：
             * 1. rs == SHUTDOWN，这时表示关闭状态，不再接受新提交的任务，但却
             可以继续处理阻塞队列中已保存的任务
             * 2. firsTask为空
             * 3. 阻塞队列不为空
             *
             * 首先考虑rs == SHUTDOWN的情况
             * 这种情况下不会接受新提交的任务，所以在firstTask不为空的时候会返回
             false；
             * 然后，如果firstTask为空，并且workQueue也为空，则返回false，
             */
               if (rs >= SHUTDOWN &&
                   ! (rs == SHUTDOWN &&
                      firstTask == null &&
                      ! workQueue.isEmpty()))
                   return false;
   
   
               for (;;) {
               //获取线程数
                   int wc = workerCountOf(c);
                   // 如果wc超过CAPACITY，也就是ctl的低29位的最大值（二进制是
                   29个1），返回false；
                   // 这里的core是addWorker方法的第二个参数，如果为true表示
                   根据corePoolSize来比较，
                   // 如果为false则根据maximumPoolSize来比较。
                   //
                   if (wc >= CAPACITY ||
                       wc >= (core ? corePoolSize : maximumPoolSize))
                       return false;
                   // 尝试增加workerCount，如果成功，则跳出第一个for循环
                   if (compareAndIncrementWorkerCount(c))
                       break retry;
                   // 如果增加workerCount失败，则重新获取ctl的值
                   c = ctl.get();  // Re-read ctl
                   
                   // 如果当前的运行状态不等于rs，说明状态已被改变，返回第一个
                   for循环继续执行
                   if (runStateOf(c) != rs)
                       continue retry;
                   // else CAS failed due to workerCount change; retry inner loop
               }
           }
   
           boolean workerStarted = false;
           boolean workerAdded = false;
           Worker w = null;
           try {
           // 根据firstTask来创建Worker对象
               w = new Worker(firstTask);
               // 每一个Worker对象都会创建一个线程
               final Thread t = w.thread;
               if (t != null) {
                   final ReentrantLock mainLock = this.mainLock;
                   mainLock.lock();
                   try {
                      // rs < SHUTDOWN表示是RUNNING状态；
                      // 如果rs是RUNNING状态或者rs是SHUTDOWN状态并且
                      firstTask为null，向线程池中添加线程。
                      // 因为在SHUTDOWN时不会在添加新的任务，但还是会执行
                      workQueue中的任务
                       int rs = runStateOf(ctl.get());
                       if (rs < SHUTDOWN ||
                           (rs == SHUTDOWN && firstTask == null)) {
                           if (t.isAlive()) // precheck that t is startable
                               throw new IllegalThreadStateException();
                           // workers是一个HashSet
                           workers.add(w);
                           int s = workers.size();
                           // largestPoolSize记录着线程池中出现过的最大线程数量
                           if (s > largestPoolSize)
                               largestPoolSize = s;
                           workerAdded = true;
                       }
                   } finally {
                       mainLock.unlock();
                   }
                   if (workerAdded) {
                   //启动线程
                       t.start();
                       workerStarted = true;
                   }
               }
           } finally {
               if (! workerStarted)
                   addWorkerFailed(w);
           }
           return workerStarted;
       }
       
       
       
#### Worker类讲解

     private final class Worker
            extends AbstractQueuedSynchronizer
            implements Runnable
        {
            /**
             * This class will never be serialized, but we provide a
             * serialVersionUID to suppress a javac warning.
             */
            private static final long serialVersionUID = 6138294804551838833L;

        Worker(Runnable firstTask) {
            setState(-1); // inhibit interrupts until runWorker
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }
        public void run() {
            runWorker(this);
        }


    线程池中每一个线程都被封装成了一个worker类 ThreadPool维护的其实就是一组Worker对象 请参考JDK目录
    Worker类继承AQS并且实现了Runnable接口 注意其中的firstTask和thread属性 firstTask用来保存他传入的任务
    thread是在调用构造方法的时候通过threadFactory来创建的线程用来处理任务的线程
    
    在调用构造方法的时候需要把任务传入这里通过getThreadFactory().newThread(this); 
    来新建一个线程newthread
    方法传入的参数是this 因为worker本身继承了runnable接口 也就是一个线程，所以一个worker对象
    在启动的时候会调用worker类中的run方法
    
    Worker继承了AQS，使用AQS来实现独占锁的功能。为什么不使用ReentrantLock来
    实现呢？可以看到tryAcquire方法，它是不允许重入的，而ReentrantLock是允许重入的：
    1. lock方法一旦获取了独占锁，表示当前线程正在执行任务中；
    2. 如果正在执行任务，则不应该中断线程；
    3. 如果该线程现在不是独占锁的状态，也就是空闲的状态，说明它没有在处理任务，
    这时可以对该线程进行中断；
    4. 线程池在执行shutdown方法或tryTerminate方法时会调用interruptIdleWorkers
    方法来中断空闲的线程，interruptIdleWorkers方法会使用tryLock方法来判断线程
    池中的线程是否是空闲状态；
    5. 之所以设置为不可重入，是因为我们不希望任务在调用像setCorePoolSize这样的
    线程池控制方法时重新获取锁。如果使用ReentrantLock，它是可重入的，这样如果
    在任务中调用了如setCorePoolSize这类线程池控制的方法，会中断正在运行的线
    程。
    所以，Worker继承自AQS，用于判断线程是否空闲以及是否可以被中断
    
    此外，在构造方法中执行了setState(-1);，把state变量设置为-1，为什么这么做呢？
    是因为AQS中默认的state是0，如果刚创建了一个Worker对象，还没有执行任务时，这时
    就不应该被中断，看一下tryAquire方法：
    
    protected boolean tryAcquire(int unused) {
    //cas修改state，不可重入
     if (compareAndSetState(0, 1)) {
     setExclusiveOwnerThread(Thread.currentThread());
     return true;
    }
    return false;
    }
    tryAcquire方法是根据state是否是0来判断的，所以，setState(-1);将state设置为-1是
    为了禁止在执行任务前对线程进行中断。
    正因为如此，在runWorker方法中会先调用Worker对象的unlock方法将state设置为0


#### runWorker方法

     final void runWorker(Worker w) {
            Thread wt = Thread.currentThread();
            //获取第一个任务
            Runnable task = w.firstTask;
            w.firstTask = null;
            // 允许中断
            w.unlock(); // allow interrupts
            boolean completedAbruptly = true;
            try {
                // 如果task为空，则通过getTask来获取任务
                while (task != null || (task = getTask()) != null) {
                    w.lock();
                    if ((runStateAtLeast(ctl.get(), STOP) ||
                         (Thread.interrupted() &&
                          runStateAtLeast(ctl.get(), STOP))) &&
                        !wt.isInterrupted())
                        wt.interrupt();
                    try {
                        beforeExecute(wt, task);
                        Throwable thrown = null;
                        try {
                            task.run();
                        } catch (RuntimeException x) {
                            thrown = x; throw x;
                        } catch (Error x) {
                            thrown = x; throw x;
                        } catch (Throwable x) {
                            thrown = x; throw new Error(x);
                        } finally {
                            afterExecute(task, thrown);
                        }
                    } finally {
                        task = null;
                        w.completedTasks++;
                        w.unlock();
                    }
                }
                completedAbruptly = false;
            } finally {
                processWorkerExit(w, completedAbruptly);
            }
        }
    
      这里说明一下第一个if判断，目的是：
      如果线程池正在停止，那么要保证当前线程是中断状态；
      如果不是的话，则要保证当前线程不是中断状态；
      这里要考虑在执行该if语句期间可能也执行了shutdownNow方法，shutdownNow方法会
      把状态设置为STOP，回顾一下STOP状态：
      不能接受新任务，也不处理队列中的任务，会中断正在处理任务的线程。在线程池处于
      RUNNING 或 SHUTDOWN 状态时，调用 shutdownNow() 方法会使线程池进入到
      该状态。
      STOP状态要中断线程池中的所有线程，而这里使用Thread.interrupted()来判断是否中断
      是为了确保在RUNNING 或者SHUTDOWN 状态时线程是非中断状态的， 因为
      Thread.interrupted()方法会复位中断的状态
      
#### runworker 方法总结
    
    总结一下runWorker方法的执行过程：
    1. while循环不断地通过getTask()方法获取任务；
    2. getTask()方法从阻塞队列中取任务；
    3. 如果线程池正在停止，那么要保证当前线程是中断状态，否则要保证当前线程不是
    中断状态；
    4. 调用task.run()执行任务；
    5. 如果task为null则跳出循环，执行processWorkerExit()方法；
    6. runWorker方法执行完毕，也代表着Worker中的run方法执行完毕，销毁线程。
    这里的beforeExecute方法和afterExecute方法在ThreadPoolExecutor类中是空的，留给
    子类来实现。
    completedAbruptly 变量来表示在执行任务过程中是否出现了异常， 在
    processWorkerExit方法中会对该变量的值进行判断

#### getTask方法

getTask方法用来从阻塞队列中取任务，代码如下：



        private Runnable getTask() {
        // timeOut变量的值表示上次从阻塞队列中取任务时是否超时
        boolean timedOut = false; // Did the last poll() time out?

        for (;;) {
            int c = ctl.get();
            int rs = runStateOf(c);
            /*
            * 如果线程池状态rs >= SHUTDOWN，也就是非RUNNING状态，再进行以下判断：
            * 1. rs >= STOP，线程池是否正在stop；
            * 2. 阻塞队列是否为空。
            * 如果以上条件满足，则将workerCount减1并返回null。
            * 因为如果当前线程池状态的值是SHUTDOWN或以上时，不允许再向阻塞队列中添加
            任务。
            */
            // Check if queue empty only if necessary.
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);

            // timed变量用于判断是否需要进行超时控制。
            // allowCoreThreadTimeOut默认是false，也就是核心线程不允许进行超
            时；
            // wc > corePoolSize，表示当前线程池中的线程数量大于核心线程数量；
            // 对于超过核心线程数量的这些线程，需要进行超时控制
            
            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;


                /*
                * wc > maximumPoolSize的情况是因为可能在此方法执行阶段同时执行了
                setMaximumPoolSize方法；
                * timed && timedOut 如果为true，表示当前操作需要进行超时控制，并且上
                次从阻塞队列中获取任务发生了超时
                * 接下来判断，如果有效线程数量大于1，或者阻塞队列是空的，那么尝试将
                workerCount减1；
                * 如果减1失败，则返回重试。
                * 如果wc == 1时，也就说明当前线程是线程池中唯一的一个线程了。
                */
                
                if ((wc > maximumPoolSize || (timed && timedOut))
                && (wc > 1 || workQueue.isEmpty())) {
                if (compareAndDecrementWorkerCount(c))
                    return null;
                continue;
            }
            /*
            * 根据timed来判断，如果为true，则通过阻塞队列的poll方法进行超时控
            制，如果在keepAliveTime时间内没有获取到任务，则返回null；
            * 否则通过take方法，如果这时队列为空，则take方法会阻塞直到队列不为
            空。
            *
            */
            try {
                Runnable r = timed ?
                    workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                    workQueue.take();
                if (r != null)
                    return r;
                    // 如果 r == null，说明已经超时，timedOut设置为true
                timedOut = true;
            } catch (InterruptedException retry) {
            // 如果获取任务时当前线程发生了中断，则设置timedOut为false并返
            回循环重试
                timedOut = false;
            }
        }
    }
    
    
     这里重要的地方是第二个if判断，目的是控制线程池的有效线程数量。由上文中的分析
     可以知道，在执行execute方法时，如果当前线程池的线程数量超过了corePoolSize且小于
     maximumPoolSize，并且workQueue已满时，则可以增加工作线程，但这时如果超时没
     有获取到任务，也就是timedOut为true的情况，说明workQueue已经为空了，也就说明了
     当前线程池中不需要那么多线程来执行任务了，可以把多于corePoolSize数量的线程销毁
     掉，保持线程数量在corePoolSize即可。
     什么时候会销毁？当然是runWorker方法执行完之后，也就是Worker中的run方法执
     行完，由JVM自动回收。
     getTask 方法返回null 时， 在runWorker 方法中会跳出while 循环， 然后会执行
     processWorkerExit方法
     
     
#### processWorkerExit方法


        private void processWorkerExit(Worker w, boolean completedAbruptly) {
        // 如果completedAbruptly值为true，则说明线程执行时出现了异常，需要将
           workerCount减1；
        // 如果线程执行时没有出现异常，说明在getTask()方法中已经已经对
            workerCount进行了减1操作，这里就不必再减了。
        if (completedAbruptly) // If abrupt, then workerCount wasn'tadjusted
           decrementWorkerCount();
            final ReentrantLock mainLock = this.mainLock;
             mainLock.lock();
        try {
            //统计完成的任务数
            completedTaskCount += w.completedTasks;
            // 从workers中移除，也就表示着从线程池中移除了一个工作线程
            workers.remove(w);
            } finally {
            mainLock.unlock();
            }
        // 根据线程池状态进行判断是否结束线程池
        tryTerminate();
        int c = ctl.get();
        /*
        * 当线程池是RUNNING或SHUTDOWN状态时，如果worker是异常结束，那么会直接
        addWorker；
        * 如果allowCoreThreadTimeOut=true，并且等待队列有任务，至少保留一个
        worker；
        * 如果allowCoreThreadTimeOut=false，workerCount不少于corePoolSize。
        */
             if (runStateLessThan(c, STOP)) {
              if (!completedAbruptly) {
               int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
               if (min == 0 && ! workQueue.isEmpty())
                 min = 1;
                if (workerCountOf(c) >= min)
                  return; // replacement not needed
            }
             addWorker(null, false);
            }
        }
        至此，processWorkerExit执行完之后，工作线程被销毁，以上就是整个工作线程的生
        命周期，从execute方法开始，Worker使用ThreadFactory创建新的工作线程，
        runWorker通过getTask获取任务，然后执行任务，如果getTask返回null，进入
        processWorkerExit方法
        
       
       
![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/pool5.png)


### 手写线程池代码

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/手写线程池.png)

threadpoolexecutor-example 为手写线程池代码

### 小结

    分析了线程的创建，任务的提交，状态的转换以及线程池的关闭；
    这里通过execute 方法来展开线程池的工作流程， execute 方法通过
    corePoolSize，maximumPoolSize以及阻塞队列的大小来判断决定传入的任务应该
    被立即执行，还是应该添加到阻塞队列中，还是应该拒绝任务。
    介绍了线程池关闭时的过程，也分析了shutdown方法与getTask方法存在竞态
    条件；
    在获取任务时，要通过线程池的状态来判断应该结束工作线程还是阻塞线程等待
    新的任务，也解释了为什么关闭线程池时要中断工作线程以及为什么每一个worker
    都需要lock
    
    
