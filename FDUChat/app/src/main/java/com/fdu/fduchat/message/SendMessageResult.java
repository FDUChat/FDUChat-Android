package com.fdu.fduchat.message;

/**
 * Created by HurricaneTong on 15/10/18.
 */
public class SendMessageResult {
    private String sendno;
    private String msg_id;

    public SendMessageResult(String sendno, String msg_id) {
        this.sendno = sendno;
        this.msg_id = msg_id;
    }

    public SendMessageResult() {

    }

    public String getSendno() {
        return sendno;
    }

    public void setSendno(String sendno) {
        this.sendno = sendno;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    @Override
    public String toString() {
        return "SendMessageResult{" +
                "sendno='" + sendno + '\'' +
                ", msg_id='" + msg_id + '\'' +
                '}';
    }
}
