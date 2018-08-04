package com.example.dlimagecoder.netmodel;

public class UserInfoResult {
    private String status;
    private UserInfo userInfo;

    public UserInfoResult(String status, UserInfo userInfo) {
        this.status = status;
        this.userInfo = userInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
