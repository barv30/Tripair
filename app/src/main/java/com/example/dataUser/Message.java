package com.example.dataUser;

import java.io.Serializable;

public class Message implements Serializable {
    private String senderName;
    private String senderId;
    private String content;
    private String date;
    private String msgNum;

    public Message(){}
    public Message(
            String senderName,
            String senderId,
            String content,
            String date,
            String msgNum)
    {
        this.senderName=senderName;
        this.senderId=senderId;
        this.content=content;
        this.date=date;
        this.msgNum=msgNum;
    }

    public String getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(String msgNum) {
        this.msgNum = msgNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

