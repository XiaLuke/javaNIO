package cn.xf.buffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * 缓冲区分片
 *
 * @author XF
 * @date 2022/11/02
 */
public class BufferFragmentation {
    @Test
    public void test() {
        ByteBuffer buff = ByteBuffer.allocate(20);
        for (int i = 0; i < buff.capacity(); i++) {
            buff.put((byte) i);
        }
        // 起始位置
        buff.position(3);
        // 标识最多可以写到哪个位置
        buff.limit(7);
        // 创建缓冲区
        ByteBuffer slice = buff.slice();

        // 改变子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte value = slice.get(i);
            value *= 5;
            slice.put(i, value);
        }

        buff.position(0);
        buff.limit(buff.capacity());

        while (buff.remaining() > 0) {
            System.out.println(buff.get());
        }
    }
}
