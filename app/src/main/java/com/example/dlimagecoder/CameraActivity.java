package com.example.dlimagecoder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.dlimagecoder.base.BaseActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraActivity extends BaseActivity {

    private SurfaceView sv;

    private SurfaceHolder holder;
    private Camera camera;

    public final static String IMAGE_PATH = "image_path";

    @Override
    protected int getResourceId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        sv=findViewById(R.id.sv);
    }

    @Override
    protected void initEvent() {
        holder = sv.getHolder();
        holder.setKeepScreenOn(true);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new TakePictureSurfaceCallback());
        findViewById(R.id.btn_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null,null,null,new TakePictureCallback());
            }
        });
    }

    private class TakePictureSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            camera = Camera.open();
            try {
                camera.setPreviewDisplay(surfaceHolder);//绑定
            } catch (IOException e) {
                e.printStackTrace();
            }
            Camera.Parameters params = camera.getParameters();
            params.setJpegQuality(100);//照片质量
            params.setPictureSize(1024, 768);//图片分辨率
            params.setPreviewFrameRate(5);//预览帧率

            camera.setDisplayOrientation(90);
            camera.startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    public void pictures(View view){
        PictureSelector.create(this)
                .openGallery(PictureConfig.TYPE_IMAGE)
                .maxSelectNum(1)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==PictureConfig.CHOOSE_REQUEST){
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            String path=selectList.get(0).getPath();
            Intent intent = new Intent(CameraActivity.this,EditActivity.class);
            intent.putExtra(IMAGE_PATH,path);
            startActivity(intent);
        }
    }

    private class TakePictureCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
            byte[] tempData = os.toByteArray();
            if (tempData != null && tempData.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(tempData, 0, tempData.length);
                Intent intent  = new Intent(CameraActivity.this,EditActivity.class);
                intent.putExtra(IMAGE_PATH,bitmap);
                startActivity(intent);
            }
        }
    }
}
