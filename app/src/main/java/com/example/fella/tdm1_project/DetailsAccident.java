package com.example.fella.tdm1_project;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.fella.tdm1_project.DbSqlLight.DossierDAO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fella on 09/04/2017.
 */

public class DetailsAccident extends AppCompatActivity implements
        View.OnClickListener{

    private ImageView recordingButton;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int SELECT_PICTURE_CONSTANT = 1;
    public static Uri imageStoragePath;
    public static Uri filmStoragePath;
    private DossierDAO dossierDAO;
    Intent camera_intent;
    Intent video_intent;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime, lieuA;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private final int IMAGE_REQUEST_CODE = 100;
    private final int VIDEO_REQUEST_CODE = 101;

    Uri otherData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_accident);
        getSupportActionBar().show();
        setTitle("Détails accident");

        dossierDAO = new DossierDAO(this);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        lieuA= (EditText) findViewById(R.id.lieu_edittext);
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        txtDate.setText(mMonth + "-" + (mDay + 1) + "-" + mYear);
        txtTime.setText(mHour + ":" + mMinute);


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        ImageView buttonLoadImage = (ImageView) findViewById(R.id.buttonLoadPicture);
        camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        otherData = Uri.parse("android.resource://dz.easy.exo2s5/" + R.drawable.croix);
        imageStoragePath = otherData;
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                File image_file = getFilePath();
                imageStoragePath = Uri.fromFile(image_file);
                Log.d("Path = ",imageStoragePath.toString());
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageStoragePath);
                camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(camera_intent, IMAGE_REQUEST_CODE);
            }
        });


        video_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        otherData = Uri.parse("android.resource://dz.easy.exo2s5/" + R.drawable.ic_video);

        recordingButton = (ImageView) findViewById(R.id.buttonLoadVideo);
        recordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File video_file = getVideoPath();
                filmStoragePath = Uri.fromFile(video_file);
                Log.d("Path = ",filmStoragePath.toString());
                video_intent.putExtra(MediaStore.EXTRA_OUTPUT, filmStoragePath);
                video_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(video_intent, VIDEO_REQUEST_CODE);

            }
        });

    }

    public File getFilePath(){
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/images");
        if(!folder.exists()) folder.mkdir();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File file = new File(folder, "IMG_"+ timeStamp + ".jpg");
        return file;
    }

    public File getVideoPath(){
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/videos");
        if(!folder.exists()) folder.mkdir();
        File video_file;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        video_file = new File(folder,"VID_" + timeStamp + ".mp4");
        return video_file;
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==IMAGE_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(),"Image prise avec succès", Toast.LENGTH_LONG).show();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                imgView.setVisibility(View.VISIBLE);
                imgView.setImageURI(imageStoragePath);
            }else{
                Toast.makeText(getApplicationContext(),"Image non Capturée", Toast.LENGTH_LONG).show();
                imageStoragePath = otherData;
            }
        }else if(requestCode==VIDEO_REQUEST_CODE){
            if(resultCode==RESULT_OK){
//                Toast.makeText(this, "Enregistré", Toast.LENGTH_SHORT).show();
                VideoView videoView = (VideoView)findViewById(R.id.detail_video);
//                videoView.setVideoURI(filmStoragePath);

                MediaController mc = new MediaController(this);
                mc.setAnchorView(videoView);
                mc.setMediaPlayer(videoView);

                videoView.setMediaController(mc);
                videoView.setVideoURI(filmStoragePath);
                videoView.start();
            }else{
                Toast.makeText(getApplicationContext(),"Video Non prise", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void chargerActivity(View view){
        Intent i = getIntent();

        Intent intent=null;
        switch (view.getId()) {


            case R.id.butSuiv2:
                if(imageStoragePath != null && filmStoragePath != null){
                    MainActivity.dossier.setDate(txtDate.getText().toString());
                    MainActivity.dossier.setHeure(txtTime.getText().toString());
                    MainActivity.dossier.setLieu(lieuA.getText().toString());

                    if (MainActivity.dossier.getTypeAccident() == "Collision")
                    {
                        intent = new Intent(this, PersonneTiersActivity.class);
                    }
                    else
                    {
                        intent = new Intent(this, DegatsApparents.class);

                    }
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Veuillez insérer une photo et une vidéo", Toast.LENGTH_SHORT).show();
                }
                break;
                }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_home: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Alert!");
                builder.setMessage("Voulez-vous enregistrer votre déclaration?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.dossier.setEtat("Ouvert");
                        MainActivity.dossier.setDate(txtDate.getText().toString());
                        MainActivity.dossier.setHeure(txtTime.getText().toString());
                        MainActivity.dossier.setLieu(lieuA.getText().toString());
                        dossierDAO.ajouter(MainActivity.dossier);

                        Intent intent = new Intent(DetailsAccident.super.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(DetailsAccident.super.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        }
        return false;
    }


}
