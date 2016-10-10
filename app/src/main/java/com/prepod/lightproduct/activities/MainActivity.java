package com.prepod.lightproduct.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.prepod.lightproduct.Consts;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.Utils;
import com.prepod.lightproduct.fragments.ProductsListFragment;


public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(null);
        ImageView icon = (ImageView) findViewById(R.id.back_btn);
        icon.setImageResource(R.drawable.logo);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.content_frame);
        showProductsList();

    }

    private void showProductsList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new ProductsListFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Log.v("", "");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, Consts.REQUSET_CODE_LOGIN);
                return true;
            case 2:
                Log.v("", "");
                Utils.clearToken(this);
                Utils.showMessage(coordinatorLayout, getString(R.string.logged_out));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Utils.getToken(this).equals("")){
            menu.setGroupVisible(0, true);
            menu.setGroupVisible(1, false);
        } else {
            menu.setGroupVisible(1, true);
            menu.setGroupVisible(0, false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Log in");
        menu.add(1, 2, 1, "Log out");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Consts.REQUSET_CODE_LOGIN){
            if (resultCode == RESULT_OK){
                Utils.showMessage(coordinatorLayout, getString(R.string.logged_in));
            }
        }
    }



}
