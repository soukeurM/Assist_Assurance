package com.example.fella.tdm1_project;

import android.*;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.fella.tdm1_project.DbSqlLight.DossierDAO;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by RIMA on 21/05/2017.
 */

public class DetailsDossier extends AppCompatActivity implements
        View.OnClickListener{

    private ImageView recordingButton;
    ImageView imgView;
    VideoView videoView;
    ImageView buttonLoadImage;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int SELECT_PICTURE_CONSTANT = 1;
    public static Uri imageStoragePath = null;
    public static Uri filmStoragePath = null;
    Intent camera_intent;
    Intent video_intent;
    private final int IMAGE_REQUEST_CODE = 100;
    private final int VIDEO_REQUEST_CODE = 101;
    Uri otherData = null;
    DatabaseReference dossiersListe;
    public static PersonneTiers personneTiers;
    private String[] arraySpinner1;
    private DossierDAO dossierDAO;

    private String leType;
    private String test = "", numDossier, etatDossier;
    Button btnTiers, btnSuiv, btnSend;
    EditText txtDate, txtTime, lieuA;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private Spinner s1;
    DatabaseReference firebaseChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();


        final String unDossier = (String) getIntent().getSerializableExtra("noDossier");
        final String unEtat = (String) getIntent().getSerializableExtra("unEtat");
        //final String unType = (String) getIntent().getSerializableExtra("typeDossier");
        final String unType = (String) getIntent().getExtras().getString("typeDossier");
        leType = unType;
        setTitle("Dossier N° "+ unDossier +" : "+unEtat);
        setContentView(R.layout.details_dossier);


        dossierDAO = new DossierDAO(this);

        numDossier = unDossier;
        etatDossier = unEtat;
        this.arraySpinner1 = new String[] {
                "Collision", "Brise de galce ou vitre", "Incendie et explosion", "Vol et cambrilolage"
        };
        s1 = (Spinner) findViewById(R.id.spin_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        s1.setAdapter(adapter);

        imgView = (ImageView) findViewById(R.id.imgView);
        videoView = (VideoView) findViewById(R.id.detail_video);

        btnTiers=(Button)findViewById(R.id.btn_tiers);
        btnSuiv=(Button)findViewById(R.id.button2);
        btnSend = (Button)findViewById(R.id.btn_send);

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

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        btnTiers.setOnClickListener(this);
        btnSuiv.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        if(!unEtat.equals("Ouvert")){
            btnSuiv.setEnabled(false);
            btnSend.setEnabled(false);
            StorageReference childStorage = MainActivity.mStorage.child("Medias").child(numDossier);
            childStorage.child("Video").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    MediaController mc = new MediaController(DetailsDossier.this);
                    mc.setAnchorView(videoView);
                    mc.setMediaPlayer(videoView);

                    videoView.setMediaController(mc);
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            childStorage.child("Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(DetailsDossier.this).load(uri).fit().centerCrop().into(imgView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            dossiersListe = MainActivity.mDatabase.child("Dossiers");

            ValueEventListener valueEventListener = dossiersListe.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot vehiculeItem : snapshot.getChildren()) {
                        if(vehiculeItem.child("id").getValue().equals(numDossier)){
                            Dossier value = vehiculeItem.getValue(Dossier.class);

                            txtDate.setText(value.getDate());
                            txtTime.setText(value.getHeure());
                            lieuA.setText(value.getLieu());

                            int position = 1;
                            String choixType;

                            if(leType.equals("#"))   choixType = value.getTypeAccident();
                            else choixType = unType;


                            if(choixType.equals("Collision"))   position = 0;
                            else if(choixType.equals("Brise de galce ou vitre"))  position = 1;
                            else if(choixType.equals("Incendie ou explosion"))  position = 2;
                            else position = 3;

                            if(position == 0)   btnTiers.setEnabled(true);
                            else btnTiers.setEnabled(false);

                            s1.setSelection(position);
                            personneTiers = value.getInfoTiers();
                        }

                        }

                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{
            btnSuiv.setEnabled(true);
            btnSend.setEnabled(true);

            Dossier value = dossierDAO.selectionner(unDossier);

            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            if(value.getVideoPath() != null){
                Uri video = Uri.parse(value.getVideoPath());
                videoView.setMediaController(mc);
                videoView.setVideoURI(video);
                videoView.start();
            }

            if(value.getImagePath() != null)
                imgView.setImageURI(value.getImagePath());

            txtDate.setText(value.getDate());
            txtTime.setText(value.getHeure());
            lieuA.setText(value.getLieu());

            int position = 1;
            String choixType = value.getTypeAccident();
            if(choixType.equals("Collision"))   position = 0;
            else if(choixType.equals("Brise de galce ou vitre"))  position = 1;
            else if(choixType.equals("Incendie ou explosion"))  position = 2;
            else position = 3;

            if(position == 0)   btnTiers.setEnabled(true);
            else btnTiers.setEnabled(false);
            s1.setSelection(position);

            personneTiers = value.getInfoTiers();

        }

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        leType = "Collision";
                        btnTiers.setEnabled(true);break;
                    case 1:
                        leType = "Brise de galce ou vitre";
                        btnTiers.setEnabled(false);
                        break;
                    case 2:
                        leType = "Incendie et explosion";
                        btnTiers.setEnabled(false);
                        break;
                    case 3:
                        leType = "Vol et cambrilolage";
                        btnTiers.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonLoadImage = (ImageView) findViewById(R.id.buttonLoadPicture);
        camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Image Succefully Captured", Toast.LENGTH_LONG).show();

                imgView.setVisibility(View.VISIBLE);
                imgView.setImageURI(imageStoragePath);
            } else {
                Toast.makeText(getApplicationContext(), "Image Capture Failed", Toast.LENGTH_LONG).show();
                imageStoragePath = otherData;
            }
        } else if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Enregistré", Toast.LENGTH_SHORT).show();

//                videoView.setVideoURI(filmStoragePath);

                videoView.setVisibility(View.VISIBLE);
                MediaController mc = new MediaController(this);
                mc.setAnchorView(videoView);
                mc.setMediaPlayer(videoView);

                videoView.setMediaController(mc);
                videoView.setVideoURI(filmStoragePath);
                videoView.start();
            } else {
                Toast.makeText(getApplicationContext(), "Video Non prises", Toast.LENGTH_LONG).show();
            }
        }
    }


        @Override
    public void onClick(View v) {

        if (v == txtDate) {
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

        if (v == txtTime) {

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

        if(v == btnSuiv){
            Dossier value = dossierDAO.selectionner(numDossier);

                // id = unDossier
                String assureID = "fella";
                String dateDossier = txtDate.getText().toString();
                String heureDossier = txtTime.getText().toString();
                // personneTiers
                String lieuDossier = lieuA.getText().toString();
                String typeD = s1.getSelectedItem().toString();

            if(imageStoragePath == null && value.getImagePath() != null)    imageStoragePath = value.getImagePath();
            if(filmStoragePath == null && value.getVideoPath()!=null)     filmStoragePath = Uri.parse(value.getVideoPath());
            Dossier thsDossier;
            if(imageStoragePath != null && filmStoragePath != null)
             thsDossier = new Dossier(numDossier, value.getAssureID(), dateDossier, heureDossier, personneTiers,
                    value.getMontantEstime(), etatDossier, typeD, lieuDossier, value.getMatricule(), imageStoragePath, filmStoragePath.toString(), value.getDegatPath());
            else
                thsDossier = new Dossier(numDossier, value.getAssureID(), dateDossier, heureDossier, personneTiers,
                        value.getMontantEstime(), etatDossier, typeD, lieuDossier, value.getMatricule(), null, null, null);
            dossierDAO.modifier(thsDossier);

            onBackPressed();
        }

        if(v == btnTiers){
            Intent intent = new Intent(getApplicationContext(), InfosTiersActivity.class);
            intent.putExtra("noDossier", numDossier);
            intent.putExtra("unEtat", etatDossier);
            intent.putExtra("typeDossier", leType);
            startActivity(intent);
        }

        if(v == btnSend){
            Dossier value = dossierDAO.selectionner(numDossier);

            // id = unDossier
            String assureID = "fella";
            String dateDossier = txtDate.getText().toString();
            String heureDossier = txtTime.getText().toString();
            // personneTiers
            String lieuDossier = lieuA.getText().toString();
            String typeD = s1.getSelectedItem().toString();

            if(imageStoragePath == null && value.getImagePath() != null)    imageStoragePath = value.getImagePath();
            if(filmStoragePath == null && value.getVideoPath() != null)     filmStoragePath = Uri.parse(value.getVideoPath());
            Dossier thsDossier = new Dossier(numDossier, value.getAssureID(), dateDossier, heureDossier, personneTiers,
                    value.getMontantEstime(), etatDossier, typeD, lieuDossier, value.getMatricule(), imageStoragePath, filmStoragePath.toString(), value.getDegatPath());

            dossierDAO.supprimer(numDossier);

            thsDossier.setEtat("Envoyé");
            thsDossier.setVideoPath(null);
            thsDossier.setDegatPath(null);
            thsDossier.setImagePath(null);

            firebaseChild = MainActivity.mDatabase.child("Dossiers/"+String.valueOf(thsDossier.getId()));
            firebaseChild.setValue(thsDossier);

            onBackPressed();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = checkSelfPermission(android.Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);


                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);

                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


}
