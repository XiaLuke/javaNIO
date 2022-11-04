package cn.xf.buffer;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射I/O
 *
 * @author XF
 * @date 2022/11/02
 */
public class MemoryMappingIO {
    @Test
    public void test() throws Exception{
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test.txt","rw");
        FileChannel channel = file.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        map.put(0,(byte) 20);
        map.put(2,(byte) 333);
        file.close();


    }
}
