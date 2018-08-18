package com.example.dlimagecoder.netmodel;

import static com.example.dlimagecoder.netmodel.NetResult.SUCCESSFUL;

public class ImgProcessResult {

    private int status;
    private String url;

    public ImgProcessResult(int status, String url) {
        this.status = status;
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSuccessful(){
        return status == SUCCESSFUL ;
    }

}
