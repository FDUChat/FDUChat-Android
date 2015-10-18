package com.fdu.fduchat.model;

import com.fdu.fduchat.backend.InfoReceiver;

public class Message {
    private String sender;
    private String timestamp;
    private MessageContent content;
    private String receiver;

    public Message() {

    }

    public Message(InfoReceiver.InfoExtras e) {
        setSender(e.getSender());
        setTimestamp(e.getTimestamp());
        setReceiver(e.getReceiver());
    }

    public Message(String sender, String timestamp, MessageContent content, String receiver) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.content = content;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", content=" + content +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
