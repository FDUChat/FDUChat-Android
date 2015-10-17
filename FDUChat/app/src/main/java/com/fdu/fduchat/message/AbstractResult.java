package com.fdu.fduchat.message;

/**
 * Created by HurricaneTong on 15/10/17.
 */
public class AbstractResult {
    public Integer id;
    public String content;
    public Boolean isSuccessful() {
        return id.toString().startsWith("2");
    }
}
