package com.geek.aionio.nioCopyFile;

import java.nio.ByteBuffer;

/**
 * @author 邱润泽 bullock
 */
public class BufferSize {

    public static void main(String[] args) throws Exception {
        ByteBuffer b = ByteBuffer.allocate(15); // 15个字节大小的缓冲区
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        for (int i = 0; i < 10; i++) {
            // 存入10个字节数据
            b.put((byte) i);
        }
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        b.flip(); // 重置position
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        for (int i = 0; i < 5; i++) {
            System.out.print(b.get());
        }
        System.out.println();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        b.flip();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());

    }
}
