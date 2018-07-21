package com.example.dlimagecoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

public class CameraActivity extends AppCompatActivity {

    private SurfaceView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();


    }

    private void initView() {
        sv=findViewById(R.id.sv);
    }
}
