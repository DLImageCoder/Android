package com.example.dlimagecoder.netmodel;

public class NetResult {
    private String status;

    public NetResult(String status) {
        this.status = status;
    }

    private static final int SUCCESSFUL = 1;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccessful(){
        return Integer.parseInt(status) == SUCCESSFUL ;
    }
}
