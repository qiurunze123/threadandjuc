
**什么是CAS算法**

原理：

CAS算法过程： 

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew10.png)

CPU指令：

     /*
        accumulator = AL, AX, or EAX, depending on whether
        a byte, word, or doubleword comparison is being performed
        */
        if(accumulator == Destination) {
        ZF = 1;
        Destination = Source;
        }
        else {
        ZF = 0;
        accumulator = Destination;
        }

目标值和寄存器里的值相等的话，就设置一个跳转标志，并且把原始数据设到目标里面去。如果不等的话，就不设置跳转标志了

无锁类使用：(无锁类要比阻塞效率搞得多毋庸置疑)

AtomicInteger 所有方法

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew11.png)
 
 AtomicInteger 继承了 Number类 随便找一个方法解析下 /atomic/AtomicInteger1 
 
 incrementAndGet 底层实现 
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew13.png)
 
 unsafe.compareAndSwapInt方法，他的意思是，对于this这个类上的偏移量为valueOffset的变量值如果与期望值expect相同，那么把这个变量的值设为update。
 其实偏移量为valueOffset的变量就是value
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew14.png)
 
 
 CAS是有可能会失败的，但是失败的代价是很小的，所以一般的实现都是在一个无限循环体内，直到成功为止。
 
     public final int getAndIncrement() {
             for (;;) {
                 int current = get(); // 获取最新的值
                 int next = current + 1;
                 if (compareAndSet(current, next))
                     return current;
             }
         }


**主要接口实现**

Unsafe 的操作事线程非安全的操作 作用：

1.根据偏移量设置值（AtomicInteger中已经看到了这个功能）

2.park()（把这个线程停下来）

3.底层的CAS操作

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew15.png)
 
 
**ABA 问题**

从AtomicInteger的incrementAndGet我们也可以发现一个问题 

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew17.png)
 
 此时的我们需要 AtomicStampedReference 

**AtomicStampedReference**

其内部实现一个Pair类来封装值和时间戳

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew18.png)


 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew19.png)


 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew20.png)


使用AtomicStampedReference来实现时，只会给用户充值一次，因为每次操作使得时间戳+1

**AtomicIntegerArray**

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadnew21.png)
 
 它的内部只是封装了一个普通的array
 private final int[] array;
 
 里面有意思的是运用了二进制数的前导零来算数组中的偏移量。
 
 shift = 31 - Integer.numberOfLeadingZeros(scale);
 
 前导零的意思就是比如8位表示12,00001100，那么前导零就是1前面的0的个数，就是4 /atomic/AtomicArray1
 
**AtomicIntegerFieldUpdater** 

1.Updater只能修改它可见范围内的变量。因为Updater使用反射得到这个变量。如果变量不可见，就会出错。
比如如果score申明为private，就是不可行的。


2.为了确保变量被正确的读取，它必须是volatile类型的。如果我们原有代码中未申明这个类型，那么简单得
申明一下就行，这不会引起什么问题。

3.由于CAS操作会通过对象实例中的偏移量直接进行赋值，因此，它不支持static字段（Unsafe.
objectFieldOffset()不支持静态变量） /atomic/AtomicIntegerFieldUpdater1



