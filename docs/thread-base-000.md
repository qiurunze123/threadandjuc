### 多线程到底有几种实现方式 ?


    不同得角度看 有不同得回达 
    一般来说 俩种实现方式 分别是实现Runnable接口和继承Thread类 
    其实继承Thread类和实现Runnable接口本质上是没有区别的最终都是调用start()方法来新建线程
    他们的区别就是
    ①实现runnable
     @Override
        public void run() {
            if (target != null) {
                target.run();
            }
        }
     是调用target.run()
     ②继承Thread类是重写整个run方法
     虽然有许多的方式 线程池 定时器 等等各种写法但是也都只是表面不同但是实质都一样
     
     
     结论： 我们只能通过新建Thread的一种方式来创建线程 但是类里面的run方法有两种实现方式
     一种就是重写Run方法
     第二种就是实现Runnable接口把接口实例传给Thread类 除了这些还有一些类似定时器,线程池，lamda等都只是表象本质上还是都是一样的
    