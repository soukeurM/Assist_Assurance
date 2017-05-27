package com.example.fella.tdm1_project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fella.tdm1_project.DbSqlLight.DossierDAO;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by RIMA on 16/04/2017.
 */

public class DegatsApparents extends AppCompatActivity {
    Bitmap myBitmap;
    public static Uri degatImage;
    DatabaseReference firebaseChild;
    private DossierDAO dossierDAO;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Dégats apparents");
        setContentView(R.layout.degats);


        btnSave = (Button)findViewById(R.id.btn_save);
        dossierDAO = new DossierDAO(this);

        final ImageView imageView = (ImageView) findViewById(R.id.img);
        final RelativeLayout rr = (RelativeLayout) findViewById(R.id.imager);
        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    ImageView iv = new ImageView(getApplicationContext());
                    lp.setMargins(x, y, 0, 0);
                    iv.setLayoutParams(lp);
                    iv.setImageDrawable(getResources().getDrawable(
                            R.drawable.croix));
                    rr.addView(iv);
                }
                return false;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envoyerMail();
                MainActivity.dossier.setEtat("Ouvert");
                MainActivity.dossier.setImagePath(DetailsAccident.imageStoragePath);
                MainActivity.dossier.setDegatPath(degatImage);
                MainActivity.dossier.setVideoPath(DetailsAccident.filmStoragePath.toString());
                dossierDAO.ajouter(MainActivity.dossier);

                Intent intent = new Intent(DegatsApparents.super.getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

    public void saveBitmap(Bitmap bitmap) {

        File imagePath =  getFilePath();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            //sendMail(filePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        degatImage = Uri.fromFile(imagePath);

    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "receiver@website.com" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Truiton Test Mail");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "This is an autogenerated mail from Truiton's InAppMail app");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void envoyerMail(){
        View v1 = findViewById(R.id.imager);
        // View v1 = iv.getRootView(); //even this works
        // View v1 = findViewById(android.R.id.content); //this works too
        // but gives only content
        v1.setDrawingCacheEnabled(true);
        myBitmap = v1.getDrawingCache();
        saveBitmap(myBitmap);
    }

    public void chargerActivity(View view){
        switch (view.getId()) {

            case R.id.butSuiv6:
                envoyerMail();

                MainActivity.dossier.setEtat("Envoyé");

                firebaseChild = MainActivity.mDatabase.child("Dossiers/"+String.valueOf(MainActivity.dossier.getId()));
                firebaseChild.setValue(MainActivity.dossier);

                //StorageReference mStorage = FirebaseStorage.getInstance().getReference();
                StorageReference filePath = MainActivity.mStorage.child("Medias").child(String.valueOf(MainActivity.dossier.getId()));

                filePath.child("Image").putFile(DetailsAccident.imageStoragePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(DegatsApparents.super.getApplicationContext(), "Image enregistrée", Toast.LENGTH_SHORT).show();
                    }
                });

                filePath.child("degats_apparents").putFile(DegatsApparents.degatImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(DegatsApparents.super.getApplicationContext(), "Deagts enregistrée", Toast.LENGTH_SHORT).show();

                    }
                });

                filePath.child("Video").putFile(DetailsAccident.filmStoragePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(DegatsApparents.super.getApplicationContext(), "Vidéo enregistrée", Toast.LENGTH_SHORT).show();

                    }
                });

                Intent intent = new Intent(DegatsApparents.super.getApplicationContext(), MainActivity.class);
                startActivity(intent);

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
                builder.setTitle("Attention!");
                builder.setMessage("Êtes vous sûr d'annuler la déclaration?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();

                        Intent intent = new Intent(DegatsApparents.super.getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
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
