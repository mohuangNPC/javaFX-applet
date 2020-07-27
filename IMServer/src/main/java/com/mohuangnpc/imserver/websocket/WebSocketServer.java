package com.mohuangnpc.imserver.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/25 9:58
 */
public class WebSocketServer {
    public static void main(String[] args) {
        //创建主从线程池
        EventLoopGroup groudMaster = new NioEventLoopGroup();
        //创建从线程池
        EventLoopGroup groudFollwe = new NioEventLoopGroup();
        try {
            //创建服务器类
            ServerBootstrap server = new ServerBootstrap();
            server.group(groudMaster,groudFollwe)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitialzer());
            ChannelFuture sync = server.bind(9999).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            groudMaster.shutdownGracefully();
            groudFollwe.shutdownGracefully();
        }

    }
}
