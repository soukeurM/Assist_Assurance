package com.example.fella.tdm1_project;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.BoolRes;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class liste_vehicule_activity extends AppCompatActivity implements TextWatcher {


    private int nbrChampsRempli=0;
    private boolean champsDejasRemplis=false;
    private Button buttonValider;
    int position;

    private  EditText v1;
    private  EditText v2;
    private  EditText v3;
    private  EditText v4;
    private  EditText v5;
    private  EditText v6;
    private String modifV;

    private ListView mCompleteListView;
    private Button mAddItemToList;
    private ArrayList<Vehicule> mItems;
    private ArrayList<Vehicule> msauv;

    private CompleteListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_vehicules);
        getSupportActionBar().show();
        setTitle("Mes véhicules");
        initViews();

        v2 = (EditText) findViewById(R.id.ModeleV);
        v1 = (EditText) findViewById(R.id.MarqueV);
        v3 = (EditText) findViewById(R.id.NumContrat);
        v4 = (EditText) findViewById(R.id.NumV1);
        v5 = (EditText) findViewById(R.id.NumV2);
        v6 = (EditText) findViewById(R.id.NumV3);


        if(isTwopane()){
            v1.addTextChangedListener(this);
            v2.addTextChangedListener(this);
            v3.addTextChangedListener(this);
            v4.addTextChangedListener(this);
            v5.addTextChangedListener(this);
            v6.addTextChangedListener(this);
        }


        buttonValider = (Button) findViewById(R.id.validerAdd);

        position=-1;

        mItems = new ArrayList<Vehicule>();
        mListAdapter = new CompleteListAdapter(this, mItems);
        mCompleteListView.setAdapter(mListAdapter);

        // Ajout d'une liste statique de véhicules

        Vehicule vh1= new Vehicule(1,"Mercedes","CLA",1232123,251211316);
        Vehicule vh2= new Vehicule(1,"Peugeot","3008",1200000,251211426);
        Vehicule vh3= new Vehicule(1,"BMW","X6",4321276,547711723);

        addItemsToList(vh1);
        addItemsToList(vh2);
        addItemsToList(vh3);

        //----------------------------------------------------------------------------------

        // Afficher les véhicules ajoutés dynamiquement en cas d'orientation de l'écran
        if (savedInstanceState != null) {
            mItems = new ArrayList<Vehicule>();
            msauv = savedInstanceState.getParcelableArrayList("VehiculesSauv");
            for (int i = 0; i < msauv.size(); i++) {
                addItemsToList(msauv.get(i));
            }
        }
        else if(!isTwopane()){

            //----------------------------------------------------------------------------------

            // Modifier ou Ajouter Un nouveau véhicule à la liste , on devrait vérifier si on vient de l'activité de l'ajout

            Bundle bund = getIntent().getExtras();
            String nouveauV = bund.getString("nouveauV", "");
            int positionV = bund.getInt("position");

            if (nouveauV.equals("true")) {
                Intent i = getIntent();
                Vehicule vi = (Vehicule) i.getParcelableExtra("sampleObject");
                if (positionV > -1 && positionV < 3) {

                    // Dans le cas de modification
                    mItems.remove(positionV);
                }

                addItemsToList(vi);

            }

        }

        mCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Vehicule vehecule =mItems.get(position);
                if(isTwopane()){
                    Bundle bund = getIntent().getExtras();


                    position= bund.getInt("positionV");
                    Intent i = getIntent();
                    Vehicule vi = vehecule;
                    //(Vehicule) i.getParcelableExtra("vehiculeModif");

                    int partie3matricule= (int) vi.getMatricule()%100;
                    int partie2matricule= (int) ((vi.getMatricule()/100)%1000);
                    int partie1matricule= (int) vi.getMatricule()/100000;

                    v1.setText(vi.getMarque());
                    v2.setText(vi.getModele());
                    v3.setText(Integer.toString(vi.getContrat()));
                    v4.setText(Integer.toString(partie1matricule));
                    v5.setText(Integer.toString(partie2matricule));
                    v6.setText(Integer.toString(partie3matricule));
                }else{

                    Intent intent = new Intent(getApplicationContext(), Vehiclue_Activity.class);
                    intent.putExtra("vehiculeModif",vehecule);
                    intent.putExtra("From","ListVehiculeForModifi");
                    intent.putExtra("positionV",position);

                    startActivity(intent);
                }

            }
        });


    }

    public boolean isTwopane(){
        View view = findViewById(R.id.item);
        return (view != null);

    }
    @Override
    public void onSaveInstanceState(final Bundle outState) {

        outState.putParcelableArrayList("VehiculesSauv",mItems);
        super.onSaveInstanceState(outState);



    }

    private void initViews() {
        mCompleteListView = (ListView) findViewById(R.id.completeList);
    }

    public void chargerVehicule(View v) {

        if (v == findViewById(R.id.addVehicule)) {
            Intent  intent = new Intent(this, Vehiclue_Activity.class);
            intent.putExtra("From","ListVehiculeForAjout");
            startActivity(intent);
        }

    }

    private void addItemsToList(Vehicule vi) {

        mItems.add(vi);
        mListAdapter.notifyDataSetChanged();

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
                buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.colorAccent));

            }
        } else {
            nbrChampsRempli--;
            buttonValider.setEnabled(false);
            buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));

        }
    }


    public void ReponseAjout(View view) {

        if (view == findViewById(R.id.validerAdd)) {
            Random rn =new Random();
            int id= rn.nextInt()% 20;
            Vehicule vec = new Vehicule(id,v1.getText().toString(),v2.getText().toString(),Integer.parseInt(v3.getText().toString()),Long.parseLong( v4.getText().toString()+v5.getText().toString()+v6.getText().toString()));
            Intent intent = new Intent(this, liste_vehicule_activity.class);
            intent.putExtra("sampleObject", vec);
            intent.putExtra("nouveauV", "true");
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }


}
