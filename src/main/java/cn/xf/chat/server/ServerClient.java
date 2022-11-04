package cn.xf.chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ServerClient {
    public void start() throws Exception {
        // 1.创建Selector选择器
        Selector selector = Selector.open();

        // 2.创建ServerSocketChannel通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 3.为channel通道绑定监听端口
        socketChannel.bind(new InetSocketAddress(9889));
        socketChannel.configureBlocking(false);
        // 4.循环等待连接接入
        // channel绑定到selector中
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // for(;;) == while(true)
        for (; ; ) {
            // 先判断selector中是否有就绪的通道
            int channelCount = selector.select();
            if (channelCount == 0) {
                continue;
            }
            // 获取可用channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();

                // 移除当前取出的key
                iterator.remove();

                // 5.通过就绪状态处理业务
                // accept
                if (next.isAcceptable()) {
                    acceptOperator(selector, socketChannel);
                }
                // 可读状态
                else if (next.isReadable()) {
                    // 操作就绪的通道
                    readableOperator(selector, next);
                }
            }
        }


    }

    /**
     * 可读操作
     *
     * @param selector 选择器
     * @param next     就绪通道
     */
    private void readableOperator(Selector selector, SelectionKey next) throws Exception {
        // 1.获取就绪通道
        SocketChannel channel = (SocketChannel) next.channel();
        // 2.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 3.循环读取客户端传递的信息
        int length = channel.read(buffer);
        String readMsg = "";
        if (length > 0) {
            // 切换模式
            buffer.flip();
            // 读
            readMsg += Charset.forName("UTF-8").decode(buffer);
        }
        // 4.channel重新注册到selector上，监听可读事件
        channel.register(selector, SelectionKey.OP_READ);
        // 5.客户端发送的消息广播到其他客户端
        if (readMsg.length() > 0) {
            System.out.println(readMsg);
            fanoutMsg(selector, readMsg, channel);
        }
    }

    /**
     * 广播消息
     *
     * @param selector 选择器
     * @param readMsg  读味精
     * @param channel  通道
     */
    private void fanoutMsg(Selector selector, String readMsg, SocketChannel channel) throws Exception{
        // 1.获取到所有客户端
        Set<SelectionKey> channelKeys = selector.keys();
        Iterator<SelectionKey> iterator = channelKeys.iterator();
        // 2.循环广播消息
        while (iterator.hasNext()) {
            SelectionKey item = iterator.next();
            Channel itemChannel = item.channel();
            // 开始广播，需要是socketChannel并且不是当前的channel
            if (itemChannel instanceof SocketChannel && itemChannel != channel){
               ((SocketChannel) itemChannel).write(Charset.forName("UTF-8")
                       .encode(readMsg));
            }
        }

    }

    /**
     * 接收方法
     *
     * @param selector      选择器
     * @param socketChannel 套接字通道
     * @throws IOException ioexception
     */
    private void acceptOperator(Selector selector, ServerSocketChannel socketChannel) throws IOException {
        // 1.创建socketChannel
        SocketChannel accept = socketChannel.accept();
        // 2.把socketChannel设置为非阻塞模式
        accept.configureBlocking(false);
        // 3.把channel注册到selector选择器上，监听可读状态
        accept.register(selector, SelectionKey.OP_READ);
        // 4.回复消息
        accept.write(Charset.forName("UTF-8").encode("进入聊天室"));
    }

    public static void main(String[] args) throws Exception {
        new ServerClient().start();
    }

}
