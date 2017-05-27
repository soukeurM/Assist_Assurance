package com.example.fella.tdm1_project;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class LuncherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Intent intent;
        if (sharedPreferences.getString("username","null").equals("null"))
        {
            intent = new Intent(this,LoginActivity.class);
        } else {
            intent = new Intent(this,MainActivity.class);
            intent.putExtra("nvlConnexion",false);

        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            int hasWritePermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = checkSelfPermission(android.Manifest.permission.CAMERA);
            int hasBluetoothPermission = checkSelfPermission(android.Manifest.permission.BLUETOOTH);
            int hasBluetoothAdminPermission = checkSelfPermission(android.Manifest.permission.BLUETOOTH_ADMIN);
            int hasloation1Permission = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
            int hasloation2Permission = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);



            List<String> permissions = new ArrayList<String>();

            if (hasloation1Permission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }




            if (hasloation2Permission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            }



            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.CAMERA);

            }
            if (hasBluetoothPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.BLUETOOTH);

            }
            if (hasBluetoothAdminPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.BLUETOOTH_ADMIN);

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
