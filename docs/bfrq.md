
#### map简介

hashMap HashTable LinkedHashMap TreeMap

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/map1.png)

hashtable 任何时刻只有一个线程能对它进行操作

linkedHashmap 插入顺序

TreeMap 可以自定义排序

多个线程在map put 的时候 会在rehash的时候造成链表死循环 没必要了解太深 当作兴趣还行

hashmap 1.8 当链表超过一定的值时就会变成红黑树存储

#### vector 和 hashTable

并发性能不好

#### Java 7 ConcurrentHashMap 结构

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/map2.png)

java7 中的ConcurrentHashMap 最外层时多个segment 每个segment的底层数据结构与HashMap类似 仍然是数组和链表组成的拉链法

每个segment 独立上ReenTrantLock锁 每个segment之间互不影响 提高了并发效率

ConcurrentHashMap 默认有16个segments 所以最多可以支持16个线程的并发写（操作分别分布在不同的segment上） 这个默认值可以在初始化的时候设置为其他值但是一旦
被初始化后是不可以扩容的

#### Java 8  ConcurrentHashMap 结构

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/map3.png)

java8中完成舍弃了7的写法 而是用node去存储并且 保证线程安全的方式为: cas+synchronized

#### 为什么要进行升级

1.数据结构 节点每个node都独立 

2.hash 碰撞  7 拉链法 8 先使用拉链法 之后转成红黑树

3.保证并发安全 

3.查询复杂度 遍历链表 和 遍历 链表或者红黑树 O(n) 变为 Olog(n)

#### 为什么超过8要用红黑树

泊松分布  hash冲突小于8次都已经小于千万分之一了  正常情况下时不会碰到的

#### ConcurrentHashMap 线程安全吗?

保证的是多个线程同时put 是不会发生并发问题的  OptionsNotSafe 组合操作 putIfAbsent

#### 生产环境并发安全问题

#### CopyOnWriteArrayList

1.用来代替Vector和SynchronizedList 

2.读操作尽可能快 而写操作即使慢一些也没有太大的关系

3.读多写少:黑名单 每日更新 监听器 

4.读写锁规则的升级： 读取时完全不用加锁的 并且更厉害的是 写入也不会阻塞读取的操作 只有写入和写入之间需要进行同步等待

#### CopyOnWriteArrayList分析

1.数据一致性问题： 不能保证数据实时性

2.内存占用： CopyOnwrite 的写是复制机制

数据结构是一个数组 再写入的时候用的 reentrantLock 

CopyOnWriteArrayListDemo2

#### 并发队列 BlockingQueue

ArrayBlockingQueue 有界 指定是否公平

LinkedBlockingQueue

PriorityBlockingQueue

SynchronousQueue

take() 可以进行阻塞 

put()  可以进行阻塞

add remove element 抛出异常

offer poll peek 返回boolean值





