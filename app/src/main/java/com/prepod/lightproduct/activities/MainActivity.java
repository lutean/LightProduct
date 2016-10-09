package com.prepod.lightproduct.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.prepod.lightproduct.Consts;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.fragments.ProductsListFragment;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(null);
        ImageView icon = (ImageView) findViewById(R.id.back_btn);
        icon.setImageResource(R.drawable.logo);
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
                clearToken();
                return true;

           // case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
            //    return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (getToken().equals("")){
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
      //  if (getToken().equals("")){
      //      getMenuInflater().inflate(R.menu.user_in, menu);
       // } else {
       //    getMenuInflater().inflate(R.menu.user_out, menu);
      //  }
        menu.add(0, 1, 0, "Log in");
        menu.add(1, 2, 1, "Log out");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Consts.REQUSET_CODE_LOGIN){
            if (resultCode == RESULT_OK){
                Log.v("","");
            }
        }
    }

    private String getToken(){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        return settings.getString("token", "");
    }

    private void clearToken() {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", "");
        editor.commit();
    }

}
