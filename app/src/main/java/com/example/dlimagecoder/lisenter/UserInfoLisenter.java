package com.example.dlimagecoder.lisenter;

import com.example.dlimagecoder.netmodel.UserInfo;

public interface UserInfoLisenter {

    void process(UserInfo info);

    void fail();
}
