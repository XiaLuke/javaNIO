package cn.xf.channel;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class MyDatagramChannel {
    @Test
    public void readInfo() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        // 绑定端口接收udp的数据包
        channel.bind(new InetSocketAddress(8456));

        while (true) {
            // 接收udp的数据包
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            SocketAddress receive = channel.receive(byteBuffer);
            byteBuffer.flip();
            System.out.println(receive.toString());
            System.out.println(Charset.forName("UTF-8").decode(byteBuffer));
        }


    }


    @Test
    public void sendInfo() throws Exception {
        // 打开一个DatagramChannel
        DatagramChannel channel = DatagramChannel.open();
        // 绑定端口
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8456);
        // 发送数据
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.wrap("hello".getBytes());
            channel.send(byteBuffer, socketAddress);
            System.out.println("完成发送数据");
            Thread.sleep(3000);
        }
    }

    @Test
    public void test() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        // 绑定
        channel.bind(new InetSocketAddress(9999));
        // 创立连接
        channel.connect(new InetSocketAddress("127.0.0.1", 9999));
        // write
        channel.write(ByteBuffer.wrap("hello".getBytes()));
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        // read
        while (true) {
            readBuffer.clear();
            channel.read(readBuffer);
            readBuffer.flip();
            System.out.println(Charset.forName("UTF-8").decode(readBuffer));
        }
    }

}
