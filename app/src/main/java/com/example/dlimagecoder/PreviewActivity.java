package com.example.dlimagecoder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.example.dlimagecoder.util.Tool;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class PreviewActivity extends BaseActivity {

    private String images;
    private Bitmap bitmap;
    private volatile int uploadImages;

    private ImageView image1,image2,image3;
    private EditText et;

    @Override
    protected int getResourceId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initVariable() {
        images = getIntent().getStringExtra(CameraActivity.IMAGES);
    }

    @Override
    protected void initView() {
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        et = findViewById(R.id.et);
    }

    @Override
    protected void initEvent() {
        List<String> paths = Tool.str2List(images);
        ImageView[] imageViews = {image1,image2,image3};
        if (!paths.isEmpty()){
            for (int i=0;i<paths.size();i++){
                String str = paths.get(i);
                Glide.with(this).load(str).into(imageViews[i]);
            }
        }

    }

    public void post(View view){
        final String text = et.getText().toString();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("上传中");
        dialog.setCancelable(false);
        dialog.show();
        List<String> paths = Tool.str2List(images);
        uploadImages = paths.size();
        final List<String> imageUrls = new ArrayList<>();
        for (String path : paths){
            final String p = path;
            if (p.contains("http")){
                //已经是网络路径
                imageUrls.add(p);
                uploadImages--;
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path= NetUtil.uploadImage(p);
                        imageUrls.add(path);
                        uploadImages--;
                    }
                }).start();
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (uploadImages>0){
                    try {
                        Log.v("zy","up"+uploadImages);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                NetUtil.getAppUrl().post(NetUtil.id,text,Tool.imagesToString(imageUrls))
                        .subscribe(new Action1<NetResult>() {
                            @Override
                            public void call(final NetResult netResult) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (netResult.isSuccessful()){
                                            dialog.cancel();
                                            ToastUtil.showToast("发表成功");
                                            startActivity(new Intent(PreviewActivity.this,MainActivity.class));
                                        } else {
                                            ToastUtil.showToast("发表失败");
                                        }
                                    }
                                });
                            }
                        });
                if (dialog.isShowing())
                    dialog.cancel();
            }
        }).start();
    }

    public void back(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("确定要放弃编辑吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
        // super.onBackPressed();
    }
}