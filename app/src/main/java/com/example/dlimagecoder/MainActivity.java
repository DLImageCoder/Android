package com.example.dlimagecoder;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.util.Tool;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private FloatingActionButton cameraBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout loadingMoreRL;
    private RecyclerView rv;

    private final int REQUEST_PER = 1;
    private boolean loading = false;


    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        cameraBtn=findViewById(R.id.btn_camera);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        loadingMoreRL = findViewById(R.id.rl_loading);
        rv = findViewById(R.id.rv);
    }

    @Override
    protected void initEvent() {
        checkMPermission();

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (loading){//防止同时多次调用
                    return;
                }
                if (Tool.isSlideToBottom(recyclerView)) {
                    loadMore();
                }
            }
        });
    }

    //底部加载
    private void loadMore() {
        loadingMoreRL.setVisibility(View.VISIBLE);
    }

    //更新列表
    private void refresh() {
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkMPermission() {
        List<String> permissions = new ArrayList<>();
        if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.CAMERA);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()){
            requestPermissions(permissions.toArray(new String[permissions.size()]),REQUEST_PER);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int permit:grantResults){
            if (permit!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"请同意所有权限",Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }
    }
}
