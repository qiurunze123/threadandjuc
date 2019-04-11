package com.geek.VolatileExample;

/**
 * 无法保证原子性
 * Volatile实现禁止指令重排序原理
 * 如果线程A刚获取了i的值，正在做+1操作时，线程B修改了i的值，那么就算线程B立即将i的值刷新至内存，线程A也无法得知，这样就会导致线程不安全
 */
public class AccountingVol implements Runnable{
	static AccountingVol instance=new AccountingVol();
	static volatile int i=0;
	@Override
	public void run() {
		for(int j=0;j<10000000;j++){
			i++;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1=new Thread(instance);
		Thread t2=new Thread(instance);
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(i);
	}
}
