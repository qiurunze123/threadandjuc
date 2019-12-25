package com.apollo.threadinterview.deadlock;

/**
 * @author 邱润泽 bullock
 * 生产中的实际例子 转账
 *
 * 需要俩把锁
 *
 * 获取俩把锁 且余额大于0  则扣除转出人 增加收款人的余额 是原子操作
 *
 * 顺序相反导致 死锁 我首先获取我的锁在获取他的锁 他首先回去他的所锁在获取我的锁 就会产生死锁
 */
public class TransferDeadLockMoney implements Runnable {

    int flag = 1;
    static Account a = new Account(500);
    static Account b = new Account(500);


    public static void main(String[] args) throws InterruptedException {
        TransferDeadLockMoney r1 = new TransferDeadLockMoney();
        TransferDeadLockMoney r2 = new TransferDeadLockMoney();

        r1.flag =1;
        r2.flag = 0;

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("A的余额" + a.balance);
        System.out.println("B的余额" + b.balance);

    }
    @Override
    public void run() {

        if(flag == 1){
            transferMoney(a,b,200);
        }

        if(flag == 0){
            transferMoney(b,a,200);
        }
    }

    public static void transferMoney(Account from ,Account to , int amount ){
        synchronized (from){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to){
                if(from.balance - amount <0){
                    System.out.println("余额不足，转账失败 。");
                }

                from.balance -=amount;
                to.balance+=amount;
                System.out.println("成功转账");
            }
        }
    }
    static class Account{

        int balance ;

        public Account(int balance) {
            this.balance = balance;
        }
    }
}
