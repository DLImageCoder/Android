package com.example.dlimagecoder.util;

import com.example.dlimagecoder.netmodel.NetResult;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface AppUrl {

    //登录
    @GET("user/login.jsp")
    Observable<NetResult> login(@Query("userId") String userId, @Query("userPwd") String userPwd);

    //注册
    @GET("user/register.jsp")
    Observable<NetResult> register(@Query("userId") String userId, @Query("userPwd") String userPwd);

    //提交个人信息
    @GET("user/setInfo")
    Observable<NetResult> setInfo
        (@Query("userId") String userId, @Query("name") String name
                ,@Query("age") int age,@Query("sex") String sex,@Query("head") String headUrl);


    //发帖
    @FormUrlEncoded
    @POST("moment/sendMoment")
    Observable<NetResult> post(@Field("userId") int userId,
                                    @Field("text") String text, @Field("imgs") String imgs);

    //获取帖子,page其实是当前帖子的最小id
    @GET("moment/getMoment")
    Observable<NetResult> getTiezi
    (@Query("userId") int userId, @Query("page") int page);

}
