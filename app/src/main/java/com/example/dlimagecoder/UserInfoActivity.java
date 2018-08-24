package com.example.dlimagecoder;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.netmodel.UserInfo;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.example.dlimagecoder.util.Tool;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import rx.functions.Action1;

public class UserInfoActivity extends BaseActivity {

    private TextInputEditText nameEt,ageEt;
    private ImageView headIv;
    private UserInfo userInfo;

    private String name,head;
    private int age;

    @Override
    protected int getResourceId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initVariable() {
        userInfo = (UserInfo) getIntent().getSerializableExtra("info");
        if (userInfo!=null){
            Glide.with(this).load(userInfo.getHead()).into(headIv);
            nameEt.setText(userInfo.getName());
            ageEt.setText(userInfo.getAge());
        }
    }

    @Override
    protected void initView() {
        nameEt  =findViewById(R.id.et_name);
        ageEt = findViewById(R.id.et_age);

        headIv = findViewById(R.id.ivHead);
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
                if (head!=null){
                    head  = NetUtil.uploadImage(head);
                }
                try {
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

    public void head(View v){
        PictureSelector.create(this)
                .openGallery(PictureConfig.TYPE_IMAGE)
                .maxSelectNum(1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    String path=selectList.get(0).getPath();
                    head  =path;
                    Glide.with(UserInfoActivity.this).load(head).into(headIv);
                    break;
            }
        }
    }
}
