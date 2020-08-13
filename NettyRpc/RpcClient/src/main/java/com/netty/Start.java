package com.netty;

import cn.hutool.core.lang.UUID;
import com.netty.protocol.RpcRequest;
import com.netty.protocol.RpcResponse;
import com.netty.serializer.JSONSerializer;
import com.netty.util.ClientHandler;
import com.netty.util.RpcDecoder;
import com.netty.util.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/30 11:24
 */
public class Start {
    private String host;
    private Integer port;
    private static Channel channel;
    private ClientHandler clientHandler;
    private static EventLoopGroup group;
    public Start(String host, Integer port) {
        this.host = host;
        this.port = port;
    }
    public void connect() {
        group = new NioEventLoopGroup();
        clientHandler = new ClientHandler();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 9999))
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加编码器
                            pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                            //添加解码器
                            pipeline.addLast(new RpcDecoder(RpcResponse.class, new JSONSerializer()));
                            //请求处理类
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            ChannelFuture sync = b.connect().sync();
            sync.addListener(future -> {
                System.err.println(future.isSuccess());
                if (future.isSuccess()) {
                    System.err.println("连接服务端成功");
                }
            });
            channel = sync.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            group.shutdownGracefully();
        }
    }
    /**
     * 发送消息
     *
     * @param request
     * @return
     */
    public RpcResponse send(final RpcRequest request) {
        System.err.println("client:send");
        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientHandler.getRpcResponse(request.getRequestId());
    }

    public static void close() {
        group.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }

}
