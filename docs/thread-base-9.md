
**线程池精讲**


线程池各类的关系：
Executors -- new ThreadPoolExecutor

ThreadPoolExecutor extends AbstractExecutorService 
AbstractExecutorService(抽象类) implements ExecutorService
interface ExecutorService extends Executor 



一般线程池的使用，往往都是使用这个接口  

ExecutorService executor = Executors.newFixOrCachedThreadPool(args...);

实现类似于

图片threadpool2

ThreadPoolExecutor 主要是通过不同参数来初始化一个 ThreadPoolExecutor 对象


图片threadpool1


各个参数的含义为：

corePoolSize 核心线程数 如果当前线程池中的线程少于核心线程数那么这些线程不会因为被空闲太久而关闭 除非调用allowCoreThreadTimeOut(true)

maximumPoolSize 最大线程数

workQueue 任务缓存队列 阻塞队列BlockingQueue的某个实 存放来不及处理的任务的队列，是一个BlockingQueue
ArrayBlockingQueue和PriorityBlockingQueue使用较少，
一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。




1）LinkedBlockingQueue：基于链表的无界（默认构造函数为：最大值Integer.MAX_VALUE容量）阻塞队列，
按FIFO(先进先出)的规则存取任务

2）	ArrayBlockingQueue：基于数组的有界阻塞队列，按FIFO的规则对任务进行存取，必须传入参数来定义队列大小

3）DelayedWorkQueue：基于堆的延迟队列，Executors.newScheduledThreadPool(...)中使用了该队列

4）PriorityBlockingQueue：具有优先级的阻塞队列

5）SynchronousQueue：不存储任务的阻塞队列，每一个存入对应一个取出，串行化队列

unit.toNanos(keepAliveTime)  空闲线程的保活时间，如果某线程的空闲时间超过这个值都没有任务给它做，那么可以被关闭了
threadFactory 用于生成线程，一般我们可以用默认的就可以了

handler  拒绝策略 当线程池满了的时候 有新的任务提交 该采取什么策略制定


32 的整数来存放线程池的状态和当前池中的线程数，其中高三位用于存放线程池状态，低29位代表线程的数量


解析：


private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
// 这里 COUNT_BITS 设置为 29(32-3)，意味着前三位用于存放线程状态，后29位用于存放线程数
// 很多初学者很喜欢在自己的代码中写很多 29 这种数字，或者某个特殊的字符串，然后分布在各个地方，这是非常糟糕的
private static final int COUNT_BITS = Integer.SIZE - 3;
// 000 11111111111111111111111111111
// 这里得到的是 29 个 1，也就是说线程池的最大线程数是 2^29-1=536860911
// 以我们现在计算机的实际情况，这个数量还是够用的
private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
// 我们说了，线程池的状态存放在高 3 位中
// 运算结果为 111跟29个0：111 00000000000000000000000000000
private static final int RUNNING    = -1 << COUNT_BITS;
// 000 00000000000000000000000000000
private static final int SHUTDOWN   =  0 << COUNT_BITS;
// 001 00000000000000000000000000000
private static final int STOP       =  1 << COUNT_BITS;
// 010 00000000000000000000000000000
private static final int TIDYING    =  2 << COUNT_BITS;
// 011 00000000000000000000000000000
private static final int TERMINATED =  3 << COUNT_BITS;
// 将整数 c 的低 29 位修改为 0，就得到了线程池的状态
private static int runStateOf(int c)     { return c & ~CAPACITY; }
// 将整数 c 的高 3 为修改为 0，就得到了线程池中的线程数
private static int workerCountOf(int c)  { return c & CAPACITY; }
private static int ctlOf(int rs, int wc) { return rs | wc; }
/*
 * Bit field accessors that don't require unpacking ctl.
 * These depend on the bit layout and on workerCount being never negative.
 */
