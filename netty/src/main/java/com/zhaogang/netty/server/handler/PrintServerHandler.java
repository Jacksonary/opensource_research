package com.zhaogang.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author weiguo.liu
 * @date 2020/10/27
 * @description 打印服务器（接收到的请求直接在控制台打印，但不给出响应）
 */
public class PrintServerHandler extends ChannelHandlerAdapter {

    /**
     * 监听客户端的请求，任何客户端的请求都会促发该方法 属于服务端的逻辑处理层
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ByteBuf in = (ByteBuf) msg;
        try {
            // 1. 将客户端的信息输出到控制台
            // 下面是易懂的低效循环，当然也可以写作：System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            // 丢弃掉消息，当然也可以直接 in.release()
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 由netty引发的异常将进入该方法
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
