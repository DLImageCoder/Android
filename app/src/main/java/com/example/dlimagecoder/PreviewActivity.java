package com.example.dlimagecoder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;

public class PreviewActivity extends BaseActivity {

    private String imagePath;
    private Bitmap bitmap;

    private ImageView image;

    @Override
    protected int getResourceId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initVariable() {
        imagePath = getIntent().getStringExtra(CameraActivity.IMAGE_PATH);
    }

    @Override
    protected void initView() {
        image = findViewById(R.id.image);
    }

    @Override
    protected void initEvent() {
        Glide.with(this).load(imagePath).into(image);
    }

    public void post(View view){

    }
}
