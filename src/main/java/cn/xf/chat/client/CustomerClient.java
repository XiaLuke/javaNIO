package cn.xf.chat.client;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author XF
 * @date 2022/11/04
 */
public class CustomerClient {
    public void start(String username) throws Exception {
        // 1.连接服务器
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9889));
        // 2.接收服务器响应消息
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        // 创建线程
        CustomerClientThread clientThread = new CustomerClientThread(selector);
        new Thread(clientThread).start();

        // 3.向服务器发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String lineMsg = scanner.nextLine();
            if (lineMsg.length()>0) {
                channel.write(Charset.forName("UTF-8").encode(username+":"+lineMsg));
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new CustomerClient().start("user1");
    }
}
