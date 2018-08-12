package com.example.dlimagecoder;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dlimagecoder.adapter.TieziAdapter;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.common.Constrants;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.netmodel.Tiezi;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.Tool;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    private FloatingActionButton cameraBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout loadingMoreRL;
    private RecyclerView rv;

    private final int REQUEST_PER = 1;
    private boolean loading = false;
    private List<Tiezi> list;
    private TieziAdapter adapter;
    private boolean end = false;//没有更多数据了


    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        list = new ArrayList<>();
        adapter = new TieziAdapter(this,list);
        adapter.setLisenter(new TieziAdapter.OnItenClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Tiezi tiezi = list.get(position);
                Intent intent = new Intent(MainActivity.this,TieziDetailActivity.class);
                intent.putExtra("tiezi",tiezi);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initView() {
        cameraBtn=findViewById(R.id.btn_camera);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        loadingMoreRL = findViewById(R.id.rl_loading);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
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
                if (Tool.isSlideToBottom(recyclerView)&&!end) {
                    loadMore();
                }
            }
        });
    }

    //底部加载
    private void loadMore() {
        if (list.isEmpty()){
            return;
        }
        loadingMoreRL.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtil.getAppUrl().getTiezi(Integer.parseInt(Constrants.ID),list.get(list.size()-1).getId())
                        .subscribe(new Action1<NetResult>() {
                            @Override
                            public void call(NetResult netResult) {
                                // TODO:
                                List<Tiezi> list1 = new ArrayList<>();
                                final int start = list.size();
                                list.addAll(list1);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingMoreRL.setVisibility(View.GONE);
                                        adapter.notifyItemRangeChanged(start,list.size()-1);
                                    }
                                });
                            }
                        });
            }
        }).start();
    }

    //更新列表
    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtil.getAppUrl().getTiezi(Integer.parseInt(Constrants.ID),-1)
                        .subscribe(new Action1<NetResult>() {
                            @Override
                            public void call(NetResult netResult) {
                                // TODO:
                                List<Tiezi> list1 = new ArrayList<>();
                                list.clear();
                                list.addAll(list1);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
            }
        }).start();
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

    //注销
    public void quit(View v){
        new AlertDialog.Builder(this)
                .setMessage("确定注销账号吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(LoginActivity.IS_LOGIN, false);
                        editor.commit();
                        finish();
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();

    }
}
