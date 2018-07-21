package com.example.dlimagecoder.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private SharedPreferences sharedPreferences;

    public SpUtil(SharedPreferences preferences){
        this.sharedPreferences=preferences;
    }

    public static SpUtil getSp(Context context,String name){
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        return new SpUtil(sharedPreferences);
    }


}
