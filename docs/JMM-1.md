
#### JMM 内存模型
#### java内存模型+jvm内存结构+java对象模型


1.java对象模型

   ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm2.png)
    
    
    首先这个图分为三个区域 左上角是栈 左下角是方法去 右边的这个是堆 首先我们会在左下角的方法区
    把这个类的信息创建出来instanceklass
    这个的k打头而不是c目的就是区分我们c打头的class有个这个信息之后 这个对象的每个实例都会放在这边的堆中
     markword 和元数据指针 
    被称为对象头 实例数据等 在堆中的每个对象也就分为对象头和除去对象头的实例部分 假如这个对象被我们的某个方法所调用了
    这个时候就会去栈中把这个对象保存下来 所以已上的栈 方法区 堆 被称为java对象模型

总结一下就是： java对象模型就是对象自身的存储模型

   ![img](https://raw.githubusercontent.com/qiurunze123/imageall/master/threadjmm3.png)
