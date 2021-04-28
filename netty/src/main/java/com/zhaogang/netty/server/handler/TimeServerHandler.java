package com.zhaogang.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    /**
     * channelActive 方法在连接建立后即将数据传输时将会被调用
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 为了发送消息，先要分配一个新的包含消息的buffer
        final ByteBuf time = ctx.alloc().buffer(4);
        // 写入需要发送的消息，注：ByteBuf有2端，一端可以写、一端可以读
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // writeAndFlush方法（包括write方法）都是返回一个ChannelFuture对象，这个对象就是一个还没有发生的IO操作
        // 那些已经请求的行为还没有被指执行
        final ChannelFuture channelFuture = ctx.writeAndFlush(time);
        channelFuture.addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        assert channelFuture == future;
                        // 关闭client的连接
                        ctx.close();
                    }
                }
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
