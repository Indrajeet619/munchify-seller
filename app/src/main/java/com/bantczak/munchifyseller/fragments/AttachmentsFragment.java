package com.bantczak.munchifyseller.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

import com.bantczak.munchifyseller.R;
import com.bantczak.munchifyseller.databinding.FragmentAttachmentsBinding;
import com.bantczak.munchifyseller.databinding.adapters.ImageAttachmentAdapter;
import com.bantczak.munchifyseller.routes.CameraViewRoute;

import java.util.ArrayList;

public class AttachmentsFragment extends Fragment {
    private FragmentAttachmentsBinding mBinding;
    private static final int REQUEST_PICTURE_DATA = 0;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

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
        ArrayList<String> list = new ArrayList<>(0);
        // Dummy image right now
        list.add("ds");
        list.add("https21221-256a-4e41-9664-7edc4307a8d6");
        list.add("https21221-256a-4e41-9664-7edc4307a8d6");
        list.add("https21221-256a-4e41-9664-7edc4307a8d6");
        list.add("https21221-256a-4e41-9664-7edc4307a8d6");
        list.add("https21221-256a-4e41-9664-7edc4307a8d6");
        ImageAttachmentAdapter attachmentAdapter = new ImageAttachmentAdapter(list, getContext());
        mBinding.horizontalRecyclerView.setAdapter(attachmentAdapter);
        mBinding.horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return mBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Attachment", "Click on menu");
        switch (item.getItemId()) {
            case R.id.add_photo:
                Log.d("Attachment", "Clicked on photo");
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Launch camera activity
                    new CameraViewRoute().newTask().go(getActivity());
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
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), R.string.camera_permission_not_granted,
                            Toast.LENGTH_SHORT).show();
                }
                // Start camera
                new CameraViewRoute().newTask().go(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_PICTURE_DATA) {
            // Take the data of the
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
