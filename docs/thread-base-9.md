
**线程池精讲**


线程池各类的关系：

Executors -- new ThreadPoolExecutor

ThreadPoolExecutor extends AbstractExecutorService
 
AbstractExecutorService(抽象类) implements ExecutorService

interface ExecutorService extends Executor 



**线程池运行流程**

![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/theradpool8.png)


https://www.jianshu.com/p/e6a8048d0446 数据源 与 事务问题   

定义AOP共同使用时，事务不起作用