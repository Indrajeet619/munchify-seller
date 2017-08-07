package com.bantczak.munchifyseller.databinding.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bantczak.munchifyseller.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageAttachmentViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private Context mContext;

    public ImageAttachmentViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;

        mImageView = (ImageView) itemView.findViewById(R.id.attached_image);
    }

    public void setImageView(String url) {
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("postings").child("ABC123").child("19424319_1407332822685439_8350214214509760708_n.jpg");
        Glide.with(mContext)
                .using(new FirebaseImageLoader())
                .load(reference)
                .placeholder(R.drawable.check_circle)
                .into(mImageView);
    }

}
