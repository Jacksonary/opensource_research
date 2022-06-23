package com.hhu.netty.server.encoder;

import com.hhu.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description MessageToByteEncoder可以更简单的来编码
 */
public class TimeEncoder2 extends MessageToByteEncoder<UnixTime> {

    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        out.writeInt((int) msg.getValue());
    }
}
