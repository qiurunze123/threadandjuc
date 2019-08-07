### 导航
| ID | Problem  | Article | 
| --- | ---   | :--- |
| 000 |队列和阻塞队列 | [解决思路](/docs/blocking.md) |
| 001 |怎么使用阻塞队列好处是什么 | [解决思路](/docs/blocking.md) |
| 002 |BlockingQueue核心方法 | [解决思路](/docs/blocking.md) |
| 003 |设计梳理种类分析 | [解决思路](/docs/blocking.md) |
| 004 |使用方式与使用场景 | [解决思路](/docs/blocking.md) |
### 队列与阻塞队列

阻塞队列可以阻塞，非阻塞队列不能阻塞，只能使用队列wait(),notify()进行队列消息传送。
而阻塞队列当队列里面没有值时，会阻塞直到有值输入。输入也一样，当队列满的时候，会阻塞，直到队列不为空

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/blocking.png)
   
当阻塞队列是空时,从队列中获取元素的操作将会被阻塞.

当阻塞队列是满时,往队列中添加元素的操作将会被阻塞.

同样试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞,直到其他线程从队列中移除一个或者多个元素或者全清空队列后使队列重新变得空闲起来并后续新增.

### 使用阻塞队列的好处与作用

使用非阻塞队列的时候有一个很大问题就是：它不会对当前线程产生阻塞，那么在面对类似消费者-生产者的模型时，
就必须额外地实现同步策略以及线程间唤醒策略，这个实现起来就非常麻烦。但是有了阻塞队列就不一样了，
它会对当前线程产生阻塞，比如一个线程从一个空的阻塞队列中取元素，此时线程会被阻塞直到阻塞队列中有了元素。
当队列中有元素后，被阻塞的线程会自动被唤醒（不需要我们编写代码去唤醒）。这样提供了极大的方便性。

在concurrent包 发布以前,在多线程环境下,我们每个程序员都必须自己去控制这些细节,尤其还要兼顾效率和线程安全,而这会给我们的程序带来不小的复杂度.

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/blocking2.png)

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/blocking3.png)
   
   对于非阻塞队列，一般情况下建议使用offer、poll和peek三个方法，不建议使用add和remove方法。因为使用offer、poll和peek三个方法可以通过返回值判断操作成功与否，
   而使用add和remove方法却不能达到这样的效果。注意，非阻塞队列中的方法都没有进行同步措施。

### 队列种类分析 

`ArrayBlockingQueue ：一个由数组支持的有界队列
`

`LinkedBlockingQueue ：一个由链接节点支持的可选有界队列 最大值 大约21亿
`

`PriorityBlockingQueue ：一个由优先级堆支持的无界优先级队列
`

`DelayQueue ：一个由优先级堆支持的、基于时间的调度队列
`

`SynchronousQueue :不存储元素的阻塞队列及生产一个没有数据缓冲的BlockingQueue，生产者线程对其的插入操作put必须等待消费者的移除操作take，反过来也一样 不然就阻塞着`

### 生产者和消费者模式 

传统版:com.geek.blockingdemo.ProdComsumerTraditionDemo

阻塞队列版: com.geek.blockingdemo.ProdComsumerBlockingQueueDemo

### 使用场景 利用多线程和队列进行解耦

com.geekq.highimporttry.timer.BlockingRunTask