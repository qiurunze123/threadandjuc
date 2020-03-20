##### ThreadLocal 典型的使用场景

#### 典型使用场景： 每个线程都有一个独享的对象 
 
1.通常是工具类 典型需要使用的类simpleDateFormat和Random每个thread内有自己的实例副本不共享

2.教材只有一本 每个人都抢着看 那么这终究会有线程安全问题  

3.模拟场景 1000个线程都需要去打印日期  simpleDateFormat 格式化 但是线程不安全 而且如果用线程池 就造成资源浪费

如何解决：

      1.可以加锁 但是会销毁比较低
      2.threadlocal 可以解决  可以实现initialValue 去new 一下simpledateFormat 每一个线程内都有自己独享的对象

#### 每个线程内 需要保存全局变量（例如在拦截器中获取用户信息 可以让每个不同的方法直接使用 避免参数传递的麻烦）

比如当前用户信息需要被线程内所有的方法共享  
一个比较繁琐的解决方案就是 把user作为参数层层传递 从service-1（） 传到service-2（） 再从 service-2（ ）传到service-3（）
但是这样会造成代码冗余并且不易维护

可以用map一类的存储参数信息但是需要考虑 并发安全 所有会对性能有影响

    threadlocal的作用和方法概念
    1.保存一些业务内容 用户权限信息 用户系统获取用户名 userid等
    2.这些信息在同一个线程里面 但是不同的线程使用的业务内容是不相同的
    3.在线程的生命周期内 都通过这个静态的threadlocal实例的get() 方法来 自己set过得那个对象 避免将这个对象（例如user对象）
    作为参数传递的麻烦用threadLocal 保存一些业务内容 
    4.强调的是同一个请求内不同方法间的共享
    5.不需要重写initialvalue方法 但是必须手动调用set方法

    总结： 1.让某个需要用到的对象在线程建隔离 （每个对象都有自己独立的对象）
          2.在任何方法都可以轻松获取对象
      
####ThreadLocal的好处

1.线程安全 2.不需要加锁 提高执行效率 3。高效利用那个内存 和开销 4 避免传参的繁琐

####ThreadLocal源码分析 
 
 thread threadlocal  threadlocalmap 三者之间的关系  每个thread对象中都持有一个threadlocalmap成员变量 
 
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadlocal1.png)


在一个thread的里面有一个 成员变量     ThreadLocal.ThreadLocalMap threadLocals = null;

重要方法解析：

**initialValue()**  
1.该方法会返回当前线程对应的初始值 这是一个延迟加载的方法 只有在调用get 的时候才会触发 在没重写之前默认返回的是null

2.当线程第一个使用get方法的访问变量时候 将调用此方法 除非线程先前调用了set方法 在这种情况下 不会为线程调用本initialValue方法

3.通常 每个线程最多调用一次此方法  之后便不会再触发 但如果已经调用了remove()方法后 再调用get方法 则可以再次调用此方法

4.如果不重写本方法 这个方法会返回null 一般使用匿名内部类的方法来重写initialValue这个方法以便在后续中使用可以初始化的副本对象

**set()**

为这个线程设置一个新值

**get()方法**
得到这个线程对应的value 如果是首次调用get() 则会调用initiallize 得到这个值 

get方法是先取出当前线程的threadLocalMap 然后调用map.getEntry方法 把本ThreadLocal 的引用作为参数传入 取出map中属于本ThreadLocal的value

**remove() 删除这个线程的值**

     public T get() {
            Thread t = Thread.currentThread();
            ThreadLocalMap map = getMap(t);
            if (map != null) {
                ThreadLocalMap.Entry e = map.getEntry(this);//把当前的threadlocal 当做变量来当做key 进行取值操作
                if (e != null) {
                    @SuppressWarnings("unchecked")
                    T result = (T)e.value;
                    return result;
                }
            }
            return setInitialValue();
        }

    
     public void set(T value) {
            Thread t = Thread.currentThread();
            ThreadLocalMap map = getMap(t);
            if (map != null)
                map.set(this, value);
            else
                createMap(t, value);
        }
         createmap 的操作 --------   t.threadLocals = new ThreadLocalMap(this, firstValue);

    
    public void remove() {
             ThreadLocalMap m = getMap(Thread.currentThread());//只能删掉自己的 this 传进去 就知道应该删除哪个了
             if (m != null)
                 m.remove(this);
         }


    ThreadLocalMap类 也就是Thread.threadLocals ThreadLocalMap 类是每个Thread类里面的变量
    里面最重要的一个键值对数组 Entry[] table 
    可以认为是一个map 键值对 
    键：当前的这个ThreadLocal -- this 
    值：实际需要的成员变量 比如user 或者 simpleDateFormat对象 

ThreadLocalMap 这里采用的线性探测法 也就是如果发生冲突就继续找下一个空位置 而不是用链表拉链法

可以说： 通过源码分析看出 setInitialValue 和直接set最后都是利用map.set()方法来设置值
也就是说 最后都会对应到TheadLocalMap的一个Entry 只不过是起点和入口不一样

#### ThreadLocal 内存泄露

1.内存泄露 某个对象不再有用 但是占用的内存却不能被回收  

2.只有两种可能性 key 泄露 value 泄露

      static class Entry extends WeakReference<ThreadLocal<?>> {
                /** The value associated with this ThreadLocal. */
                Object value;
    
                Entry(ThreadLocal<?> k, Object v) {
                    super(k);
                    value = v;
                }
            }

    弱引用  ThreadLocalMap 的每个Entry 都是一个key的弱引用 同时每个Entry都包含了一个对value的强引用  
    正常情况下 当线程终止 保存在Threadlocal 里面的value 会被垃圾回收 因为没有任何的强引用了
    但是如果线程不终止 比如线程需要保持很久 那么key对应的value 就不能被回收 比如线程池就是用一个线程反复被使用  因此就有了一下的调用链：
    Thread -- ThreadLocalMap -- entry (Key为null) -- value 
    因为 value 和Thread之间 还存在这个强引用链路 所以导致value无法回收 就可能会出现OOM
    JDK已经考虑到这个问题了 所以在set remove rehash 方法中 会扫描 key为null 的 entry 
    并吧对应的value设置为null  这样value对象 就可以被回收 

    但是如果一个Threadlocal 不被使用 那么实际上set remove rehash 方法也不会被调用 
    如果同时线程又不停止 那么调用链就一直存在那么就会导致value的泄露 

#### 如何避免内存泄露？？

调用remove方法 就会删除对应的entry对象 可以避免内存泄露 所以使用完ThreadLocal之后 应该调用remove()方法

在进行get 之前 必须先set 否则可能会报空指针异常吗 不会 返回null 但是进行装箱和拆箱 会出现错误

共享变量：如果在每个线程中ThreadLocal.set() 进去的东西本来就是多线程共享的同一个对象 比如static对象 那么多个线程的ThreadLocal.get() 
取得的还是共享变量本身 还是有并发访问的问题

#### spring 中的实例分析

DateTimeContextHolder 类 看到了里面用了ThreadLocal
每次http 请求 都对应一个线程 线程之间相互隔离 这就是ThreadLocal 典型应用场景


