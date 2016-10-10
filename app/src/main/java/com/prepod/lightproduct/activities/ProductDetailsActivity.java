package com.prepod.lightproduct.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.prepod.lightproduct.R;
import com.prepod.lightproduct.fragments.ProductDetailsFragment;
import com.prepod.lightproduct.fragments.ProductsListFragment;
import com.prepod.lightproduct.fragments.VoteDialogFragment;

public class ProductDetailsActivity extends AppCompatActivity implements VoteDialogFragment.NoticeDialogListener{
    private ImageView backBtn;
    private ImageView voteBtn;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        voteBtn = (ImageView) findViewById(R.id.vote_btn);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutProduct);
        if (!getToken().equals("")){
            voteBtn.setVisibility(View.VISIBLE);
        }
        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        backBtn.setImageResource(R.drawable.arrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsActivity.super.onBackPressed();
            }
        });


        setTitle(null);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);


        showProductDetails();


    }

    private void showProductDetails() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new ProductDetailsFragment())
                .commit();
    }

    private void showDialog(){
        DialogFragment dialog = new VoteDialogFragment();
        dialog.show(getFragmentManager(), "NoticeDialogFragment");

    }
    private String getToken(){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        return settings.getString("token", "");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String message) {
        Log.v("", "");
        showProductDetails();
        showMessage(message);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.v("", "");
    }

    private void showMessage(String message){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
