###  多线程的生命周期状态纽机流转

https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/Thread.State.html 官方地址

线程大概分为六种状态: NEW RUNNABLE BLOCKED WAITING TIMED_WAITING TERMINATED 
 
 ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadbase003.png)
 
 此为准确流转图 
 
 1.   如果发生异常，可以直接跳到终止TERMINATED状态，不必再遵循路径，比如可以从WAITING直接到TERMINATED。
 
 2.   从Object.wait()刚被唤醒时，通常不能立刻抢到monitor锁，那就会从WAITING先进入BLOCKED状态，抢到锁后再转换到RUNNABLE状态。
 
 