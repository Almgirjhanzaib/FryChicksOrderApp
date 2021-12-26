package com.example.frychicksorderapp.ui;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class FriChicksOrderApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Picasso.Builder builder = new Picasso.Builder(this);
       builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
       Picasso built = builder.build();
       built.setIndicatorsEnabled(false);
       Picasso.setSingletonInstance(built);

    }
}
