package com.example.dlimagecoder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.common.Constrants;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;

import rx.functions.Action1;

public class RegisterActivity extends BaseActivity {

    private TextInputEditText etId,etPwd;

    private SharedPreferences preferences;


    @Override
    protected int getResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initVariable() {
        preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    @Override
    protected void initView() {
        etId = findViewById(R.id.et_id);
        etPwd = findViewById(R.id.et_pwd);

    }

    @Override
    protected void initEvent() {
    }

    public void register(View v){
        final String id = etId.getText().toString();
        final String pwd = etPwd.getText().toString();
        if (id.isEmpty()||pwd.isEmpty()){
            ToastUtil.showToast("账号密码不能为空");
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NetUtil.getAppUrl().register(id,pwd).subscribe(new Action1<NetResult>() {
                            @Override
                            public void call(NetResult netResult) {
                                if (netResult.isSuccessful()){
                                    storeAccount();
                                    startActivity(new Intent(RegisterActivity.this,UserInfoActivity.class));
                                    finish();
                                }
                            }
                        });
                    } catch (Exception e){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast("网络故障");
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void storeAccount() {
        String id = etId.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        NetUtil.id = id;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constrants.ID, id);
        editor.putString(Constrants.PASSWORD, pwd);
        editor.putBoolean(LoginActivity.IS_LOGIN, true);
        editor.commit();
    }
}
