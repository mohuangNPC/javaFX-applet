package com.mohuangnpc.imserver.websocket;

import cn.hutool.json.JSONUtil;
import com.mohuangnpc.imserver.entity.ChatMsg;
import com.mohuangnpc.imserver.entity.DataContent;
import com.mohuangnpc.imserver.entity.UserChannelRel;
import com.mohuangnpc.imserver.enums.MsgActionEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理消息的handler
 * 由于它的传输数据的载体是frame是消息载体,这个frame是消息载体在netty中，是用于为websocket专门处理文本对象的，frame是消息载体，此类叫TextWebSocketFrame
 *
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/25 10:23
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channel
    private static ChannelGroup clents = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端所传输的消息
        String content = msg.text();
        System.err.println("接收到的数据:" + content);
        Channel channel = ctx.channel();
        //1.获取客户端发来的消息
        DataContent dataContent = JSONUtil.toBean(content, DataContent.class);
        Integer action = dataContent.getAction();
        //2.判断消息的类型，根据不同的类型来处理不通的业务
        if (action == MsgActionEnum.CONNECT.type) {
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId, channel);
        } else if (action == MsgActionEnum.CHAT.type) {
            ChatMsg chatMsg = dataContent.getChatMsg();
            String chatMsgMsg = chatMsg.getMsg();
            String senderId = chatMsg.getSenderId();
            String receiverId = chatMsg.getReceiverId();
            //保存消息到数据库,暂时没有用map代替

            Channel receiverChannel = UserChannelRel.get(receiverId);
            if (receiverChannel == null) {
                //离线用户
                System.err.println("离线用户");
            } else {
                Channel findChannel = clents.find(receiverChannel.id());
                if (findChannel != null) {
                    //用户在线
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JSONUtil.parseObj(dataContent, false).toJSONString(0)
                            )
                    );
                }else {
                    //离线用户
                    System.err.println("离线用户");
                }
            }
        } else if (action == MsgActionEnum.SIGEND.type) {

        } else if (action == MsgActionEnum.KEEPALIVE.type) {

        } else if (action == MsgActionEnum.PULL_FRIEND.type) {

        }
        /**
         * 2.1 当websocket 第一次open的时候，初始化channel，把用channel和user联系起来
         * 2.2 聊天类型的消息，把聊天记录保存到数据库中，同时标记消息的签收状态[未签收]
         * 2.3 签收消息类型，针对具体消息进行签收，修改数据库中对应消息的签收状态[已签收]
         * 2.4 心跳类型的消息
         */
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clents.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端移除断开，channel对应的长Id:" + ctx.channel().id().asLongText());
        System.err.println("客户端移除断开，channel对应的短Id:" + ctx.channel().id().asShortText());
        clents.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生了异常后要关闭连接，同时从channelgroup中移除
        ctx.channel().close();
        clents.remove(ctx.channel());
    }
}
