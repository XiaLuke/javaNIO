package cn.xf.buffer;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class FirstBuffer {
    @Test
    public void test() throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int read = channel.read(byteBuffer);

        // 如果读取到的不为空
        while (read != -1) {
            // 转换buffer模式
            byteBuffer.flip();
            // 后续还有内容
            while (byteBuffer.hasRemaining()){
                System.out.println(byteBuffer.get());
            }
            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }
        channel.close();
    }

    @Test
    public void putAndRead(){
        //put
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
        }

        // 重置缓冲区
        buffer.flip();

        // 读
        while (buffer.hasRemaining()){
            int value = buffer.get();
            System.out.println(value);
        }
    }
}
