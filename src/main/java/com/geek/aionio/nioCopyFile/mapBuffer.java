package com.geek.aionio.nioCopyFile;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 邱润泽 bullock
 */
public class mapBuffer {

    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("d:\\a.txt", "rw");
        FileChannel fc = raf.getChannel();
        // 将文件映射到内存中
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0,
                raf.length());
        while (mbb.hasRemaining()) {
            System.out.print((char) mbb.get());
        }
        // 修改文件
        mbb.put(0, (byte) 98);
        raf.close();
    }
}
