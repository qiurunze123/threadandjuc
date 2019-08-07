### 导航

| ID | Problem  | 
| --- | ---   |
| 000 |CPU缓存框架|
| 001 |什么是CPU缓存行 |
| 002 |什么是内存屏障 | 
| 004 |什么是伪共享|
| 005 |如何避免伪共享 | 


#### 百度分布式id策略

     https://github.com/baidu/uid-generator/blob/master/README.zh_cn.md

#### CPU缓存框架 

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/falsesharing.png)

#### 什么是CPU缓存行

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/falsesharing1.png)

#### 什么是伪共享

   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/falsesharing2.png)

    给你免费加载其余的七个,免费加载也有一个坏处设想如果我们有个 long 类型的变量 a，它不是数组的一部分，而是一个单独的变量，并且还有另外一个 long 类型的变量 b 紧挨着它，那么当加载 a 的时候将免费加载 b。
    看起来似乎没有什么毛病，但是如果一个 CPU 核心的线程在对 a 进行修改，另一个 CPU 核心的线程却在对 b 进行读取。
    当前者修改 a 时，会把 a 和 b 同时加载到前者核心的缓存行中，更新完 a 后其它所有包含 a 的缓存行都将失效，因为其它缓存中的 a 不是最新值了而当后者读取 b 时，发现这个缓存行已经失效了
    需要从主内存中重新加载。请记住，我们的缓存都是以缓存行作为一个单位来处理的，所以失效 a 的缓存的同时，也会把 b 失效，反之亦然 b 和 a 完全不相干，
    每次却要因为 a 的更新需要从主内存重新读取，它被缓存未命中给拖慢了
    
#### 代码示例解决 FalseSharingtest 