package com.zhaogang.netty.server.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @author weiguo.liu
 * @date 2020/10/27
 * @description 有响应服务器（直接将客户端的内容回显）
 */
public class WithResponseServerHandler extends ChannelHandlerAdapter {

    /**
     * 监听客户端的请求，任何客户端的请求都会促发该方法 属于服务端的逻辑处理层
     *
     * @param ctx
     *            ChannelHandlerContext 提供了各种促发IO事件的操作行为
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // 2. 响应客户端
        // 将客户端的输入回显到客户端，这里不需要手动去释放，因为netty在写出后会自动释放
        // ctx.writeAndFlush(msg);

        // 和上面的功能一样，ChannelHandlerContext.write 只是写到内部的缓冲中，真正out to the wire 由flush出发
        ctx.write(msg);
        ctx.flush();
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
