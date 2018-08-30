package com.example.dlimagecoder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.netmodel.ImgProcessResult;
import com.example.dlimagecoder.util.FileUtil;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.PictureUtil;
import com.example.dlimagecoder.util.ToastUtil;
import com.example.dlimagecoder.util.Tool;

import java.util.List;

import rx.functions.Action1;

import static com.example.dlimagecoder.util.NetUtil.IMAGE_HOST;

public class EditActivity extends BaseActivity {

    private ImageView image;

    private String currentImageLocalPath;
    private String currentImageNetString;
    private String currentImageResizePath;
    private String currentLocalProcessPath;//已保存到的已处理的本地路径
    private String currentProcessImagePath;//处理过的当前图片网络路径
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
        currentImageLocalPath = intent.getStringExtra(CameraActivity.IMAGE_PATH);
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
        if (currentImageLocalPath !=null)
            Glide.with(this).load(currentImageLocalPath).into(image);

    }

    //下一步
    public void next(View view){
        if (!currentProcessImagePath.isEmpty()){
            images.add(currentProcessImagePath);
        } else if (!currentImageNetString.isEmpty()){
            images.add(currentImageNetString);
        } else if (!currentImageLocalPath.isEmpty()){
            images.add(currentImageLocalPath);
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
        images.add(currentImageLocalPath);
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
                process(7);
                break;
            case R.id.btnPro6:
                process(8);
                break;
            case R.id.btnPro7:
                process(9);
                break;
            case R.id.btnPro8:
                process(10);
                break;
            case R.id.btnPro9:
                process(11);
                break;
        }
    }

    public void back(View v){
        onBackPressed();
    }

    public void store(View v){
        if (currentImageNetString==null){
            ToastUtil.showToast("图片尚未处理");
            return;
        }
        if (currentLocalProcessPath!=null){
            ToastUtil.showToast("图片已保存");
            return;
        }
        dialog.show();
        final String s = currentProcessImagePath;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = NetUtil.download(s);
                if (bitmap !=null){
                    final String path  = FileUtil.storeNetImage(bitmap);
                    currentLocalProcessPath = path;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast("照片已保存到："+ path);
                        }
                    });
                }
                dialog.dismiss();
            }
        }).start();
    }

    public void process(final int type){

        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = currentImageNetString==null ?
                        currentImageNetString=NetUtil.uploadImage(PictureUtil.resize256Img(currentImageLocalPath)) : currentImageNetString;
                Log.v("zy",s);
                NetUtil.getAppUrl().processImg(s,type)
                        .subscribe(new Action1<ImgProcessResult>() {
                            @Override
                            public void call(final ImgProcessResult netResult) {
                                if (netResult.isSuccessful()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentProcessImagePath = netResult.getUrl();
                                            Glide.with(EditActivity.this)
                                                    .load(currentProcessImagePath)
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