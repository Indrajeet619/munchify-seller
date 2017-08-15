package com.bantczak.munchifyseller.databinding.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantczak.munchifyseller.R;
import com.bantczak.munchifyseller.databinding.viewholders.ImageAttachmentViewHolder;

import java.io.File;
import java.util.List;

public class ImageAttachmentAdapter extends RecyclerView.Adapter<ImageAttachmentViewHolder> {
    private List<File> mFiles;
    private Context mContext;
   // private File[] mFiles2;

    public ImageAttachmentAdapter(List<File> cacheFiles, Context context) {
        this.mFiles = cacheFiles;
        mContext = context;
        for (File file : cacheFiles) {
            Log.d("Adapter", file.getName());
        }
        // this.mFiles2 = context.getCacheDir().listFiles(this);
    }

    @Override
    public ImageAttachmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_attachment, parent, false);
        return new ImageAttachmentViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(ImageAttachmentViewHolder holder, int position) {
        holder.setImageView(mFiles.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount: " + mFiles.size());
        for (File file : mFiles) {
            Log.d("Adapter", file.getName());
        }
        return mFiles.size();
    }

    public void updateList(List<File> files) {
        this.mFiles = files;
    }
}
