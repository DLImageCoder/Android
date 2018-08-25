package com.example.dlimagecoder.util;

import com.example.dlimagecoder.netmodel.ImgProcessResult;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.netmodel.TieziResult;
import com.example.dlimagecoder.netmodel.UserInfoResult;

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

    //获取个人信息
    @GET("/user/getInfo")
    Observable<UserInfoResult> getUsrInfo(@Query("userId") String userId);


    //提交个人信息
    @GET("user/setInfo")
    Observable<NetResult> setInfo
        (@Query("userId") String userId, @Query("name") String name
                ,@Query("age") int age,@Query("sex") String sex,@Query("head") String headUrl);


    //发帖
    @FormUrlEncoded
    @POST("moment/sendMoment")
    Observable<NetResult> post(@Field("userId") String userId,
                                    @Field("text") String text, @Field("imgs") String imgs);

    //获取帖子,page其实是当前帖子的最小id
    @GET("moment/getMoment")
    Observable<TieziResult> getTiezi
    (@Query("mid") int mid);


    //处理图片
    @GET("img/imgProcess")
    Observable<ImgProcessResult> processImg
    (@Query("url") String url, @Query("type") int type);

    //点赞
    @GET("moment/like")
    Observable<NetResult> approval
    (@Query("momentId") int commentId, @Query("userId") int userId,@Query("type") int type);

    //评论
    @GET("moment/comment")
    Observable<NetResult> comment
    (@Query("momentId") int momentId, @Query("text") String text,@Query("userId") int userId,@Query("type") int type);

}
