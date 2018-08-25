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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.adapter.TieziAdapter;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.Tiezi;
import com.example.dlimagecoder.netmodel.TieziResult;
import com.example.dlimagecoder.netmodel.UserInfo;
import com.example.dlimagecoder.netmodel.UserInfoResult;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.example.dlimagecoder.util.Tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    private FloatingActionButton cameraBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout loadingMoreRL;
    private RecyclerView rv;
    private ImageView ivHead;

    private final int REQUEST_PER = 1;
    private boolean loading = false;
    private List<Tiezi> list;
    private TieziAdapter adapter;
    private UserInfo userInfo;
    private boolean end = false;//没有更多数据了
    private static final int PAGE_SIZE = 5;

    private boolean quit = false;


    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        list = new ArrayList<>();
        adapter = new TieziAdapter(this, list, new Handler());
        adapter.setLisenter(new TieziAdapter.OnItenClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Tiezi tiezi = list.get(position);
                Intent intent = new Intent(MainActivity.this, TieziDetailActivity.class);
                intent.putExtra("tiezi", Tool.geneTiezi(tiezi));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initView() {
        cameraBtn = findViewById(R.id.btn_camera);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        loadingMoreRL = findViewById(R.id.rl_loading);
        ivHead = findViewById(R.id.ivHead);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        checkMPermission();

        refresh();

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
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
                if (loading) {//防止同时多次调用
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
        if (list.isEmpty() || end) {
            ToastUtil.showToast("已经到底了");
            return;
        }
        loadingMoreRL.setVisibility(View.VISIBLE);
        loading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NetUtil.getAppUrl().getTiezi(list.get(list.size() - 1).getMomentId())
                            .subscribe(new Action1<TieziResult>() {
                                @Override
                                public void call(TieziResult netResult) {
                                    // TODO:
                                    List<Tiezi> list1 = netResult.getMoments();
                                    final int start = list.size();
                                    list.addAll(list1);
                                    if (list1.size() < PAGE_SIZE) {
                                        end = true;
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingMoreRL.setVisibility(View.GONE);
                                            adapter.notifyItemRangeChanged(start, list.size() - 1);
                                        }
                                    });
                                }
                            });
                    loading = false;
                } catch (Exception e) {
                    loading = false;
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

    //更新列表
    private void refresh() {
        end = false;
        loading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NetUtil.getAppUrl().getTiezi(-1)
                            .subscribe(new Action1<TieziResult>() {
                                @Override
                                public void call(TieziResult netResult) {
                                    if (netResult.isSuccessful()) {
                                        // TODO:
                                        List<Tiezi> list1 = netResult.getMoments();
                                        list.clear();
                                        list.addAll(list1);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                                swipeRefreshLayout.setRefreshing(false);
                                            }
                                        });
                                    }
                                }
                            });
                    loading = false;
                } catch (Exception e) {
                    loading = false;
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

    @TargetApi(Build.VERSION_CODES.M)
    private void checkMPermission() {
        List<String> permissions = new ArrayList<>();
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PER);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int permit : grantResults) {
            if (permit != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "请同意所有权限", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }
    }

    //注销
    public void quit(View v) {
        new AlertDialog.Builder(this)
                .setMessage("确定注销账号吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(LoginActivity.IS_LOGIN, false);
                        editor.commit();
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();

    }

    //加载个人信息
    public void requestUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtil.getAppUrl().getUsrInfo(NetUtil.id)
                        .subscribe(new Action1<UserInfoResult>() {
                            @Override
                            public void call(UserInfoResult userInfoResult) {
                                if (userInfoResult.isSuccessful()) {
                                    userInfo = userInfoResult.getUserInfo();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(userInfo.getHead()!=null){
                                                Glide.with(MainActivity.this)
                                                        .load(userInfo.getHead())
                                                        .into(ivHead);
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        }).start();
    }

    public void head(View v) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra("info", userInfo);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (quit) {
            super.onBackPressed();
            return;
        } else {
            quit = true;
            ToastUtil.showToast("再按一次退出");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                quit = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestUserInfo();
    }
}
