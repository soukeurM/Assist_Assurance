package com.example.fella.tdm1_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fella.tdm1_project.R;

public class Circonstance_Activity extends ActionBarActivity  implements RadioGroup.OnCheckedChangeListener{


    private ViewPager pager;
    private Infos_Pers_Activity.AppSectionsPagerAdapter adapter;
    private int typeAccident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Circonstances de l'accident");
        typeAccident= getIntent().getExtras().getInt("typeAccident");

        switch (typeAccident) {
            case 3:
                setContentView(R.layout.fragment_circonstance_incendie);
                break;
            case 4:
                setContentView(R.layout.fragment_circonstances_brise_de_glace);
                break;
            case 1:
                setContentView(R.layout.fragment_circonstances__avec__adversaire);
                break;
            case 2:
                setContentView(R.layout.fragment_circonstances_vol);
                break;


        }
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        // TODO Auto-generated method stub
        RadioButton rEnCours=(RadioButton)findViewById(checkedId);

        Toast.makeText(Circonstance_Activity.this,rEnCours.getText(), Toast.LENGTH_SHORT).show();
    }

    public void chargerActivity(View view){
        Intent i = getIntent();
        Vehicule vi = (Vehicule) i.getParcelableExtra("VehiculeAccidenté");
        int typeAccident= getIntent().getExtras().getInt("typeAccident");
        InfoDetaille diV = (InfoDetaille) i.getSerializableExtra("detailAccident");
        personne pers = (personne) i.getSerializableExtra("PersonneTiers");



        Intent intent=null;
        switch (view.getId()) {

            case R.id.butSuiv3:

                intent = new Intent(this, Description_Accident.class);
                intent.putExtra("PersonneTiers", pers);
                intent.putExtra("VehiculeAccidenté", vi);
                intent.putExtra("detailAccident", diV);
                intent.putExtra("typeAccident",typeAccident );

                break;
        }
        startActivity(intent);
    }

}
