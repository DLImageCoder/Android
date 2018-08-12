package com.example.dlimagecoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.Tiezi;

public class TieziDetailActivity extends BaseActivity {

    TextView tvContent,tvApproval,tvTime,tvComment;
    ImageView ivHead,ivComment,imageView1,imageView2,imageView3,imageView4,imageView5,ivLarge;
    
    private Tiezi tiezi;

    @Override
    protected int getResourceId() {
        return R.layout.activity_tiezi_detail;
    }

    @Override
    protected void initVariable() {
        tiezi = (Tiezi) getIntent().getSerializableExtra("tiezi");
    }

    @Override
    protected void initView() {
        tvApproval= (TextView) findViewById(R.id.tvApproval);
        tvContent= (TextView) findViewById(R.id.tvContent);
        tvTime= (TextView) findViewById(R.id.tvTime);
        tvComment = findViewById(R.id.tvComment);
        ivHead = findViewById(R.id.ivHead);
        ivComment = findViewById(R.id.ivComment);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        ivLarge = findViewById(R.id.ivLarge);
    }

    @Override
    protected void initEvent() {

    }
}
