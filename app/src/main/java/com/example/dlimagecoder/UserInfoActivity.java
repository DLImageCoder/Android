package com.example.dlimagecoder;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;

import rx.functions.Action1;

public class UserInfoActivity extends BaseActivity {

    private TextInputEditText nameEt,ageEt;
    private ImageView headIv;

    private String name,head;
    private int age;

    @Override
    protected int getResourceId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initView() {
        nameEt  =findViewById(R.id.et_name);
        ageEt = findViewById(R.id.et_age);
    }

    @Override
    protected void initEvent() {

    }

    //不必检查
    public void post(View v){
        name = nameEt.getText().toString();
        age = Integer.parseInt(ageEt.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtil.getAppUrl().setInfo(NetUtil.id,name,age,"男",head)
                        .subscribe(new Action1<NetResult>() {
                            @Override
                            public void call(NetResult netResult) {
                                if (netResult.isSuccessful()){
                                    startActivity(new Intent(UserInfoActivity.this,MainActivity.class));
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.showToast("提交失败");
                                        }
                                    });
                                }
                            }
                        });
            }
        }).start();
    }
}
