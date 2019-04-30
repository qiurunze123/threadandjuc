
**Nio与Aio详解**

Io与多线程的关系: Nio改变了线程在应用层面的使用方式

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew58.png)


**buffer 的三个重要参数**

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew59.png)
 
 **参数解释**
 
/BufferSize/ 详细解读

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew60.png)
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew61.png)
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew62.png)


Buffer中大多数的方法都是去改变这3个参数来达到某些功能的：

public final Buffer rewind()

将position置零，并清除标志位（mark）

public final Buffer clear()

将position置零，同时将limit设置为capacity的大小，并清除了标志mark

public final Buffer flip()

先将limit设置到position所在位置，然后将position置零，并清除标志位mark，通常在读写转换时使用

文件映射到内存 com.geek.aionio.nioCopyFile/mapBuffer

对MappedByteBuffer的修改就相当于修改文件本身，这样操作的速度是很快的。

**多线程网络一般结构**

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew64.png)
 
 
 /socket
 
 为每一个客户端使用一个线程，如果客户端出现延时等异常，线程可能会被占用很长时间。因为数据的准备和读取都在这个线程中。此时，如果客户端数量众多，可能会消耗大量的系统资源
 
 HeavySocketClient 模拟抵消的客户端接受请求 低效的客户端来模拟因网络而延时的情况
 
 因为
 
 while ((inputLine = is.readLine()) != null)
 
 是阻塞的，所以时间都花在等待中
 
 如果用NIO来处理这个问题会怎么做呢？
 
 NIO有一个很大的特点就是：把数据准备好了再通知我
 
 而Channel有点类似于流，一个Channel可以和文件或者网络Socket对应 。
 
 selector是一个选择器，它可以选择某一个Channel，然后做些事情。
 
 一个线程可以对应一个selector，而一个selector可以轮询多个Channel，而每个Channel对应了一个Socket。
 
 与上面一个线程对应一个Socket相比，使用NIO后，一个线程可以轮询多个Socket。
 
 当selector调用select()时，会查看是否有客户端准备好了数据。当没有数据被准备好时，select()会阻塞。平时都说NIO是非阻塞的，但是如果没有数据被准备好还是会有阻塞现象。
 
 当有数据被准备好时，调用完select()后，会返回一个SelectionKey，SelectionKey表示在某个selector上的某个Channel的数据已经被准备好了。
 
 只有在数据准备好时，这个Channel才会被选择。
 
 这样NIO实现了一个线程来监控多个客户端。
 
 而刚刚模拟的网络延迟的客户端将不会影响NIO下的线程，因为某个Socket网络延迟时，数据还未被准备好，selector是不会选择它的，而会选择其他准备好的客户端。
 
 selectNow()与select()的区别在于，selectNow()是不阻塞的，当没有客户端准备好数据时，selectNow()不会阻塞，将返回0，有客户端准备好数据时，selectNow()返回准备好的客户端的个数
 ---------
 
 /aionio/nio
 
 当用之前模拟的那个延迟的客户端时，这次的时间消耗就在2ms到11ms之间了。性能提升是很明显的。
 
 总结：
 
 1. NIO会将数据准备好后，再交由应用进行处理，数据的读取/写入过程依然在应用线程中完成，只是将等待的时间剥离到单独的线程中去。
 
 2. 节省数据准备时间（因为Selector可以复用）
 
**AIO的特点**

1. 读完了再通知我

2. 不会加快IO，只是在读完后进行通知

3. 使用回调函数，进行业务处理

这里使用了Future来实现即时返回

在理解了NIO的基础上，看AIO，区别在于AIO是等读写过程完成后再去调用回调函数。

NIO是同步非阻塞的

AIO是异步非阻塞的

由于NIO的读写过程依然在应用线程里完成，所以对于那些读写过程时间长的，NIO就不太适合。

而AIO的读写过程完成后才被通知，所以AIO能够胜任那些重量级，读写过程长的任务
