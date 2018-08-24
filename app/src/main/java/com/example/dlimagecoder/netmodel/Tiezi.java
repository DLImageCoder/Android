package com.example.dlimagecoder.netmodel;

import java.io.Serializable;
import java.util.List;

public class Tiezi implements Serializable{
    private int id;
    private String text;
    private List<String> approUsers;
    private List<String> pics;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getApproUsers() {
        return approUsers;
    }

    public void setApproUsers(List<String> approUsers) {
        this.approUsers = approUsers;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
