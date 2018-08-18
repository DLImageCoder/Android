package com.example.dlimagecoder.netmodel;

import static com.example.dlimagecoder.netmodel.NetResult.SUCCESSFUL;

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

    public boolean isSuccessful(){
        return Integer.parseInt(status) == SUCCESSFUL ;
    }

}
