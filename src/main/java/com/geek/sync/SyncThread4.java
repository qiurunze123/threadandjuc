package com.geek.sync;

/**
 * @author 邱润泽 bullock
 *
 * 修饰一个对象
 * synchronized 给account对象加了锁。这时，当一个线程访问account对象时，
 * 其他试图访问account对象的线程将会阻塞，直到该线程访问account对象结束。
 * 也就是说谁拿到那个锁谁就可以运行它所控制的那段代码。
 *
 *
 *
 * 说明：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。
 *
 */
public class SyncThread4 implements Runnable {

    private Account account;
    public SyncThread4(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        synchronized (account) {
            account.deposit(500);
            account.withdraw(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }
    }

    public static void main(String[] args) {
        Account account = new Account("zhang san", 10000.0f);
        SyncThread4 accountOperator = new SyncThread4(account);

        final int THREAD_NUM = 5;
        Thread threads[] = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i ++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
            threads[i].start();
        }
    }



}
