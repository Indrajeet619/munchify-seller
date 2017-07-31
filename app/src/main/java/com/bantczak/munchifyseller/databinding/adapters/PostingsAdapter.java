package com.bantczak.munchifyseller.databinding.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantczak.munchifyseller.R;
import com.bantczak.munchifyseller.databinding.viewholders.FoodPostingViewHolder;
import com.bantczak.munchifyseller.model.FoodPosting;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class PostingsAdapter extends FirebaseRecyclerAdapter<FoodPosting, FoodPostingViewHolder>
        implements FoodPostingViewHolder.FoodPostingListener {

    public interface PostingsAdapterListener {
        void onFoodPostingClicked(FoodPosting posting);
    }

    private PostingsAdapterListener mListener;

    public PostingsAdapter(Class<FoodPosting> modelClass,
                           int modelLayout,
                           Class<FoodPostingViewHolder> viewHolderClass,
                           Query ref,
                           PostingsAdapterListener listener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mListener = listener;
    }

    @Override
    protected void populateViewHolder(FoodPostingViewHolder viewHolder, FoodPosting model, int position) {
        viewHolder.setFoodPostingPriceText(String.valueOf(model.getNewPrice()));
        viewHolder.setFoodPostingTitleText(model.getTitle());
    }

    @Override
    public FoodPostingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_posting_item, parent, false);
        return new FoodPostingViewHolder(v, this);
    }

    @Override
    public void onItemClicked(int position) {
        FoodPosting posting = getItem(position);
        mListener.onFoodPostingClicked(posting);
    }
}
