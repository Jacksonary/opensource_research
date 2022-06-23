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
public class InBoundHandler2 extends ChannelHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(InBoundHandler2.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf)msg;
        String strMsg = data.toString(StandardCharsets.UTF_8);
        LOGGER.info(">> channelRead -> data: {}", strMsg);
        ctx.writeAndFlush(Unpooled.copiedBuffer("InBoundHandler2 | " + strMsg, StandardCharsets.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
