
package com.geek.Nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class NIOEchoServer {
    class EchoClient {
        private LinkedList<ByteBuffer> outq;

        EchoClient() {
            outq = new LinkedList<ByteBuffer>();
        }

        // Return the output queue.
        public LinkedList<ByteBuffer> getOutputQueue() {
            return outq;
        }

        // Enqueue a ByteBuffer on the output queue.
        public void enqueue(ByteBuffer bb) {
            outq.addFirst(bb);
        }
    }

    private Selector selector;

    /**
     * Accept a new client and set it up for reading.
     */
    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);

            // Register this channel for reading.
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);

            // Allocate an EchoClient instance and attach it to this selection key.
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        } catch (Exception e) {
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }

    }

    /**
     * Read from a client. Enqueue the data on the clients output
     * queue and set the selector to notify on OP_WRITE.
     */
    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;

        try {
            len = channel.read(bb);
            if (len < 0) {
                disconnect(sk);
                return;
            }
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }

        // Flip the buffer.
        bb.flip();
        

        EchoClient echoClient = (EchoClient) sk.attachment();
        echoClient.enqueue(bb);

        // We've enqueued data to be written to the client, we must
        // not set interest in OP_WRITE.
        sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    /**
     * Called when a SelectionKey is ready for writing.
     */
    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }

            if (bb.remaining() == 0) {
                // The buffer was completely written, remove it.
                outq.removeLast();
            }
        } catch (Exception e) {
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            disconnect(sk);
        }

        // If there is no more data to be written, remove interest in
        // OP_WRITE.
        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();

        InetAddress clientAddress = channel.socket().getInetAddress();
        System.out.println(clientAddress.getHostAddress() + " disconnected.");

        try {
            channel.close();
        } catch (Exception e) {
            System.out.println("Failed to close client socket channel.");
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();

        // Create non-blocking server socket.
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // Bind the server socket to localhost.
//        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);

        // Register the socket for select events.
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        // Loop forever.
        for (;;) {
            selector.select();
            Set readyKeys = selector.selectedKeys();
            Iterator i = readyKeys.iterator();

            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey) i.next();
                i.remove();

                if (sk.isAcceptable()) {
                    doAccept(sk);
                }
                if (sk.isValid() && sk.isReadable()) {
                    doRead(sk);
                }
                if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                }
            }
        }
    }

    // Main entry point.
    public static void main(String[] args) {
        NIOEchoServer echoServer = new NIOEchoServer();
        try {
            echoServer.startServer();
        } catch (Exception e) {
            System.out.println("Exception caught, program exiting...");
            e.printStackTrace();
        }
    }
}
