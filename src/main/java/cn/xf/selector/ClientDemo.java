package cn.xf.selector;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientDemo {

    @Test
    public void server() throws Exception {
        // 1.获取服务端通道，使用ServerSocketChannel监听TCP连接
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 2.切换非阻塞模式
        socketChannel.configureBlocking(false);
        // 3.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4.绑定端口号
        socketChannel.bind(new InetSocketAddress(1321));
        // 5.获取selector选择器
        Selector selector = Selector.open();
        // 6.通道注册到选择器
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 7.使用选择器轮询操作
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    // 获取连接
                    SocketChannel accept = socketChannel.accept();
                    // 切换非阻塞
                    accept.configureBlocking(false);
                    // 注册
                    accept.register(selector, SelectionKey.OP_READ);

                } else if (next.isReadable()) {
                    // 获取通道
                    SocketChannel channel = (SocketChannel) next.channel();
                    // 创建缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,length));
                        byteBuffer.clear();
                    }
                }
            }
            iterator.remove();
        }
    }

    @Test
    public void client() throws Exception {
        // 1.获取通道，绑定主机和端口号，使用SocketChannel读写数据
        SocketChannel channel = SocketChannel.open(
                new InetSocketAddress("127.0.0.1", 1321)
        );
        // 2.切换到非阻塞模式
        channel.configureBlocking(false);
        // 3.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 4.写入buffer数据
        byteBuffer.put("hello".getBytes());
        // 5.模式切换
        byteBuffer.flip();
        // 6.写入通道
        channel.write(byteBuffer);
        // 7.关闭
        byteBuffer.clear();
    }
}
