package com.geek.threadandjuc.base;

public  class ChangeObjectThread {


    public static User u = new User();

    public static class User {

        public User() {
            id = 0;
            name="0";
        }

        private String name;

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    if(u.getId()!=Integer.valueOf(u.getName())) {
                        System.out.println(u.toString());
                    }
                }
                Thread.yield();
            }
        }
    }
    public static class ChangeThread extends Thread{
        volatile  boolean stopme = false;
//        public void stopMe(){
//            stopme = true;
//        }

        @Override
        public void run(){
//            while(true){
//                if(stopme){
//                    System.out.println("exit by stop me!");
//                    break;
//                }
while(true){
                synchronized (u){
                    int v = (int)(System.currentTimeMillis()/1000);
                    u.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    u.setName(String.valueOf(v));
                }
                Thread.yield();
            }

        }

    }

    public static void main(String[] args) {
        new ReadObjectThread().start();
        while (true){
            Thread t = new ChangeThread();
            t.start();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.stop();
        }
    }
}
