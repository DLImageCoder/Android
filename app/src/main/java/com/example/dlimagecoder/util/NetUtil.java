package com.example.dlimagecoder.util;

import android.app.Application;
import android.util.Log;

import com.example.dlimagecoder.common.Constrants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String HOST = "http://47.106.140.199:8080/";
    private static final String IMAGE_HOST = "http://pcqi1922c.bkt.clouddn.com/";

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
        if (mOkHttpClient == null){
            mOkHttpClient = new OkHttpClient();
        }
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

    //返回url
    public static String uploadImage(String path){
        Auth auth = Auth.create(Constrants.ACCESS_KEY,Constrants.SECRET_KEY);
        String token = auth.uploadToken(Constrants.BUCKET);

        Configuration configuration = new Configuration(Zone.autoZone());
        UploadManager manager = new UploadManager(configuration);
        try {
            com.qiniu.http.Response response = manager.put(path,null,token);
            return urlFromJson(response.bodyString());
        } catch (QiniuException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String urlFromJson(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        return IMAGE_HOST+jsonObject.get("key").toString();
    }
}
