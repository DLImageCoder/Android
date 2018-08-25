package com.example.dlimagecoder.netmodel;

import java.io.Serializable;

public class Comment implements Serializable {
    private String text;
    private String time;
    private int uid;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Comment(String text, String time, int uid) {
        this.text = text;
        this.time = time;
        this.uid = uid;
    }
}
