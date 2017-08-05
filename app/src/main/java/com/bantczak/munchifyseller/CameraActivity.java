package com.bantczak.munchifyseller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.cameraview.CameraView;

public class CameraActivity extends AppCompatActivity {

    private CameraView cameraView;
    private FrameLayout cameraCover;

    CameraView.Callback mCallback = new CameraView.Callback() {
        @Override
        public void onCameraOpened(CameraView cameraView) {
            super.onCameraOpened(cameraView);
        }

        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            super.onPictureTaken(cameraView, data);
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            super.onCameraClosed(cameraView);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);
        cameraView = (CameraView) findViewById(R.id.camera);
        cameraCover = (FrameLayout) findViewById(R.id.camera_cover_layout);

        cameraView.addCallback(mCallback);
    }

    private void setUI() {
        /*
        SOMETHING LIKE THIS?

        FrameLayout.LayoutParams params;
        params.width = 3;
        cameraCover.setLayoutParams(params);
    */}

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
