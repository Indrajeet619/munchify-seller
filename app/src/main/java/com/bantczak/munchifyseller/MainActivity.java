package com.bantczak.munchifyseller;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bantczak.munchifyseller.databinding.ActivityMainBinding;
import com.bantczak.munchifyseller.routes.NewPostingRoute;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Log.d(TAG, "onCreate");
        // Setup UI.
        setSupportActionBar(mActivityBinding.toolbar);
        mActivityBinding.createPostingFab.setOnClickListener(this);
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
}
