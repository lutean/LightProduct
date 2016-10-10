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
                    //attemptLogin();
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
               // attemptLogin();
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
                        } else showMessage(getString(R.string.bad_pass));
                    } else showMessage(getString(R.string.bad_name));
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
                            //testRequest(user);
                            progressBar.setVisibility(View.VISIBLE);
                            registerUser(user);
                        } else showMessage(getString(R.string.bad_pass));
                    } else showMessage(getString(R.string.bad_name));

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
                    storeToken();
                } else {
                    showMessage(token.getMessage());
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
                    storeToken();
                } else {
                    showMessage(token.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return token.getSuccess();
    }

    private void showMessage(String message){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    private void storeToken(){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token.getToken());
        editor.putString("username", user.getUsername());
        editor.commit();

        setResult(RESULT_OK);
        finish();

    }

    private void testRequest(final User user){
        String url = Consts.BASE_URL + "api/register/";

        RequestQueue queue = Volley.newRequestQueue(this);  // this = context

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // response

                            Log.d("Response", "" );


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", "" + error);
                        //Toast.makeText(AuthActivity.this, "Что-то пошло не так..", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user.getUsername());
                params.put("password", user.getPassword());
                return params;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
    }




}

