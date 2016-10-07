package com.prepod.lightproduct.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.prepod.lightproduct.R;
import com.prepod.lightproduct.fragments.ProductDetailsFragment;
import com.prepod.lightproduct.fragments.ProductsListFragment;

public class ProductDetailsActivity extends AppCompatActivity{
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        backBtn = (ImageView) findViewById(R.id.back_btn);
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

}
