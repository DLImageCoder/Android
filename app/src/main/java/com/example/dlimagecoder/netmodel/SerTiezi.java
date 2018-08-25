package com.example.dlimagecoder.netmodel;

import java.io.Serializable;

public class SerTiezi implements Serializable {
    private int id;
    private int authorId;
    private String text;
    private String time;
    private int appro;
    private int hasLike;
    private String comment;
    private String picsText;

    public SerTiezi(int id, int authorId, String text, String time, int appro, int hasLike, String comment, String picsText) {
        this.id = id;
        this.authorId = authorId;
        this.text = text;
        this.time = time;
        this.appro = appro;
        this.hasLike = hasLike;
        this.comment = comment;
        this.picsText = picsText;
    }

    public int getAppro() {
        return appro;
    }

    public void setAppro(int appro) {
        this.appro = appro;
    }

    public int getHasLike() {
        return hasLike;
    }

    public void setHasLike(int hasLike) {
        this.hasLike = hasLike;
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
