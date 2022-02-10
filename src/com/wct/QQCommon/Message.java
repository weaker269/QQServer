package com.wct.QQCommon;

import java.io.Serializable;

/**
 * @author WenCT
 * 表示客户端和服务器端通信时的消息对象
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sender; //发送方
    private String receiver;//接收方
    private String content;//内容
    private String sendTime;//发送时间
    private String messageType;//消息类型

    public Message(String sender, String receiver, String content, String sendTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
