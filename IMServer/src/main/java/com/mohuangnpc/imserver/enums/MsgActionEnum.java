package com.mohuangnpc.imserver.enums;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/26 16:08
 */
public enum MsgActionEnum {
    CONNECT(1,"第一次初始化连接"),
    CHAT(2,"聊天信息"),
    SIGEND(3,"消息签收"),
    KEEPALIVE(4,"客户端保持心跳"),
    PULL_FRIEND(5,"拉去好友");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type,String content){
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
