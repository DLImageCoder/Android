package com.example.dlimagecoder.netmodel;

import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.Tool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tiezi implements Serializable{
    private int momentId;
    private int userId;
    private String text;
    private String time;
    private int likes;//点赞数
    private int hasLike;
    private String comments;
    private String imgs;
    private List<String> pics;
    private List<Comment> commentList;
    private int commentNum;//评论数

    public Tiezi(int momentId, int userId, String text, String time, int likes, int hasLike, String comments, String imgs) {
        this.momentId = momentId;
        this.userId = userId;
        this.text = text;
        this.time = time;
        this.likes = likes;
        this.hasLike = hasLike;
        this.comments = comments;
        this.imgs = imgs;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public int getCommentNum() {
        commentNum = getCommentList().size();
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comment) {
        this.comments = comment;
    }

    public List<Comment> getCommentList() {
        if (commentList ==null){
            Type listType = new TypeToken<List<Comment>>(){}.getType();
            List<Comment> res =  new Gson().fromJson(getComments(),listType);
            commentList = res;
        }
        return commentList == null? new ArrayList<Comment>() : commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public List<String> getPics() {
        if (pics ==null){
            pics = Tool.str2List(imgs);
        }
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }
}
