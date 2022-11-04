package cn.xf.channel;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 套接字通道
 *
 * @author XF
 * @date 2022/11/01
 */
public class MySocketChannel {
    @Test
    public void createMode1() throws Exception {
        // 1.创建SocketChannel
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        // 2.校验连接
        // socketChannel是否为open状态
        System.out.println("socketChannel是否开启：" + channel.isOpen());
        // 是否已经被连接
        System.out.println("socketChannel是否被链接：" + channel.isConnected());
        // 是否正在连接
        System.out.println("socketChannel是否正在链接：" + channel.isConnectionPending());
        // 正在进行套接字的连接是否完成
        System.out.println("正在进行套接字的连接是否完成：" + channel.finishConnect());

        // 3.设置读写模式（阻塞还是非阻塞）,不进行设置时将一直阻塞
        channel.configureBlocking(false);

        // 4.读写操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        channel.read(byteBuffer);
        channel.close();


    }

    public void createMode2() throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("", 8877));
    }
}
