package com.hhu.netty.server.handler;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author weiguo.liu
 * @date 2020/10/27
 * @description 无响应服务器（接收到的请求不做任何处理，也不给出响应）
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    /**
     * 监听客户端的请求，任何客户端的请求都会促发该方法 属于服务端的逻辑处理层
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 直接丢弃数据
        ((ByteBuf)msg).release();
    }

    /**
     * 由netty引发的异常将进入该方法
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        System.out.println(">> exceptionCaught -> cause: " + JSON.toJSONString(cause));
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
