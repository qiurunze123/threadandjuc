package com.geek.aionio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class NIOClient {
	private Selector selector;
	public void init(String ip, int port) throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		this.selector = SelectorProvider.provider().openSelector();
		channel.connect(new InetSocketAddress(ip, port));
		channel.register(selector, SelectionKey.OP_CONNECT);
	}

	public void working() throws IOException {
		while (true) {
			if (!selector.isOpen())
				break;
			selector.select();
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = ite.next();
				ite.remove();
				// 连接事件发生
				if (key.isConnectable()) {
					connect(key);
				} else if (key.isReadable()) {
					read(key);
				}
			}
		}
	}

	/**
	 * 处理读取服务端发来的信息 的事件
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("客户端收到信息：" + msg);
		channel.close();
		key.selector().close();
	}

	public void connect(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		// 如果正在连接，则完成连接
		if (channel.isConnectionPending()) {
			channel.finishConnect();
		}
		channel.configureBlocking(false);
		channel.write(ByteBuffer.wrap(new String("hello server!\r\n")
				.getBytes()));
		channel.register(this.selector, SelectionKey.OP_READ);
	}

	/**
	 * 启动客户端测试
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		NIOClient client = new NIOClient();
		client.init("localhost", 8000);
		client.working();
	}
}
