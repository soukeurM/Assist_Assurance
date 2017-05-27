package com.example.fella.tdm1_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class liste_vehicule_activity extends AppCompatActivity{

    int position;
    private ListView mCompleteListView;
    private ArrayList<Vehicule> mItems;
    private CompleteListAdapter mListAdapter;
    String userName;
    DatabaseReference vehiculesListe;
    DatabaseReference mDatabase;
    static Vehicule vehicule=new Vehicule();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_vehicules);
        getSupportActionBar().show();
        setTitle("Mes véhicules");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName=sharedPreferences.getString("username","null");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        vehiculesListe = mDatabase.child("vehicules");

        initViews();
         ValueEventListener valueEventListener = vehiculesListe.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mItems.clear();
                for (DataSnapshot vehiculeItem : snapshot.getChildren()) {

                   if(vehiculeItem.child("propriétaire").getValue().equals(userName))

                    {
                        vehicule =new Vehicule();
                        vehicule= (vehiculeItem.getValue(Vehicule.class));
                        vehicule.setIdVehicule(vehiculeItem.getKey().toString());

                        mItems.add(vehicule);

                    }

                }
                mListAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



          mCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                    Vehicule vehicule =mItems.get(position);
                    Intent intent = new Intent(getApplicationContext(), Detail_Vehicule_Activity.class);
                    intent.putExtra("vehiculeModif",vehicule);
                    intent.putExtra("positionV",position);
                    startActivity(intent);

            }
        });


    }


    @Override
    public void onSaveInstanceState(final Bundle outState) {

        outState.putParcelableArrayList("VehiculesSauv",mItems);
        super.onSaveInstanceState(outState);



    }

    private void initViews() {
        mCompleteListView = (ListView) findViewById(R.id.completeList);
        position=-1;
        mItems = new ArrayList<Vehicule>();
        mListAdapter = new CompleteListAdapter(this, mItems);
        mCompleteListView.setAdapter(mListAdapter);
    }

    public void chargerVehicule(View v) {

        if (v == findViewById(R.id.addVehicule)) {
            Intent  intent = new Intent(this, Vehiclue_Activity.class);
            intent.putExtra("From","ListVehiculeForAjout");
            startActivity(intent);
        }

    }







}
