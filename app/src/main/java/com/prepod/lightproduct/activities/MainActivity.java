package com.prepod.lightproduct.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.fragments.ProductsListFragment;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(null);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        showProductsList();

    }

    private void showProductsList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new ProductsListFragment())
                .commit();
    }
}
