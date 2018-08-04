package com.example.dlimagecoder;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;

public class RegisterActivity extends BaseActivity {

    private TextInputEditText etId,etPwd;

    @Override
    protected int getResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initVariable() {

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
        String id = etId.getText().toString();
        String pwd = etPwd.getText().toString();
        if (id.isEmpty()||pwd.isEmpty()){
            ToastUtil.showToast("账号密码不能为空");
        } else {
            //NetUtil.getAppUrl().
        }
    }
}
