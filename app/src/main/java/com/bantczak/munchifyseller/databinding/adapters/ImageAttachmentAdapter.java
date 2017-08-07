package com.bantczak.munchifyseller.databinding.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantczak.munchifyseller.R;
import com.bantczak.munchifyseller.databinding.viewholders.ImageAttachmentViewHolder;

import java.util.List;

public class ImageAttachmentAdapter extends RecyclerView.Adapter<ImageAttachmentViewHolder> {
    private List<String> mImageUrls;
    private Context mContext;

    public ImageAttachmentAdapter(List<String> imageUrls, Context context) {
        this.mImageUrls = imageUrls;
        mContext = context;
    }

    @Override
    public ImageAttachmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_attachment, parent, false);
        return new ImageAttachmentViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(ImageAttachmentViewHolder holder, int position) {
        holder.setImageView(mImageUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }
}
