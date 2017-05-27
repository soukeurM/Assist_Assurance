package com.example.fella.tdm1_project;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Fella on 06/04/2017.
 */

public class TypesAccident extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Type de l'accident");
        setContentView(R.layout.types_accident);
    }



    public void chargerActivity(View view){
        Intent intent=null;
        intent = new Intent(this, Partie1Declaration.class);

        switch (view.getId())
        {
            case R.id.button5:
                intent.putExtra("typeAccident",1 );
                break;

            case R.id.button6 :
                intent.putExtra("typeAccident",2 );
                break;
            case R.id.button7 :
                intent.putExtra("typeAccident",3 );
                break;
            case R.id.button8 :
                intent.putExtra("typeAccident",4 );
                break;        }
        startActivity(intent);


    }



}
