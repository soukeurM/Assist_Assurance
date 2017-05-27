package com.example.fella.tdm1_project;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;



public class Partie1Declaration extends ActionBarActivity  implements TextWatcher {
    private Spinner spinner;
    private  EditText v1;
    private  EditText v2;
    private  EditText v3;
    private  EditText v4;
    private  EditText v5;
    private  EditText v6;
    private int nbrChampsRempli=6;
    private boolean champsDejasRemplis=false;
    private Button buttonValider;

    private ArrayList<Vehicule> mItems;


    private CompleteListAdapter mListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Véhicule accidenté");
        setContentView(R.layout.partie1_declaration);

        Vehicule[] vecs = new Vehicule[3];


        ArrayList<Vehicule> ListeV = new ArrayList<>();

        Vehicule vh1= new Vehicule(1,"Mercedes","CLA",1232123,251211316);
        Vehicule vh2= new Vehicule(2,"Peugeot","3008",1200000,251211426);
        Vehicule vh3= new Vehicule(3,"BMW","X6",4321276,547711723);
        Vehicule vh4= new Vehicule(4,"","Autre",0,0);

        ListeV.add(vh1);
        ListeV.add(vh2);
        ListeV.add(vh3);
        ListeV.add(vh4);

        if(isTwopane()){
            ListView listView = (ListView)findViewById(R.id.list_voiture);
            mItems = new ArrayList<Vehicule>();
            mListAdapter = new CompleteListAdapter(this, mItems);
            listView.setAdapter(mListAdapter);

            addItemsToList(vh1);
            addItemsToList(vh2);
            addItemsToList(vh3);
            addItemsToList(vh4);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    v1.setEnabled(false);
                    v2.setEnabled(false);
                    v3.setEnabled(false);
                    v4.setEnabled(false);
                    v5.setEnabled(false);
                    v6.setEnabled(false);

                    whenSelect(arg2);
                }


            });

        }else{
            spinner = (Spinner) findViewById(R.id.VehiculeL);
            ArrayAdapter<Vehicule> adapter =
                    new ArrayAdapter<Vehicule>(getApplicationContext(),R.layout.chpspinner, ListeV);
            adapter.setDropDownViewResource(R.layout.chpspinner);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    v1.setEnabled(false);
                    v2.setEnabled(false);
                    v3.setEnabled(false);
                    v4.setEnabled(false);
                    v5.setEnabled(false);
                    v6.setEnabled(false);

                    whenSelect(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }

            });
        }




        v2 = (EditText) findViewById(R.id.ModeleV);
        v1 = (EditText) findViewById(R.id.MarqueV);
        v3 = (EditText) findViewById(R.id.NumContrat);
        v4 = (EditText) findViewById(R.id.NumV1);
        v5 = (EditText) findViewById(R.id.NumV2);
        v6 = (EditText) findViewById(R.id.NumV3);


        v2.setText("CLA");
        v1.setText("Mercedes");
        v3.setText("1232123");
        v4.setText("2512");
        v5.setText("113");
        v6.setText("16");

        v1.addTextChangedListener(this);
        v2.addTextChangedListener(this);
        v3.addTextChangedListener(this);
        v4.addTextChangedListener(this);
        v5.addTextChangedListener(this);
        v6.addTextChangedListener(this);

        buttonValider = (Button) findViewById(R.id.butSuiv1);



    }

    public void whenSelect(int position){
        switch (position)
        {
            case 0:
                v2.setText("CLA");
                v1.setText("Mercedes");
                v3.setText("1232123");
                v4.setText("2512");
                v5.setText("113");
                v6.setText("16");
                break;

            case 1:
                v2.setText("3008");
                v1.setText("Peugeot");
                v3.setText("1200000");
                v4.setText("2512");
                v5.setText("114");
                v6.setText("26");
                break;

            case 2:
                v2.setText("X6");
                v1.setText("BMW");
                v3.setText("4321276");
                v4.setText("5477");
                v5.setText("117");
                v6.setText("23");
                break;
            case 3:
                v1.setText("");
                v1.setEnabled(true);

                v2.setText("");
                v2.setEnabled(true);

                v3.setText("");
                v3.setEnabled(true);

                v4.setText("");
                v4.setEnabled(true);

                v5.setText("");
                v5.setEnabled(true);

                v6.setText("");
                v6.setEnabled(true);


                break;

        }
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        if (s.toString().trim().length() > 0) {
            champsDejasRemplis=true;
        }
        else
        {
            champsDejasRemplis=false;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {



    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().trim().length() > 0) {

            if (champsDejasRemplis==false) {nbrChampsRempli++;}

            if(nbrChampsRempli==6)
            {
                buttonValider.setEnabled(true);

            }
        } else {
            nbrChampsRempli--;

            buttonValider.setEnabled(false);

        }
    }


    public void chargerActivity(View view){
        int typeAccident= getIntent().getExtras().getInt("typeAccident");
        Intent intent=null;
        switch (view.getId()) {


            case R.id.butSuiv1:
                intent = new Intent(this, DetailsAccident.class);
                Vehicule vec = new Vehicule(1,v1.getText().toString(),v2.getText().toString(),Integer.parseInt(v3.getText().toString()),Long.parseLong( v4.getText().toString()+v5.getText().toString()+v6.getText().toString()));
                intent.putExtra("VehiculeAccidenté", vec);
                intent.putExtra("typeAccident",typeAccident );
                break;
        }
        startActivity(intent);
    }


    public boolean isTwopane(){
        View view = findViewById(R.id.details);
        return (view != null);

    }


    private void addItemsToList(Vehicule vi) {

        mItems.add(vi);
        mListAdapter.notifyDataSetChanged();

    }
}