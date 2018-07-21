package com.example.dlimagecoder.util;

import android.widget.Toast;

import com.example.dlimagecoder.DLImageCoder;

public class ToastUtil {

    public static void showToast(String text){
        Toast.makeText(DLImageCoder.getInstance().getApplicationContext()
            ,text,Toast.LENGTH_SHORT).show();
    }
}
