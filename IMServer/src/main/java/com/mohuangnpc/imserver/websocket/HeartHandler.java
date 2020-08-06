package com.mohuangnpc.imserver.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于检测channel的心跳handler
 * 集成ChannelInboundHandlerAdapter
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/8/5 11:17
 */
public class HeartHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;//强制类型转换
            if(event.state() == IdleState.READER_IDLE){
                System.err.println("进入读空闲");
            }else if(event.state() == IdleState.WRITER_IDLE){
                System.err.println("进入写空闲");
            }else if(event.state() == IdleState.ALL_IDLE){
                System.err.println("channel关闭之前:user数量:"+ChatHandler.getClents().size());
                Channel channel = ctx.channel();
                //资源释放
                channel.close();
                System.err.println("channel关闭之后:user数量:"+ChatHandler.getClents().size());
            }
        }
    }
}
