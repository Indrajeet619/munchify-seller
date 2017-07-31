package com.bantczak.munchifyseller.databinding.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bantczak.munchifyseller.R;

public class FoodPostingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public interface FoodPostingListener {
        void onItemClicked(int position);
    }

    private FoodPostingListener mListener;
    private View mItemView;

    private ImageView foodPostingImage;
    private TextView foodPostingTitle, foodPostingPrice;

    public FoodPostingViewHolder(View itemView, FoodPostingListener listener) {
        super(itemView);

        mItemView = itemView;
        mListener = listener;

        foodPostingImage = (ImageView) itemView.findViewById(R.id.food_posting_preview);
        foodPostingTitle = (TextView) itemView.findViewById(R.id.food_posting_title);
        foodPostingPrice = (TextView) itemView.findViewById(R.id.food_posting_price);

        mItemView.setOnClickListener(this);
    }

    /*
    public void setFoodImage(Bitmap bitmap) {
        // Glide
    }
    */

    public void setFoodPostingPriceText(String foodPrice) {
        this.foodPostingPrice.setText(foodPrice);
    }

    public void setFoodPostingTitleText(String foodTitle) {
        this.foodPostingTitle.setText(foodTitle);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClicked(getAdapterPosition());
    }
}
