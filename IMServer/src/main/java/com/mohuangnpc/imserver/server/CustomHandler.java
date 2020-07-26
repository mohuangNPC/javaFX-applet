package com.mohuangnpc.imserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义助手类
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/23 8:11
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //获取channel
        Channel channel = ctx.channel();
       if(msg instanceof HttpRequest){
           //在控制台打印远程地址
           System.err.println(channel.remoteAddress());
           //定义向客户端发送的数据内容
           ByteBuf content = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);
           //构建http response
           FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
           //为响应增加数据类型和长度
           response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
           response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
           //把响应渲染到html客户端页面
           ctx.writeAndFlush(response);
       }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel 注册");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel 移除");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel 活跃");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel 不活跃");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel 读取完毕");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel 可写更改");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("异常捕获");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.err.println("用户事件触发");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println("助手类添加");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.err.println("助手类移除");
    }
}
