package cn.xf.channel;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件通道
 * 可以实现read，write，以及scatter/gather操作
 *
 * @author XF
 * @date 2022/11/01
 */
public class MyFileChannel {
    /**
     * 使用FileChannel读取数据到Buffer中
     */
    @Test
    public void readFileToBuffer() throws Exception {
        // 创建FileChannel
        // 因为没有直接可以创建FileChannel的方式，需要通过输入流或输出流或RandomAccessFile获取FileChannel实例
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test.txt", "rw");
        FileChannel channel = file.getChannel();
        // 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        // 读取数据到Buffer
        int read = channel.read(byteBuffer);
        while (read != -1) {
            System.out.println("读取到的数据大小：" + read);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }
        file.close();
        System.out.println("结束");
    }

    @Test
    public void writeFileChannel() throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(2048);
        // 追加到文件头部
        String appendData = "pouokkhiuoijkln";
        buffer.clear();
        buffer.put(appendData.getBytes());
        buffer.flip();
        // 无法保证一次能向fileChannel中写入多少数据，只能循环调用
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        accessFile.close();

    }

    @Test
    public void positionInsert() throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(2048);

        // 指定位置插入数据，
        channel.position(25);

        String appendData = "INSERT@";

        buffer.clear();
        buffer.put(appendData.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        accessFile.close();
    }

    @Test
    public void transferFrom() throws Exception {
        RandomAccessFile file1 = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test.txt", "rw");
        FileChannel fromChannel = file1.getChannel();

        RandomAccessFile file2 = new RandomAccessFile("C:\\Users\\XF\\Desktop\\test1.txt", "rw");
        FileChannel toChannel = file2.getChannel();

        // 目标文件内容来自源文件，从第0位拷贝，拷贝大小位源文件大小
        toChannel.transferFrom(fromChannel,0,fromChannel.size());

        // fromChannel.transferTo(toChannel,0,fromChannel.size());

        file2.close();
        file1.close();




    }
}
