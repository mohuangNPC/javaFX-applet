package com.mohuangnpc.imserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/22 8:06
 */
public class HelloNettyServer {
    public static void main(String[] args)  {
        //创建一组线程池
        //主线程池: 用于接受客户端请求,不做任何处理
        EventLoopGroup groupMaster = new NioEventLoopGroup();
        //从线程池: 主线程会把任务交给它,让他做任务
        EventLoopGroup groupFollow = new NioEventLoopGroup();
        try {
            //创建服务器启动类
            ServerBootstrap server = new ServerBootstrap();
            server.group(groupMaster,groupFollow)//设置主从线程池
                    .channel(NioServerSocketChannel.class)//设置Nio双向通道
                    .childHandler(new HelloNettyServerInitializer());//添加子处理器，用于处理从线程池的任务
            //启动服务，并且设置端口号，同时启动方式为同步
            ChannelFuture channelFuture = server.bind(8888).sync();
            //监听关闭的channel,设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            groupMaster.shutdownGracefully();
            groupFollow.shutdownGracefully();
        }
    }
}
