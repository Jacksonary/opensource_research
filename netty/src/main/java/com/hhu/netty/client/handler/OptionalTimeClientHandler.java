package com.hhu.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import java.util.Date;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description 修复碎片重组不一致的问题，基于TimeClientHandler的优化
 */
public class OptionalTimeClientHandler extends ChannelHandlerAdapter {

    private ByteBuf buf;

    /**
     * handlerAdded 和 handlerRemove 是ChannelHandler的2个生命期，可以在里面做随便的工作，只要不会长时间阻塞
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 初始化累计buf容器
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        // 所有的数据都会累积到之前定义的buffer容器中
        buf.writeBytes(m);
        m.release();

        // 检查容器中是否大于4bytes，是的话就执行真正的逻辑，否则继续收集数据
        if (buf.readableBytes() >= 4) {
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }
}
