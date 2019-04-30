
package com.geek.aionio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadEchoServer {
    private static ExecutorService  tp=Executors.newCachedThreadPool();
    static class HandleMsg implements Runnable{
        Socket clientSocket;
        public HandleMsg(Socket clientSocket){
            this.clientSocket=clientSocket;
        }
        
        public void run(){
            BufferedReader is =null;
            PrintWriter os = null;
            try {

                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
                // 从InputStream当中读取客户端所发送的数据
                String inputLine = null;
                long b=System.currentTimeMillis();
                while ((inputLine = is.readLine()) != null) {
                    os.println(inputLine);
                }
                long e=System.currentTimeMillis();
                System.out.println("spend:"+(e-b)+"ms");
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    if(is!=null)is.close();
                    if(os!=null)os.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String args[]) {
        ServerSocket echoServer = null;
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(8000);
        } catch (IOException e) {
            System.out.println(e);
        }
        while (true) {
            try {
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
                tp.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
