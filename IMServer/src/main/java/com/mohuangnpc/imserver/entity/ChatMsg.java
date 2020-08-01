package com.mohuangnpc.imserver.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/26 16:02
 */
public class ChatMsg implements Serializable {
    //发送者id
    private String senderId;
    //接收者id
    private String receiverId;
    //聊天内容
    private String msg;
    //消息id 用于消息的签收
    private String msgId;
    //创建时间
    private Date createTme;
    //消息标识
    private Integer signFlag;

    public Date getCreateTme() {
        return createTme;
    }

    public void setCreateTme(Date createTme) {
        this.createTme = createTme;
    }

    public Integer getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
