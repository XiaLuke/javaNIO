package cn.xf.selector;


import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MySelector {
    @Test
    public void test() throws Exception{
        // 1.获取selector选择器
        Selector selector = Selector.open();
        // 2.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 3.设置通道类型为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 4.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(8888));
        // 5.将通道注册到选择器上并制定监听事件为：“接收”事件，支持的事件可通过validOps()方法获取
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        // 查询就绪的操作是啥
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey next = iterator.next();
            if(next.isAcceptable()){

            }else if(next.isValid()){

            }else if(next.isReadable()){

            }else if(next.isWritable()){

            }else if(next.isConnectable()){

            }
            iterator.remove();
        }

        // 一直获取就绪通道，直到一个通道阻塞到注册事件中
        // System.out.println(selector.select());
        // 控制等待时间
        // System.out.println(selector.select(2000));
        // 立即返回
        // System.out.println(selector.selectNow());
        // System.out.println(serverSocketChannel.validOps());
    }

}
