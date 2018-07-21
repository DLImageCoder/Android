package com.example.dlimagecoder;

import android.app.Application;
import android.content.Context;

public class DLImageCoder extends Application {

    private static DLImageCoder instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static DLImageCoder  getInstance(){
        return instance;
    }


}
