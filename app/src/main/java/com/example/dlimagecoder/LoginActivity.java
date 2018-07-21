package com.example.dlimagecoder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.common.Constrants;
import com.example.dlimagecoder.util.ToastUtil;

public class LoginActivity extends BaseActivity {

    private final String IS_LOGIN ="is_login";

    private TextInputEditText idEt;
    private TextInputEditText pwdEt;
    private Button loginPwd;
    private SharedPreferences preferences;


    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariable() {
        preferences=getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    protected void initView() {
        idEt=findViewById(R.id.et_id);
        pwdEt=findViewById(R.id.et_pwd);
        loginPwd=findViewById(R.id.btn_login);


    }

    @Override
    protected void initEvent() {
        checkLogin();

        loginPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=idEt.getText().toString().trim();
                String pwd=pwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(id)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this,"账号密码不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    login(listener);
                }
            }
        });
    }

    private void checkLogin() {
        if (preferences.getBoolean(IS_LOGIN,false)){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void login(LoginListener listener) {
        listener.onSuccess();
    }



    private interface LoginListener{
        void onSuccess();

        void onFailed();
    }

    private LoginListener listener = new LoginListener() {
        @Override
        public void onSuccess() {
            ToastUtil.showToast("登陆成功");
            storeAccount();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        @Override
        public void onFailed() {
            ToastUtil.showToast("登陆失败");
        }
    };

    private void storeAccount() {
        String id=idEt.getText().toString().trim();
        String pwd=pwdEt.getText().toString().trim();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Constrants.ID,id);
        editor.putString(Constrants.PASSWORD,pwd);
        editor.commit();
    }

}
