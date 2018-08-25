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
    private String likes;
    private String comments;
    private String imgs;
    private List<String> approUsers;
    private List<String> pics;
    private List<Comment> commentList;
    private int approNum;//点赞数
    private int commentNum;//评论数
    private boolean isApproval;

    public Tiezi(int momentId, int userId, String text, String time, String likes, String comment, String imgs) {
        this.momentId = momentId;
        this.userId = userId;
        this.text = text;
        this.time = time;
        this.likes = likes;
        this.comments = comment;
        this.imgs = imgs;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCommentNum() {
        return getCommentList().size();
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

    public boolean isApproval() {
        return getApproUsers().contains(NetUtil.id);
    }

    public void setApproval(boolean approval) {
        isApproval = approval;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getApproNum() {
        approNum = getApproUsers().size();
        return approNum;
    }

    public void setApproNum(int approNum) {
        this.approNum = approNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getApproUsers() {
        if (approUsers == null){
            approUsers = Tool.str2List(likes);
        }
        return approUsers;
    }

    public void setApproUsers(List<String> approUsers) {
        this.approUsers = approUsers;
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