private static boolean runStateLessThan(int c, int s) {
    return c < s;
}
private static boolean runStateAtLeast(int c, int s) {
    return c >= s;
}
private static boolean isRunning(int c) {
    return c < SHUTDOWN;
}

就是对一些整数进行简单的位操作

线程池的一些状态：


 图片threadpool4
 
 RUNNING ： 接受任务，处理等待队列中的任务
 
 SHUTDOWN ： 不能接受新的任务提交，但是还是会继续处理等待队列中的任务
 
 STOP : 不会接受新的任务 不处理缓存队列中的 任务，中断正在执行的线程
 
 TIDYING : 所有任务都被销毁，workCount为0 ，再转换为TIDYING状态时，会执行terminated()
 
 TERMINATED : terminated() 结束后被变成这个
 
 
 
 RUNNING -> SHUTDOWN：当调用了 shutdown() 后，会发生这个状态转换，这也是最重要的
(RUNNING or SHUTDOWN) -> STOP：当调用 shutdownNow() 后，会发生这个状态转换，这下要清楚 shutDown() 和 shutDownNow() 的区别了
SHUTDOWN -> TIDYING：当任务队列和线程池都清空后，会由 SHUTDOWN 转换为 TIDYING
STOP -> TIDYING：当任务队列清空后，发生这个转换
TIDYING -> TERMINATED：这个前面说了，当 terminated() 方法结束后

在多线程执行任务的时候 任务的task是runnable ， 执行的核心方法为：


图片threadpool5



    public void execute(Runnable command) {
    if (command == null)
    throw new NullPointerException();
    
    //表示线程池状态和线程数的整数
    int c = ctl.get();
    //如果当前线程少于核心线程数则直接添加一个worker来执行任务
    if (workerCountOf(c) < corePoolSize) {
    // 创建一个新的线程，并把当前任务 command 作为这个线程的第一个任务(firstTask） 返回true 成功 
    // 返回false则不允许提交任务
    if (addWorker(command, true))
        return;
    c = ctl.get();
    }
    //如果线程池大于核心线程数或者提交失败 
    //如果线程池处于RUNNNING状态 添加到workQueue里面
        //判断线程池运行状态，工作队列是否有空间
    if (isRunning(c) && workQueue.offer(command)) {
    //
    int recheck = ctl.get();
            // 如果线程池已不处于 RUNNING 状态，那么移除已经入队的这个任务，并且执行拒绝策略
    if (! isRunning(recheck) && remove(command))
        reject(command);
                    //线程池在运行，有效线程数为0 
    else if (workerCountOf(recheck) == 0)
     //添加一个空线程进线程池，使用非core容量线程
     //仅有一种情况，会走这步，core线程数为0，max线程数>0,队列容量>0
     //创建一个非core容量的线程，线程池会将队列的command执行
        addWorker(null, false);
    }
    //线程池停止了或者队列已满，添加maximumPoolSize容量工作线程，如果失败，执行拒绝策略
    else if (!addWorker(command, false))
    reject(command);
    }






