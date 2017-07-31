package com.bantczak.munchifyseller;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bantczak.munchifyseller.databinding.ActivityMainBinding;
import com.bantczak.munchifyseller.databinding.adapters.PostingsAdapter;
import com.bantczak.munchifyseller.databinding.viewholders.FoodPostingViewHolder;
import com.bantczak.munchifyseller.model.FoodPosting;
import com.bantczak.munchifyseller.routes.NewPostingRoute;
import com.bantczak.munchifyseller.util.Constants;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PostingsAdapter.PostingsAdapterListener {
    private String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Log.d(TAG, "onCreate");
        // Setup UI.
        setUI();
    }

    private void setUI() {
        mActivityBinding.createPostingFab.setOnClickListener(this);

        // RecyclerView
        // TODO for MUSE-5
        Query query = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_POSTINGS_BY_SELLER).child("ABC123");
        PostingsAdapter adapter = new PostingsAdapter(FoodPosting.class, R.layout.food_posting_item,FoodPostingViewHolder.class, query, this);
        mActivityBinding.foodPostingRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mActivityBinding.foodPostingRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof FloatingActionButton) {
            startCreateNewPosting();
        }
    }

    private void startCreateNewPosting() {
        new NewPostingRoute().go(this);
    }

    @Override
    public void onFoodPostingClicked(FoodPosting posting) {
        makeToast("Clicked on " + posting.getTitle());
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
