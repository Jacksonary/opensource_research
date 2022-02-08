package com.zhaogang.netty.client;

import com.zhaogang.netty.client.decoder.TimeDecoder;
import com.zhaogang.netty.client.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description 用于TimeServer的客户端，注意netty客户端和实现端的区别
 *              <p>
 *              netty中Server和Client的区别主要在于 Bootstrap 和
 */
public class TimeClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // Bootstrap和ServerBootstrap 类似，只是为了没有服务端Channel的场景（如Client或者非Channel）
        Bootstrap boss = new Bootstrap();
        // 若这里仅指定一个 NioEventLoopGroup ，那他既是boss又是worker,但是，boss-worker不用于客户端
        boss.group(worker)
            // 和NioServerSocketChannel类似，只是NioSocketChannel是创建client端的管道
            .channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 1. 原始方案
                    // ch.pipeline().addLast(new TimeClientHandler());
                    // 2. 优化方案1
                    // ch.pipeline().addLast(new OptionalTimeClientHandler());
                    // 3. 优化方案2
                    ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                    // ch.pipeline().addLast(new UnixTimeDecoder(), new UnixTimeClientHandler());
                }
            });
        // client-side 的 SocketChannel 是没有 parent 的概念的

        try {
            // 客户端是connect，不是bind
            ChannelFuture future = boss.connect("127.0.0.1", 8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
            e.printStackTrace();
        }
    }
}
