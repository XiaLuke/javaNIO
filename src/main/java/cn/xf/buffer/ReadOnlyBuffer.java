package cn.xf.buffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * 只读缓冲区
 *
 * @author XF
 * @date 2022/11/02
 */
public class ReadOnlyBuffer {
    @Test
    public void test() {
        ByteBuffer buffer = ByteBuffer.allocate(20);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 创建只读缓冲区
        ByteBuffer byteBuffer = buffer.asReadOnlyBuffer();

        // 修改原缓冲区中数据
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 5;
            buffer.put(i, b);
        }

        // 从哪到哪
        byteBuffer.position(0);
        byteBuffer.limit(buffer.capacity());

        // 遍历只读缓冲区
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
    }
}
