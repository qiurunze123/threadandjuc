###  ThreadAndObject

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadandobject.png)

### wait 和 notify 基本讲解

    作用+用法 ： 阻塞 唤醒 遇到中断
    
    特点性质  1.用的必须先拥有monitor锁
             2.notify 只能唤醒其中一个  notifyall 全部唤醒
             3.属于object object 是所有对象的父类 意味着任何对象都可以调用它
             4.wait 和notify相对于来说比较底层 类似功能condition 

### wait和notify原理

 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadandobject1.png)


两个概念 入口集 Entry Set 等待集 Wait Set 
    
这个大标题是java monitor 表示的就是我们抢synchronize锁的一系列动作 从1开始往下开始抢锁进入入口集合 可能有多个线程
比如我们的锁现在已经被其他人所获取了  我们在想获取就会被统统的放在这个入口集合进行等待 右侧的紫色部分代表的就是他已经获得锁 
知道右侧拿到锁的人释放 我们才能再让入口集中的某一个再去获得锁 虽有拿到的线程锁 释放的途径有俩种 一个是 6 一个是 3 6就是说他执行完了于是
正常释放 并且退出 一旦拿到锁执行的过程中被wait()了 那么就会进入到左侧的等待集 先进入上边的蓝色的部分 首先这个release代表它释放了锁
因为wait()这个方法一旦执行了就会释放掉我们的锁 他就是跑到左边的等待集中 等待notify 或者 notifyall 它就会进入到下边的集合中 下面的集合
和上面的集合是不一样的 区别在于在等待集中他还是在不停的等待还没有想获取锁的意愿 还没有被唤醒 一旦到了下面的这个粉红色的区域里面实际上
他和我们的上面绿色是一样 都是在等待现在我们持有锁的线程去释放然后在来抢锁 所以 5 的acquire 和2的acquire 是一样的 都值得是获取
锁之后再重新回到我们右侧的紫色部分中 回到持有锁的模块中 


### 状态转换的特殊情况

1.从Object.wait()状态刚被唤醒时通常不能立刻抢到monitor锁那么就会从waiting先进入blocked状态 抢到锁后再转换到runnable状态

2.如果发生异常 可以直接跳到种植Terminated状态 不必再遵循路径比如可以从外应直接到terminated