package cn.xf.filelock;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件锁
 *
 * @author XF
 * @date 2022/11/03
 */
public class MyFileLock {
    @Test
    public void test() throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
        String filePath = "C:\\Users\\XF\\Desktop\\test.txt";
        Path path = Paths.get(filePath);
        FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        channel.position(channel.size()-1);

        // 加锁
        FileLock lock = channel.lock();

        System.out.println("是否为共享锁" + lock.isShared());

        channel.write(buffer);
        channel.close();

        read(filePath);
    }

    public void read(String path) throws Exception {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String value = bufferedReader.readLine();
        while (value != null){
            System.out.println("获取到内容"+value);
            value = bufferedReader.readLine();
        }
        bufferedReader.close();
        fileReader.close();
    }
}
