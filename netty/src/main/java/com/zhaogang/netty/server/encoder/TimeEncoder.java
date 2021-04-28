package com.zhaogang.netty.server.encoder;

import com.zhaogang.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description 编码要比解码简单得多，因为它不需要解决数据包碎片化的问题
 */
public class TimeEncoder extends ChannelHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime unixTime = (UnixTime) msg;
        ByteBuf encodeBuffer = ctx.alloc().buffer(4);
        encodeBuffer.writeInt((int) unixTime.getValue());
        // 这里不需要去flush，这个handler只是为了切割，覆盖flush方法
        ctx.write(encodeBuffer, promise);
    }
}
