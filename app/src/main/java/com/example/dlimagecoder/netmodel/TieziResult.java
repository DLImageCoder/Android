package com.example.dlimagecoder.netmodel;

import java.util.List;

import static com.example.dlimagecoder.netmodel.NetResult.SUCCESSFUL;

public class TieziResult {
    private int status;
    private List<Tiezi> moments;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Tiezi> getMoments() {
        return moments;
    }

    public void setMoments(List<Tiezi> moments) {
        this.moments = moments;
    }

    public boolean isSuccessful(){
        return status == SUCCESSFUL ;
    }

}
