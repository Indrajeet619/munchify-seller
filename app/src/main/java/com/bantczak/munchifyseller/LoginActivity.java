package com.bantczak.munchifyseller;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bantczak.munchifyseller.databinding.ActivityLoginBinding;
import com.bantczak.munchifyseller.routes.MainActivityRoute;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    //private DatabaseReference mDatabase;
    private ActivityLoginBinding mLoginBinding;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Firebase auth
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "onCreate");
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // Firebase database
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        // Setup login dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.prompt_wait));
        mProgressDialog.setTitle(getString(R.string.login_message));

        // Setup listener
        mLoginBinding.signInButton.setOnClickListener(this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void validateLogin() {
        // Reset errors.
        mLoginBinding.sellerEmail.setError(null);
        mLoginBinding.sellerPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mLoginBinding.sellerEmail.getText().toString();
        String password = mLoginBinding.sellerPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mLoginBinding.sellerPassword.setError(getString(R.string.error_invalid_password));
            focusView = mLoginBinding.sellerPassword;
            cancel = true;
        }

        // Check for a valid email.
        if (TextUtils.isEmpty(email)) {
            mLoginBinding.sellerEmail.setError(getString(R.string.error_field_required));
            focusView = mLoginBinding.sellerEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            attemptLogin(email, password);
        }
    }

    private void attemptLogin(final String email, final String password) {
        showProgress(true);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    showProgress(false);
                    startMainActivity();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showProgress(final boolean show) {
        if (show) {
            mProgressDialog.show();
        } else if (mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private boolean isPasswordValid(String password) {
        // Firebase passwords must be at least 6 characters long
        return password.length() >= 6;
    }

    private void startMainActivity() {
        // Start the main activity
        new MainActivityRoute().go(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startMainActivity();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            validateLogin();
        }
    }
}

