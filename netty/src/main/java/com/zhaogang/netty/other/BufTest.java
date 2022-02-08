package com.zhaogang.netty.other;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author jacks
 * @date 2022/1/29
 */
public class BufTest {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        byte[] bytes = "hello".getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = buffer.writeBytes(bytes);
        for (int i = 0; i < buf.writerIndex(); i++) {
            byte b = buf.getByte(i);
            System.out.println(b);
        }
        System.out.println("done");
    }
}
