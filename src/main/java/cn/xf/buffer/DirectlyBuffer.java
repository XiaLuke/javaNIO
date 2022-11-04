package cn.xf.buffer;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲区
 *
 * @author XF
 * @date 2022/11/02
 */
public class DirectlyBuffer {
    @Test
    public void test() throws Exception{
        FileInputStream inputStream = new FileInputStream("C:\\Users\\XF\\Desktop\\test.txt");
        FileChannel inputChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\XF\\Desktop\\test1.txt");
        FileChannel outputChannel = outputStream.getChannel();

        // 创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true){
            buffer.clear();
            int read = inputChannel.read(buffer);
            if(read == -1){
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }
    }
}
