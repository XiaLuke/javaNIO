package cn.xf.chat.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端线程
 *
 * @author XF
 * @date 2022/11/04
 */
public class CustomerClientThread implements Runnable{
    private Selector selector;
    public CustomerClientThread(Selector selector){
        this.selector = selector;
    }
    @Override
    public void run() {
        try{



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
                // 可读状态
                if (next.isReadable()) {
                    // 操作就绪的通道
                    readableOperator(selector, next);
                }
            }
        }
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

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
        }
    }
}
