package com.example.dlimagecoder.util;

import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Tool {

    //判断列表是否滑到底部
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public static String imagesToString(List<String> imagePaths){
        return new Gson().toJson(imagePaths);
    }

    public static List<String> getImagesFromString(String json){
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(json,listType);
    }
}
