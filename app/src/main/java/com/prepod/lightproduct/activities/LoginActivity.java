package com.prepod.lightproduct.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prepod.lightproduct.Consts;
import com.prepod.lightproduct.LightProduct;
import com.prepod.lightproduct.ProductApiInterface;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.Utils;
import com.prepod.lightproduct.containers.Token;
import com.prepod.lightproduct.containers.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ProductApiInterface service;
    private CoordinatorLayout coordinatorLayout;
    private Token token = new Token();
    private ProgressBar progressBar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEmailView.getText().toString();
                String pass = mPasswordView.getText().toString();
                if (userName != null && pass != null) {
                    if (isLoginValid(userName)) {
                        if (isPasswordValid(pass)) {
                            user = new User();
                            user.setUsername(userName);
                            user.setPassword(pass);
                            progressBar.setVisibility(View.VISIBLE);
                            loginUser(user);
                        } else Utils.showMessage(coordinatorLayout, getString(R.string.bad_pass));
                    } else Utils.showMessage(coordinatorLayout, getString(R.string.bad_name));
                }
            }
        });

        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEmailView.getText().toString();
                String pass = mPasswordView.getText().toString();
                if (userName != null && pass != null) {
                    if (isLoginValid(userName)) {
                        if (isPasswordValid(pass)) {
                            user = new User();
                            user.setUsername(userName);
                            user.setPassword(pass);
                            progressBar.setVisibility(View.VISIBLE);
                            registerUser(user);
                        } else Utils.showMessage(coordinatorLayout, getString(R.string.bad_pass));
                    } else Utils.showMessage(coordinatorLayout, getString(R.string.bad_name));
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 1;
    }

    private boolean isLoginValid(String login) {
        return login.length() > 1;
    }

    private boolean loginUser(User user){
        if (service == null){
            service = LightProduct.getInstance().getRetrofitClient().create(ProductApiInterface.class);
        }
        token.setSuccess(false);
        Call<Token> call = service.loginUser(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response != null){
                    token.setSuccess(response.body().getSuccess());
                    token.setToken(response.body().getToken());
                    token.setMessage(response.body().getMessage());
                }
                if (token.getSuccess()){
                    Utils.storeToken(getApplicationContext(), token);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Utils.showMessage(coordinatorLayout, token.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return token.getSuccess();
    }

    private boolean registerUser(User user){
        if (service == null){
            service = LightProduct.getInstance().getRetrofitClient().create(ProductApiInterface.class);
        }
        token.setSuccess(false);
        Call<Token> call = service.registerUser(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response != null){
                    token.setSuccess(response.body().getSuccess());
                    token.setToken(response.body().getToken());
                    token.setMessage(response.body().getMessage());
                }
                if (token.getSuccess()){
                    Utils.storeToken(getApplicationContext(), token);
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Utils.showMessage(coordinatorLayout, token.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return token.getSuccess();
    }




}

