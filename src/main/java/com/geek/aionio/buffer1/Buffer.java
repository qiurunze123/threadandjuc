package com.geek.aionio.buffer1;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 邱润泽 bullock
 * 步骤：
 *
 * 1. 得到Channel
 *
 * 2. 申请Buffer
 *
 * 3. 建立Channel和Buffer的读/写关系
 *
 * 4. 关闭
 */
public class Buffer {

    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream(new File(
                "d:\\a.txt"));
        FileChannel fc = fin.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fc.read(byteBuffer);
        fc.close();
        byteBuffer.flip();//读写转换
    }
}
