package com.mohuangnpc.imserver.entity;

import java.io.Serializable;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/26 16:00
 */
public class DataContent implements Serializable {
    //动作类型
    private Integer action;
    //用户的聊天内容
    private ChatMsg chatMsg;
    //扩展字段
    private String extand;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
