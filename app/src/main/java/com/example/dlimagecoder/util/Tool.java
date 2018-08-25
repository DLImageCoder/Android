package com.example.dlimagecoder.util;

import android.support.v7.widget.RecyclerView;

import com.example.dlimagecoder.netmodel.SerTiezi;
import com.example.dlimagecoder.netmodel.Tiezi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
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


    //解析出图片地址数组
    public static List<String> str2List(String json){
        Type listType = new TypeToken<List<String>>(){}.getType();
        List<String> res =  new Gson().fromJson(json,listType);
        return res == null ? new ArrayList<String>() : res;
    }
    //UTF-8编码字符串
    public static String UTF8(String str){
        try {
            return URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static SerTiezi geneTiezi(Tiezi tiezi){
        return new SerTiezi(tiezi.getMomentId(),tiezi.getUserId(),tiezi.getText(),
                tiezi.getTime(),tiezi.getLikes(),tiezi.getHasLike(),tiezi.getComments(),tiezi.getImgs());
    }

    public static Tiezi getTiezi(SerTiezi tiezi){
        return new Tiezi(tiezi.getId(),tiezi.getAuthorId(),tiezi.getText(),
                tiezi.getTime(),tiezi.getAppro(),tiezi.getHasLike(),tiezi.getComment(),tiezi.getPicsText());
    }
}
