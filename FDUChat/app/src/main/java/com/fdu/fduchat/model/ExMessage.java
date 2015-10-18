package com.fdu.fduchat.model;

public class ExMessage extends Message{
    private boolean isMe;

    public boolean getIsMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    public ExMessage(String sender, String timestamp, MessageContent content, String receiver, boolean isMe) {
        super(sender, timestamp, content, receiver);
        this.isMe = isMe;
    }

    public ExMessage() {

    }

    public ExMessage(Message m, boolean isMe) {
        setContent(m.getContent());
        setSender(m.getSender());
        setReceiver(m.getReceiver());
        setTimestamp(m.getTimestamp());
        setIsMe(isMe);
    }

    @Override
    public String toString() {
        return "ExMessage{" +
                "isMe=" + isMe +
                '}';
    }
}

