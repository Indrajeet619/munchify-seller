package com.bantczak.munchifyseller;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bantczak.munchifyseller.databinding.CameraViewBinding;
import com.bantczak.munchifyseller.fragments.AttachmentsFragment;
import com.bantczak.munchifyseller.util.CacheManager;
import com.google.android.cameraview.CameraView;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraViewBinding mCameraViewBinding;
    private Handler mBackgroundHandler;
    public static long fileNameCount = 0;
    private byte[] mCurrentImage;

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
            mCurrentImage = data;
            showPreviewImageLayout(true);
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
            super.onCameraClosed(cameraView);
        }
    };

    // Show the check/cancel buttons when previewing an image
    private void showPreviewImageLayout(boolean show) {
        int viewType;
        if (show) {
            viewType = View.VISIBLE;
        } else {
            viewType = View.GONE;
        }

        //File.createTempFile("posting_images", "png", get);
        mCameraViewBinding.previewImage.setVisibility(viewType);
        mCameraViewBinding.cancelBtn.setVisibility(viewType);
        mCameraViewBinding.checkBtn.setVisibility(viewType);
        mCameraViewBinding.takePictureButton.setVisibility(viewType == View.GONE ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraViewBinding = DataBindingUtil.setContentView(this, R.layout.camera_view);

        setUI();
    }

    private void setUI() {
        mCameraViewBinding.camera.addCallback(mCallback);
        mCameraViewBinding.takePictureButton.setOnClickListener(this);
        mCameraViewBinding.cancelBtn.setOnClickListener(this);
        mCameraViewBinding.checkBtn.setOnClickListener(this);

        showPreviewImageLayout(false);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Take Photo");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_close);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Log.d(TAG, "clicked on home");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Get the preview size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int previewWidth = displayMetrics.widthPixels;
        int previewHeight = displayMetrics.heightPixels;

        // Set the height of the overlay so that it makes the preview a square
        FrameLayout.LayoutParams overlayParams = (FrameLayout.LayoutParams) mCameraViewBinding.cameraOverlay.getLayoutParams();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            overlayParams.height = previewHeight - previewWidth - actionBar.getHeight();
        } else {
            overlayParams.height = previewHeight - previewWidth;
        }
        Log.d(TAG, "Height: " + overlayParams.height);
        Log.d(TAG, "Width: " + overlayParams.width);
        mCameraViewBinding.cameraOverlay.setLayoutParams(overlayParams);
    }

    @Override
    protected void onResume() {
        super.onStart();
        mCameraViewBinding.camera.start();
    }

    // TODO look up if we need a new handler (static handler?)
    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
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
        } else if (v == mCameraViewBinding.cancelBtn) {
            showPreviewImageLayout(false);
            mCurrentImage = null;
        } else if (v == mCameraViewBinding.checkBtn) {
            saveImageLocally();
            // TODO: Save image as a preview in the previous activity
        }
    }

    /**
     * Saves the image to the local cache to allow the attachment adapter to read it
     */
    private void saveImageLocally() {
        if (mCurrentImage != null) {
            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String fileName = "picture" + fileNameCount + ".jpg";
                        CacheManager.cacheData(CameraActivity.this, mCurrentImage, fileName);
                        fileNameCount++;
                        Intent result = new Intent();
                        result.putExtra(AttachmentsFragment.EXTRA_FILENAME, fileName);
                        setResult(RESULT_OK, result);
                        finish();
                    } catch (IOException e) {
                        makeToast("Unable to process image.");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
