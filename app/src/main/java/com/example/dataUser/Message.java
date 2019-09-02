package com.example.dataUser;

import java.io.Serializable;

public class Message implements Serializable {
    private String senderName;
    private String senderId;
    private String content;

    public Message(){}
    public Message(
            String senderName,
            String senderId,
            String content)
    {
        this.senderName=senderName;
        this.senderId=senderId;
        this.content=content;
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

