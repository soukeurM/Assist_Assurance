package com.example.fella.tdm1_project;


import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by RIMA on 18/05/2017.
 */

public class FireApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
