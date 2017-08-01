package com.bantczak.munchifyseller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bantczak.munchifyseller.databinding.ActivityNewPostingBinding;
import com.bantczak.munchifyseller.model.FoodPosting;
import com.bantczak.munchifyseller.util.Constants;
import com.bantczak.munchifyseller.util.DataPersister;
import com.bantczak.munchifyseller.util.MessageDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewPostingActivity extends AppCompatActivity implements View.OnClickListener, DataPersister {
    private ActivityNewPostingBinding mActivityBinding;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_posting);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Uploading Posting");
        mProgressDialog.setMessage(getString(R.string.prompt_wait));

        setUI();
    }

    private void setUI() {
        // ActionBar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.new_posting));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_close);
        }
        mActivityBinding.uploadPostingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            saveData();
        }
    }

    /**
     * Saves the posting to the firebase database
     */
    @Override
    public void saveData() {
        if (isInputValid()) {
            showProgress(true);
            final String key = mDatabase.child(Constants.FIREBASE_ALL_POSTINGS).push().getKey();
            FoodPosting foodPosting = getFoodPostingFromInput(key);
            Map<String, Object> postValues = foodPosting.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + Constants.FIREBASE_ALL_POSTINGS + "/" + key, postValues);
            // TODO: store seller info in SharedPrefs and update them whenever someone else logs in
            childUpdates.put("/" + Constants.FIREBASE_POSTINGS_BY_SELLER + "/ABC123/" + key, postValues);

            mDatabase.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    showProgress(false);
                    if (databaseError == null) {
                        makeToast("Successfully uploaded posting.");
                        finish();
                    } else {
                        makeToast("Error: failed to upload posting. Please try again.");
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_posting, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_photo:
                // TODO create photo dialog (camera/gallery) ?
                Intent intent= new Intent(this, CameraActivity.class);
                startActivity(intent);
                //takePhoto();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showProgress(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else if (mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Determines if the input the user entered is valid
     */
    private boolean isInputValid() {
        if (TextUtils.isEmpty(mActivityBinding.postingTitle.getText().toString())) {
            MessageDialog.showDialog(this, "Title cannot be empty.");
            return false;
        }

        // No need to check if parsing will throw error because the EditText only accepts numbers
        double oldPrice = Double.parseDouble(mActivityBinding.postingOldPrice.getText().toString());
        double newPrice = Double.parseDouble(mActivityBinding.postingNewPrice.getText().toString());
        if (newPrice > oldPrice) {
            MessageDialog.showDialog(this, "New price must be less than or equal to old price.");
            return false;
        }

        // Otherwise, we're good
        return true;
    }

    private FoodPosting getFoodPostingFromInput(String key) {
        FoodPosting foodPosting = new FoodPosting();
        double oldPrice = Double.parseDouble(mActivityBinding.postingOldPrice.getText().toString());
        double newPrice = Double.parseDouble(mActivityBinding.postingNewPrice.getText().toString());

        foodPosting.setId(key)
                .setNewPrice(newPrice)
                .setOldPrice(oldPrice)
                .setTitle(mActivityBinding.postingTitle.getText().toString())
                .setSellerId("ABC123")
                .setSellerName("Freshii");

        return foodPosting;
    }
}
