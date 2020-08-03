package com.mohuangnpc.imserver.entity;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户和channel关联处理
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/27 7:59
 */
public class UserChannelRel {
    private static HashMap<String, Channel> map = new HashMap<>();
    private static HashMap<String, Map<String,ChatMsg>> chatMap = new HashMap<>();
    public static void put(String senderId,Channel channel){
        map.put(senderId,channel);
    }

    public static Channel get(String senderId){
        return map.get(senderId);
    }
    public static void putChat(String senderId,Map<String,ChatMsg> channel){
        chatMap.put(senderId,channel);
    }

    public static Map<String,ChatMsg> getChat(String senderId){
        return chatMap.get(senderId);
    }
}
