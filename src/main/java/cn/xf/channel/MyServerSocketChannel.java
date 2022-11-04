package cn.xf.channel;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 服务器套接字通道
 *
 * @author XF
 * @date 2022/11/01
 */
public class MyServerSocketChannel {
    @Test
    public void test1() throws Exception{
        // 端口号
        int port = 8081;
        // buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
        // ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 绑定端口
        channel.socket().bind(new InetSocketAddress(port));
        // 在非阻塞环境下运行，如果没有对象传递进行就一直阻塞
        //channel.configureBlocking(false);
        // 监听是否有新的连接运行
        while (true){
            System.out.println("等待连接");
            SocketChannel accept = channel.accept();
            if(accept==null){
                Thread.sleep(3000);
                System.out.println("null");
            }else{
                System.out.println("连接来自："+accept.socket().getRemoteSocketAddress());
                buffer.rewind();
                accept.write(buffer);
                accept.close();
            }
        }
    }
}
