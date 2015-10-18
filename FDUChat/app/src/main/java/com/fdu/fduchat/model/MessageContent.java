package com.fdu.fduchat.model;

public class MessageContent {
    public final static Integer TEXT_MESSAGE = 1;
    public final static Integer PICTURE_MESSAGE = 2;
    public final static Integer AUDIO_MESSAGE = 3;

    private Integer type;

    private String text;

    public MessageContent(Integer type, String text) {
        this.type = type;
        this.text = text;
    }

    public MessageContent() {

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MessageContent{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
