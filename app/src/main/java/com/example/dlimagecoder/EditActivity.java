package com.example.dlimagecoder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;

public class EditActivity extends BaseActivity {

    private ImageView image;

    private String imagePath;
    private Bitmap currentBitmap;

    @Override
    protected int getResourceId() {
        return R.layout.activity_edit;
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

    public void next(View view){
        Intent intent = new Intent(this,PreviewActivity.class);
        intent.putExtra(CameraActivity.IMAGE_PATH,imagePath);
        startActivity(intent);
    }
}
