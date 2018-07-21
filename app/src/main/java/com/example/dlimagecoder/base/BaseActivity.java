package com.example.dlimagecoder.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        initVariable();
        initView();
        initEvent();
    }

    protected abstract int getResourceId();

    protected abstract void initVariable();

    protected abstract void initView();

    protected abstract void initEvent();
}
