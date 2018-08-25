package com.example.dlimagecoder.netmodel;

import java.io.Serializable;

public class SerTiezi implements Serializable {
    private int id;
    private int authorId;
    private String text;
    private String time;
    private String appro;
    private String comment;
    private String picsText;

    public SerTiezi(int id, int authorId, String text, String time, String appro, String comment, String picsText) {
        this.id = id;
        this.authorId = authorId;
        this.text = text;
        this.time = time;
        this.appro = appro;
        this.comment = comment;
        this.picsText = picsText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAppro() {
        return appro;
    }

    public void setAppro(String appro) {
        this.appro = appro;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPicsText() {
        return picsText;
    }

    public void setPicsText(String picsText) {
        this.picsText = picsText;
    }
}