// 第一个参数是准备提交给这个线程执行的任务，之前说了，可以为 null
// 第二个参数为 true 代表使用核心线程数 corePoolSize 作为创建线程的界线，也就说创建这个线程的时候，
// 		如果线程池中的线程总数已经达到 corePoolSize，那么不能响应这次创建线程的请求
// 		如果是 false，代表使用最大线程数 maximumPoolSize 作为界线
private boolean addWorker(Runnable firstTask, boolean core) {
retry:
for (;;) {
int c = ctl.get();
int rs = runStateOf(c);
// 这个非常不好理解
// 如果线程池已关闭，并满足以下条件之一，那么不创建新的 worker：
// 1. 线程池状态大于 SHUTDOWN，其实也就是 STOP, TIDYING, 或 TERMINATED
// 2. firstTask != null
// 3. workQueue.isEmpty()
// 简单分析下：
// 还是状态控制的问题，当线程池处于 SHUTDOWN 的时候，不允许提交任务，但是已有的任务继续执行
// 当状态大于 SHUTDOWN 时，不允许提交任务，且中断正在执行的任务
// 多说一句：如果线程池处于 SHUTDOWN，但是 firstTask 为 null，且 workQueue 非空，那么是允许创建 worker 的
if (rs >= SHUTDOWN &&
    ! (rs == SHUTDOWN &&
       firstTask == null &&
       ! workQueue.isEmpty()))
    return false;
for (;;) {
    int wc = workerCountOf(c);
    if (wc >= CAPACITY ||
        wc >= (core ? corePoolSize : maximumPoolSize))
        return false;
    // 如果成功，那么就是所有创建线程前的条件校验都满足了，准备创建线程执行任务了
    // 这里失败的话，说明有其他线程也在尝试往线程池中创建线程
    if (compareAndIncrementWorkerCount(c))
        break retry;
    // 由于有并发，重新再读取一下 ctl
    c = ctl.get();
    // 正常如果是 CAS 失败的话，进到下一个里层的for循环就可以了
    // 可是如果是因为其他线程的操作，导致线程池的状态发生了变更，如有其他线程关闭了这个线程池
    // 那么需要回到外层的for循环
    if (runStateOf(c) != rs)
        continue retry;
    // else CAS failed due to workerCount change; retry inner loop
}
}
/* 
* 到这里，我们认为在当前这个时刻，可以开始创建线程来执行任务了，
* 因为该校验的都校验了，至于以后会发生什么，那是以后的事，至少当前是满足条件的
*/

// worker 是否已经启动
boolean workerStarted = false;
// 是否已将这个 worker 添加到 workers 这个 HashSet 中
boolean workerAdded = false;
Worker w = null;
try {
final ReentrantLock mainLock = this.mainLock;
// 把 firstTask 传给 worker 的构造方法
w = new Worker(firstTask);
// 取 worker 中的线程对象，之前说了，Worker的构造方法会调用 ThreadFactory 来创建一个新的线程
final Thread t = w.thread;
if (t != null) {
    // 这个是整个类的全局锁，持有这个锁才能让下面的操作“顺理成章”，
    // 因为关闭一个线程池需要这个锁，至少我持有锁的期间，线程池不会被关闭
    mainLock.lock();
    try {
        int c = ctl.get();
        int rs = runStateOf(c);
        // 小于 SHUTTDOWN 那就是 RUNNING，这个自不必说，是最正常的情况
        // 如果等于 SHUTDOWN，前面说了，不接受新的任务，但是会继续执行等待队列中的任务
        if (rs < SHUTDOWN ||
            (rs == SHUTDOWN && firstTask == null)) {
            // worker 里面的 thread 可不能是已经启动的
            if (t.isAlive())
                throw new IllegalThreadStateException();
            // 加到 workers 这个 HashSet 中
            workers.add(w);
            int s = workers.size();
            // largestPoolSize 用于记录 workers 中的个数的最大值
            // 因为 workers 是不断增加减少的，通过这个值可以知道线程池的大小曾经达到的最大值
            if (s > largestPoolSize)
                largestPoolSize = s;
            workerAdded = true;
        }
    } finally {
        mainLock.unlock();
    }
    // 添加成功的话，启动这个线程
    if (workerAdded) {
        // 启动线程
        t.start();
        workerStarted = true;
    }
}
} finally {
// 如果线程没有启动，需要做一些清理工作，如前面 workCount 加了 1，将其减掉
if (! workerStarted)
    addWorkerFailed(w);
}
// 返回线程是否启动成功
return workerStarted;
}



线程池的饱和策略，当阻塞队列满了，且没有空闲的工作线程，如果继续提交任务，必须采取一种策略处理该任务，线程池提供了4种策略：
1、AbortPolicy：直接抛出异常，默认策略；
2、CallerRunsPolicy：用调用者所在的线程来执行任务；
3、DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
4、DiscardPolicy：直接丢弃任务；
