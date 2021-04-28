package com.zhaogang.netty.server;

import com.zhaogang.netty.server.encoder.TimeEncoder;
import com.zhaogang.netty.server.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author weiguo.liu
 * @date 2020/10/27
 * @description 服务器的启动类
 */
public class ServerStarter {

    private int port;

    public ServerStarter(int port) {
        this.port = port;
    }

    public void run() {
        // NioEventLoopGroup 是处理IO操作的多线程事件循环，netty对不同的传输提供了多种NioEventLoop的实现
        // 这个样例是服务端的app，所以需要2个NioEventLoopGroup，通常
        // 第1个叫做Boss，负责接受连接
        // 第2个叫做worker，在boss和register接受某个连接后负责处理传输
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // ServerBootstrap是一个配置server的helper，也是可以直接使用Channel（但通常不需要这么干，ServerBootstrap已经做了大部分工作）
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 使用 NioServerSocketChannel 来初始化一个新的 Channel，以此来接受连接
                    .channel(NioServerSocketChannel.class)
                    // 这个handler总是被新接受的Channel来评估，ChannelInitializer 是为了帮助用户配置新的的Channel
                    // 如果需要通过添加一些诸如DiscardServerHandler的handler来配置新Channel的ChannelPipeline来实现
                    // 随着应用程序变得越来越复杂，可能会向管道中添加更多的处理程序，并最终将这个匿名类提取到一个顶级类中。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 在这里添加不同的handler
//                            ch.pipeline().addLast(new DiscardServerHandler());
//                            ch.pipeline().addLast(new PrintServerHandler());
//                            ch.pipeline().addLast(new WithResponseServerHandler());
                            ch.pipeline().addLast(new TimeEncoder(), new TimeServerHandler());
                        }
                    })
                    // 基于netty来实现Server，允许对Socket的一些参数进行设置，如tcpNoDelay、keepAlive
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.printf(">> occur error -> %s%n", e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 测试可以直接通过 telnet 对应的端口，在里面输入信息，Server端就会打印出对应的信息
     * @param args
     */
    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new ServerStarter(port).run();
    }

}
