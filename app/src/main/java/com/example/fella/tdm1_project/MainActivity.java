package com.example.fella.tdm1_project;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;



import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fella.tdm1_project.GeoLocalisaion.MapsActivity;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int SELECT_PICTURE_CONSTANT = 1;
    public static StorageReference mStorage;
    public static Dossier dossier;
    public static boolean debut;
    public static DatabaseReference mDatabase;
    public static boolean syncro = false;
    DatabaseReference dossiers;
    DatabaseReference token;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //<< this
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName = sharedPreferences.getString("username", "null");

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        if(!MainActivity.syncro){
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//            mDatabase = FirebaseDatabase.getInstance().getReference();
//            mDatabase.keepSynced(true);
//            syncro = true;
//        }
        token= mDatabase.child("utilisateurs").child(userName).child("tokenId");

        sendRegistrationToServer();

        dossiers=mDatabase.child("Dossiers").child("assureID");

        dossiers.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot expertItem : snapshot.getChildren()) {

                       if(expertItem.child("etat").getValue()==("En traitement") && expertItem.child("assureID").getValue()==userName)
                       {
                           editor = sharedPreferences.edit();
                           editor.putString("dossierEnTraitement","true");
                           editor.commit();

                           break;
                       }

                    }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            } });
        
        String etatDossiers =sharedPreferences.getString("dossierEnTraitement","null");
    }

    public void chargerActivity(View view){
        Intent intent=null;
        switch (view.getId())
        {
            case R.id.button11 :
                intent = new Intent(this, Profile_activity.class);
                startActivity(intent);
                break;
            case R.id.button15 :
                intent = new Intent(this, ListDossierActivity.class);
                debut =true;
                startActivity(intent);
                break;

            case R.id.button12 :
                intent = new Intent(this, liste_vehicule_activity.class);
                intent.putExtra("nouveauV", "false");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.button13 :
                intent = new Intent(this, TypesAccident.class);
                dossier = new Dossier();
                startActivity(intent);
                break;



            case R.id.buttonMap :
            intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            break;


            case R.id.btndec:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username","null");
                sendUnregetrationToServer();
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }



    }



    private void sendRegistrationToServer() {

        String tokenID= FirebaseInstanceId.getInstance().getToken();

        if (userName!="null")
        {
            token.setValue(tokenID);
        }

    }


    private void sendUnregetrationToServer() {


        if (userName!="null")
        {
            token.setValue("null");
        }

    }






}