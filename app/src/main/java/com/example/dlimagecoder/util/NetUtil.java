package com.example.dlimagecoder.util;

import android.app.Application;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class NetUtil {

    private static NetUtil mInstance;
    private static OkHttpClient mOkHttpClient;
    private static AppUrl appUrl;

    public static String id;
    public static String pwd;

    private static final String HOST = "";

    private NetUtil() {
        mOkHttpClient = new OkHttpClient();
    }

    public static NetUtil getInstance() {
        if (mInstance == null) {
            synchronized (NetUtil.class) {
                if (mInstance == null) {
                    mInstance = new NetUtil();
                }
            }
        }
        return mInstance;
    }

    public static AppUrl getAppUrl(){
        if (appUrl==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(NetUtil.HOST)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            appUrl = retrofit.create(AppUrl.class);
        }
        return appUrl;
    }
}
