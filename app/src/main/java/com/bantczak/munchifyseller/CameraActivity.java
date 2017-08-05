package com.bantczak.munchifyseller;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bantczak.munchifyseller.databinding.CameraViewBinding;
import com.google.android.cameraview.CameraView;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraViewBinding mCameraViewBinding;

    CameraView.Callback mCallback = new CameraView.Callback() {
        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d(TAG, "onCameraOpened");
            super.onCameraOpened(cameraView);
        }

        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            // preview image in image view
            Bitmap preview = BitmapFactory.decodeByteArray(data, 0, data.length);
            mCameraViewBinding.previewImage.setImageBitmap(preview);
            mCameraViewBinding.previewImage.setVisibility(View.VISIBLE);
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
            super.onCameraClosed(cameraView);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraViewBinding = DataBindingUtil.setContentView(this, R.layout.camera_view);

        mCameraViewBinding.camera.addCallback(mCallback);
        mCameraViewBinding.takePictureButton.setOnClickListener(this);

      //  setCameraUI();
    }
/*
    private void setCameraUI() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.
    }
*/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Get the preview size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int previewWidth = displayMetrics.widthPixels;
        int previewHeight = displayMetrics.heightPixels;

        //ViewGroup.LayoutParams layoutParams = mCameraViewBinding.previewImage.getLayoutParams();

        // Set the height of the overlay so that it makes the preview a square
        FrameLayout.LayoutParams overlayParams = (FrameLayout.LayoutParams) mCameraViewBinding.cameraOverlay.getLayoutParams();
        overlayParams.height = previewHeight - previewWidth;
        Log.d(TAG, "Height: " + overlayParams.height);
        Log.d(TAG, "Width: " + overlayParams.width);
        mCameraViewBinding.cameraOverlay.setLayoutParams(overlayParams);
    }

    @Override
    protected void onResume() {
        super.onStart();
        mCameraViewBinding.camera.start();
    }

    @Override
    protected void onPause() {
        mCameraViewBinding.camera.stop();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == mCameraViewBinding.takePictureButton) {
            mCameraViewBinding.camera.takePicture();
        }
    }
}
