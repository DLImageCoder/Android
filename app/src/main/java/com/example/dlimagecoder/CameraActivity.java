package com.example.dlimagecoder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.dlimagecoder.base.BaseActivity;
import com.example.dlimagecoder.util.FileUtil;
import com.example.dlimagecoder.util.PictureUtil;
import com.example.dlimagecoder.util.Tool;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends BaseActivity {

    private SurfaceView sv;

    private SurfaceHolder holder;
    private Camera camera;
    private List<String> images;//已经处理完的图片

    public final static String IMAGE_PATH = "image_path";
    public final static String IMAGES = "images";
    private final static int EDIT = 21;

    @Override
    protected int getResourceId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initVariable() {
        images = new ArrayList<>();
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
            params.setPictureSize(1080, 1920);//图片分辨率
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
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    String path=selectList.get(0).getPath();
                    Intent intent = new Intent(CameraActivity.this,EditActivity.class);
                    intent.putExtra(IMAGE_PATH,path);
                    intent.putExtra(IMAGES, Tool.imagesToString(images));
                    startActivityForResult(intent,EDIT);
                    break;
                case EDIT:
                    images = Tool.getImagesFromString(data.getStringExtra(IMAGES));
                    break;
            }
        }
    }

    private class TakePictureCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
            //byte[] tempData = os.toByteArray();
            if (data != null && data.length > 0) {
                Bitmap bitmap = PictureUtil.rotateToDegrees(BitmapFactory.decodeByteArray(data, 0, data.length),90);
                Intent intent  = new Intent(CameraActivity.this,EditActivity.class);
                intent.putExtra(IMAGE_PATH, FileUtil.storeImage(bitmap));
                intent.putExtra(IMAGES, Tool.imagesToString(images));
                startActivityForResult(intent,EDIT);
            }
        }
    }
}
