package com.bantczak.munchifyseller.databinding.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bantczak.munchifyseller.R;
import com.bumptech.glide.Glide;

import java.io.File;

public class ImageAttachmentViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private Context mContext;

    public ImageAttachmentViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;

        mImageView = (ImageView) itemView.findViewById(R.id.attached_image);
    }

    public void setImageView(File file) {
        Log.d("ViewHolder", file.getPath());
       // StorageReference reference = FirebaseStorage.getInstance().getReference().child("postings").child("ABC123").child("19424319_1407332822685439_8350214214509760708_n.jpg");
        Glide.with(mContext)
                //.using(new FirebaseImageLoader())
                .load(file)
                .placeholder(R.drawable.check_circle)
                .into(mImageView);
    }

}
