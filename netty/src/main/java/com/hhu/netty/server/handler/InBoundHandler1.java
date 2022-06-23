package com.hhu.netty.server.handler;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author jacks
 * @date 2022/1/29
 */
public class InBoundHandler1 extends ChannelHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InBoundHandler1.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf)msg;
        String strMsg = data.toString(StandardCharsets.UTF_8);
        LOGGER.info(">> channelRead -> data: {}", strMsg);

        // 拉起管道中下一个 handler 的 channelRead 方法
        ctx.fireChannelRead(Unpooled.copiedBuffer("InBoundHandler1 | " + strMsg, StandardCharsets.UTF_8));
        // ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
