package com.hhu.netty.client.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description ReplayingDecoder和ByteToMessageDecoder功能一样，只是他更加简化了decoder
 */
public class TimeDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readBytes(4));
    }
}
