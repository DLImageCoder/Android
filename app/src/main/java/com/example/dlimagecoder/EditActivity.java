package com.example.dlimagecoder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.ImgProcessResult;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.example.dlimagecoder.util.Tool;

import java.util.List;

import rx.functions.Action1;

public class EditActivity extends BaseActivity {

    private ImageView image;

    private String currentImagePath;
    private Bitmap currentBitmap;
    private List<String> images;//已经处理好的图片

    private ProgressDialog dialog;

    @Override
    protected int getResourceId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initVariable() {
        Intent intent  =getIntent();
        currentImagePath = intent.getStringExtra(CameraActivity.IMAGE_PATH);
        images = Tool.str2List(intent.getStringExtra(CameraActivity.IMAGES));

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在处理中");
        dialog.setCancelable(false);
    }

    @Override
    protected void initView() {
        image = findViewById(R.id.image);
    }

    @Override
    protected void initEvent() {
        if (currentImagePath!=null)
            Glide.with(this).load(currentImagePath).into(image);

    }

    //下一步
    public void next(View view){
        if (!currentImagePath.isEmpty()){
            images.add(currentImagePath);
        }
        if (images.size()>0){
            Intent intent = new Intent(this,PreviewActivity.class);
            intent.putExtra(CameraActivity.IMAGES,Tool.imagesToString(images));
            startActivity(intent);
        } else {
            ToastUtil.showToast("帖子必须含有图片");
        }
    }

    //下一张
    public void nextImage(View v){
        if (images.size()>=2){
            ToastUtil.showToast("最多三张");
            return;
        }
        images.add(currentImagePath);
        Intent intent = new Intent();
        intent.putExtra(CameraActivity.IMAGES,Tool.imagesToString(images));
        setResult(RESULT_OK,intent);
        finish();
    }

    public void process(View v){
        switch (v.getId()){
            case R.id.btnPro1:
                process(0);
                break;
            case R.id.btnPro2:
                process(1);
                break;
            case R.id.btnPro3:
                process(2);
                break;
            case R.id.btnPro4:
                process(3);
                break;
            case R.id.btnPro5:
                process(5);
                break;
            case R.id.btnPro6:
                process(6);
                break;
            case R.id.btnPro7:
                process(7);
                break;
            case R.id.btnPro8:
                process(8);
                break;
        }
    }

    public void back(View v){
        onBackPressed();
    }

    public void store(View v){
        ToastUtil.showToast("照片已保存到："+currentImagePath);
    }

    public void process(final int type){
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = NetUtil.uploadImage(currentImagePath);
                Log.v("zy",s);
                NetUtil.getAppUrl().processImg(s,type)
                        .subscribe(new Action1<ImgProcessResult>() {
                            @Override
                            public void call(final ImgProcessResult netResult) {
                                if (netResult.isSuccessful()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentImagePath = netResult.getUrl();
                                            Glide.with(EditActivity.this)
                                                    .load(currentImagePath)
                                                    .into(image);
                                            dialog.dismiss();
                                            ToastUtil.showToast("处理成功");
                                        }
                                    });
                                } else {
                                    dialog.dismiss();
                                    ToastUtil.showToast("处理失败");
                                }
                            }
                        });
            }
        }).start();
    }


}
