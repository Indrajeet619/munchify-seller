package com.bantczak.munchifyseller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.cameraview.CameraView;

public class CameraActivity extends AppCompatActivity {

    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);
        cameraView = (CameraView) findViewById(R.id.camera);
    }

    @Override
    protected void onResume() {
        super.onStart();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }
}
