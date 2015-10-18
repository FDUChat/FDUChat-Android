package com.fdu.fduchat.message;

public class AbstractResult {
    private Integer id;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "AbstractResult{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isSuccessful() {
        return id.toString().startsWith("2");
    }
}
