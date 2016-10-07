package com.prepod.lightproduct;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LightProduct extends Application {

    private static LightProduct mInstance;
    private Retrofit client;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;

    }

    public static synchronized LightProduct getInstance(){
        return mInstance;
    }

    public Retrofit getRetrofitClient(){
        if (client == null){
            client = new Retrofit.Builder().baseUrl(Consts.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return client;
    }

}
