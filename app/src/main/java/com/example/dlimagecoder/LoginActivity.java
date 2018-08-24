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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.common.Constrants;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity {

    public static final String IS_LOGIN = "is_login";

    private TextInputEditText idEt;
    private TextInputEditText pwdEt;
    private Button loginPwd;
    private SharedPreferences preferences;
    private TextView registerTxt;


    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariable() {
        preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    @Override
    protected void initView() {
        idEt = findViewById(R.id.et_id);
        pwdEt = findViewById(R.id.et_pwd);
        loginPwd = findViewById(R.id.btn_login);
        registerTxt = findViewById(R.id.tv_register);

    }

    @Override
    protected void initEvent() {
        checkLogin();

        loginPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEt.getText().toString().trim();
                String pwd = pwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                login(listener);
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
        });

    }

    private void checkLogin() {
        if (preferences.getBoolean(IS_LOGIN, false)) {
            NetUtil.id = preferences.getString(Constrants.ID, null);
            NetUtil.pwd = preferences.getString(Constrants.PASSWORD, null);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void login(final LoginListener listener) {
        String id = idEt.getText().toString();
        String pwd = pwdEt.getText().toString();
        if (id.isEmpty() || pwd.isEmpty()) {
            ToastUtil.showToast("输入不能为空");
        }
        NetUtil.getAppUrl().login(id, pwd)
                .subscribe(new Action1<NetResult>() {
                    @Override
                    public void call(NetResult netResult) {
                        if (netResult.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed();
                        }
                    }
                });
    }


    private interface LoginListener {
        void onSuccess();

        void onFailed();
    }

    private LoginListener listener = new LoginListener() {
        @Override
        public void onSuccess() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast("登陆成功");
                    storeAccount();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });
        }

        @Override
        public void onFailed() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast("登陆失败");
                }
            });
        }
    };

    private void storeAccount() {
        String id = idEt.getText().toString().trim();
        String pwd = pwdEt.getText().toString().trim();
        NetUtil.id = id;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constrants.ID, id);
        editor.putString(Constrants.PASSWORD, pwd);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }


    public void register(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
