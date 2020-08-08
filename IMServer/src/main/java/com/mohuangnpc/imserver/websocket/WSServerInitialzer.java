package com.mohuangnpc.imserver.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/25 10:05
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取管道（pipeline）
        ChannelPipeline pipeline = ch.pipeline();
        //websocket基于http协议，所需要的http编解码器
        pipeline.addLast(new HttpServerCodec());
        //在http上有一些数据流产生，有大有小，我们要对其进行处理，既然如此，我们需要使用netty对我们的大数据流写提供支持，这个类叫:ChunkedWriteHandler
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpmessage进行聚合处理，聚合成request或者response
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //===================心跳检测===================================
        pipeline.addLast(new IdleStateHandler(8,10,12));
        //自定义空闲状态检测handler
        pipeline.addLast(new HeartHandler());
        /**
         * 本handler会帮忙处理一些繁重复杂的事情，
         * 会处理握手动作: handshaking(close,ping,pong) ping+pong=心跳
         * 对于websocket来讲，都是以frams进行传输，不同的数据类型对应的frams也不同
         *
        */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //自定义handler
        pipeline.addLast(new ChatHandler());
    }
}
