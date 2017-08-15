package com.bantczak.munchifyseller.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bantczak.munchifyseller.CameraActivity;
import com.bantczak.munchifyseller.R;
import com.bantczak.munchifyseller.databinding.FragmentAttachmentsBinding;
import com.bantczak.munchifyseller.databinding.adapters.ImageAttachmentAdapter;
import com.bantczak.munchifyseller.routes.CameraViewRoute;
import com.bantczak.munchifyseller.util.CacheManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

public class AttachmentsFragment extends Fragment implements FilenameFilter {
    private static final String TAG = AttachmentsFragment.class.getSimpleName();
    public static final String EXTRA_FILENAME = "filename";
    private FragmentAttachmentsBinding mBinding;
    private static final int REQUEST_PICTURE_DATA = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private ImageAttachmentAdapter mAdapter;
    private File[] photoFiles;
    private ArrayList<File> mFiles;
    private Handler mBackgroundHandler;

    public static AttachmentsFragment newInstance() {
        Bundle args = new Bundle();
        AttachmentsFragment fragment = new AttachmentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Allows the fragment to call the onOptionsItemSelected() method
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.fragment_attachments,
                container,
                false);

        mFiles = new ArrayList<>(0);
        mAdapter = new ImageAttachmentAdapter(mFiles, getContext());

        mBinding.horizontalRecyclerView.setAdapter(mAdapter);
        mBinding.horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        showRecyclerView(false);

        return mBinding.getRoot();
    }

    private void showRecyclerView(boolean show) {
        if (show) {
            mBinding.horizontalRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mBinding.horizontalRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_photo:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Launch camera activity
                    new CameraViewRoute().forResult(REQUEST_PICTURE_DATA).go(this);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION);
                }
                // TODO create photo dialog (camera/gallery) ?
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                Log.d(TAG, "onRequestPermissionResult");
                if (permissions.length != 1 || grantResults.length != 1) {
                    Log.d(TAG, "error");
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), R.string.camera_permission_not_granted,
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Start camera
                    new CameraViewRoute().forResult(REQUEST_PICTURE_DATA).go(this);
                }
        }
    }

    @Override
    public void onDestroy() {
        for (File file : mFiles) {
            final String filePath = file.getPath();
            boolean result = file.delete();
            Log.d(TAG, "onDestroy: attempt to delete file at path: " + filePath + " with result " + result);
        }
        CameraActivity.fileNameCount = 0; // Reset the fileNameCount
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_PICTURE_DATA) {
            Log.d(TAG, "onActivityResult: REQUEST_PICTURE_DATA");
            String fileName = data.getStringExtra(EXTRA_FILENAME);
            addNewImageToAdapter(fileName);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addNewImageToAdapter(String fileName) {
        File newFileImage = new File(getActivity().getCacheDir(), fileName);
        mFiles.add(newFileImage);
        mAdapter.notifyDataSetChanged();
        showRecyclerView(mFiles.size() != 0);
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.contains("picture");
    }
}
