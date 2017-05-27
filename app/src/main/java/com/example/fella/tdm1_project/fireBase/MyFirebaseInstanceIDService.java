package com.example.fella.tdm1_project.fireBase;

/**
 * Created by Fella on 20/05/2017.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param tokenID The new token.
     */
    private void sendRegistrationToServer(String tokenID) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userName = sharedPreferences.getString("username", "null");
        if (userName!="null")
        {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference token= mDatabase.child("utilisateurs").child(userName).child("tokenId");
            token.setValue(tokenID);
        }
        
    }
}