package com.example.dlimagecoder.netmodel;

import java.io.Serializable;

public class Comment implements Serializable {
    private String text;
    private String time;
    private String name;

    public Comment(String text, String time, String name) {
        this.text = text;
        this.time = time;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
