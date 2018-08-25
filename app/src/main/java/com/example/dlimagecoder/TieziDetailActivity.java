package com.example.dlimagecoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.lisenter.UserInfoLisenter;
import com.example.dlimagecoder.netmodel.Comment;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.netmodel.SerTiezi;
import com.example.dlimagecoder.netmodel.Tiezi;
import com.example.dlimagecoder.netmodel.UserInfo;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.example.dlimagecoder.util.Tool;

import java.util.List;

import rx.functions.Action1;

public class TieziDetailActivity extends BaseActivity {

    TextView tvContent,tvApproval,tvTime,tvComment,tvName;
    ImageView ivHead,ivComment,iv1,iv2,iv3;
    EditText et;
    
    private Tiezi tiezi;

    @Override
    protected int getResourceId() {
        return R.layout.activity_tiezi_detail;
    }

    @Override
    protected void initVariable() {
        SerTiezi ti = (SerTiezi) getIntent().getSerializableExtra("tiezi");
        tiezi = Tool.getTiezi(ti);
    }

    @Override
    protected void initView() {
        tvName = findViewById(R.id.tvName);
        tvApproval= (TextView) findViewById(R.id.tvApproval);
        tvContent= (TextView) findViewById(R.id.tvContent);
        tvTime= (TextView) findViewById(R.id.tvTime);
        tvComment = findViewById(R.id.tvComment);
        ivHead = findViewById(R.id.ivHead);
        ivComment = findViewById(R.id.ivComment);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        et = findViewById(R.id.et);

        ImageView[] ivs = {iv1,iv2,iv3};
        List<String> pics = tiezi.getPics();
        for (int i=0;i<pics.size();i++){
            Glide.with(this).load(pics.get(i)).into(ivs[i]);
        }
        tvContent.setText(tiezi.getText());
        StringBuilder comment = new StringBuilder();
        for (Comment c:tiezi.getCommentList()){
            comment.append(c.getName()+" : "+c.getText()+"          "+c.getTime()+"\n");
        }
        tvComment.setText(comment);
        tvTime.setText(tiezi.getTime());
    }

    @Override
    protected void initEvent() {
        NetUtil.getUserInfo(String.valueOf(tiezi.getUserId()), new UserInfoLisenter() {
            @Override
            public void process(final UserInfo info) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(info.getHead()))
                             Glide.with(TieziDetailActivity.this).load(info.getHead()).into(ivHead);
                        tvName.setText(info.getName());
                    }
                });
            }

            @Override
            public void fail() {

            }
        });
    }

    public void back(View v){
        onBackPressed();
    }

    public void commentAppend(){
        String str = et.getText().toString();
        String s = "我"+" : "+str+"          "+"刚刚"+"\n";
        tvComment.append(s);
    }

    public void comment(View v){
        final String s = et.getText().toString();
        if (TextUtils.isEmpty(s)){
            ToastUtil.showToast("输入不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtil.getAppUrl().comment(tiezi.getMomentId(),s, Integer.parseInt(NetUtil.id),0)
                        .subscribe(new Action1<NetResult>() {
                            @Override
                            public void call(final NetResult netResult) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (netResult.isSuccessful()){
                                            ToastUtil.showToast("评论成功");
                                            commentAppend();
                                            et.setText("");
                                        } else {
                                            ToastUtil.showToast("评论失败");
                                        }
                                    }
                                });
                            }
                        });
            }
        }).start();
    }
}
