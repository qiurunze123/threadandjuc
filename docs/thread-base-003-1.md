###  多线程的生命周期状态纽机流转

当我们在java程序中新建一个线程时,他的状态是new,当我们调用线程的start()方法时 状态被改变runnable 程序调度器都会为runnable线程池
中的线程分配CPU时间并且将它们的状态改变为running 其他的线程状态还有waiting blocked 和 dead

这个说法是正确的的吗？？