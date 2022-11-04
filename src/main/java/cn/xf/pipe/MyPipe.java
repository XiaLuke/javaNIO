package cn.xf.pipe;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 管道
 *
 * @author XF
 * @date 2022/11/03
 */
public class MyPipe {
    @Test
    public void test() throws Exception {
        // 1.获取管道
        Pipe pip = Pipe.open();
        // 2.获取sink通道
        Pipe.SinkChannel sinkChannel = pip.sink();
        // 3.创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello".getBytes());
        byteBuffer.flip();
        // 4.通过sink通道写入数据
        sinkChannel.write(byteBuffer);

        // 5.获取source通道
        Pipe.SourceChannel sourceChannel = pip.source();
        // 6.创建缓冲区/使用缓冲区，读取数据
        // ByteBuffer buffer = ByteBuffer.allocate(1024);
        byteBuffer.flip();
        int length = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(),0,length));
        // 7.关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }
}
