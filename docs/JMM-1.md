
#### JMM 内存模型

#### 什么是JMM底层原理

1.从java代码到CPU指令

 ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm1.png)
     
     1.最开始我们编译的是java代码是*.java文件
     2.在编译（javac命令）后从刚才的*.java文件变成一个新的java字节码文件（*.class）
     3.JVM执行刚才生成的字节码（*.class）把字节码文件转化为机器指令之后就与平台无关了
     （jvm会根据不同的操作系统和平台的不同把字节码翻译成不同的机器指令）
     4.机器指令可以直接在CPU上运行 也就是最终指令
     
2.java内存模型应运而出?

     JVM实现不用的翻译 不同的CPU平台的机器指令有千差万别无法保证并发安全效果一致
     在最开始 是会发生这一系列问题的 存在不同的平台运行起来效果不一样 即使你的指令一样但是也会出现不一致的效果
     这个时候就出现了java内存模型来进行统一规范
     
#### java内存模型+jvm内存结构+java对象模型


1.java对象模型

   ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm1.png)
    
    
    首先这个图分为三个区域 左上角是栈 左下角是方法去 右边的这个是堆 首先我们会在左下角的方法区把这个类的信息创建出来instanceklass
    这个的k打头而不是c目的就是区分我们c打头的class有个这个信息之后 这个对象的每个实例都会放在这边的堆中 markword 和元数据指针 
    被称为对象头 实例数据等 在堆中的每个对象也就分为对象头和除去对象头的实例部分 加入这个对象被我们的某个方法所调用了
    这个时候就会去栈中把这个对象保存下来 所以已上的栈 方法区 堆 被称为java对象模型

总结一下就是： java对象模型就是对象自身的存储模型

   ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm3.png)
