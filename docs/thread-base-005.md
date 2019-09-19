### 多线程异常处理机制

  ① 在ThreadCatchProcess 里面你会发现

      1. 不加try catch抛出4个异常，都带线程名字
      2. 加了try catch,期望捕获到第一个线程的异常，线程234不应该运行，希望看到打印出Caught Exception
      3. 执行时发现，根本没有Caught Exception，线程234依然运行并且抛出异常
        
      说明线程的异常不能用传统方法捕获
      
   ②在ThreadCatchProcess2中你会发现  子线程抛出异常主线程不会处理
   
   ③如果想要设置自己的异常处理器，可以通过 ThreadCatchProcess3 来示例演示 异常处理器Thread.UncaughtExceptionHandler是一个函数式接口
   
   
    如果设置了异常处理器uncaughtExceptionHandler，那么将会使用这个
    如果没设置，将会在祖先线程组中查找第一个重写了uncaughtException的线程组，然后调用他的uncaughtException方法
    如果都没有重写，那么使用应用默认的全局异常处理器defaultUncaughtExceptionHandler
    如果还是没有设置，直接标准错误打印信息

   ④如果未捕获则可能把错误信息吐给前端 产生麻烦 捕获异常可以报警一类的 最常见的方式是把错误信息写入日志，或者重启线程、或执行其他修复或诊断