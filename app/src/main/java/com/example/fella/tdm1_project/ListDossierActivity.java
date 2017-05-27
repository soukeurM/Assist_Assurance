package com.example.fella.tdm1_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fella.tdm1_project.DbSqlLight.DossierDAO;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RIMA on 20/05/2017.
 */

public class ListDossierActivity extends AppCompatActivity {

    private List<Dossier> dossiers = new ArrayList<Dossier>();
    private List<Dossier> tous = new ArrayList<Dossier>();
    private DossierDAO dossierDAO;
    private String unEtat="#";
    DatabaseReference dossierList;
    int position;
    private ListView mCompleteListView;
    private DossierListAdapter mListAdapter;
    static Dossier value = new Dossier();
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Liste des dossiers");
        setContentView(R.layout.dossier_activity);

        initViews();

        dossierDAO = new DossierDAO(this);
        dossierList = MainActivity.mDatabase.child("Dossiers");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName=sharedPreferences.getString("username","null");

        ValueEventListener valueEventListener = dossierList.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                dossiers.clear();
                for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("assureID").getValue().equals(userName)) {
                        value = (dataSnapshot.getValue(Dossier.class));
                        tous.add(value);

                        if (dataSnapshot.child("etat").getValue().equals("En traitement")) {
                            Dossier doss = dataSnapshot.getValue(Dossier.class);
                            dossiers.add(doss);
                        }

                    }
                    mListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dossiers.addAll(dossierDAO.getDossiers());


        mCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, android.view.View v, int position,
                                    long id) {
                Dossier dossierA = dossiers.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailsDossier.class);
                intent.putExtra("noDossier", dossierA.getId());
                intent.putExtra("typeDossier", "#");
                if(MainActivity.debut == true){
                    List<String> strings = dossierDAO.getDossiers();
                    if(contient(dossierA.getId(), strings)){
                        unEtat = "Ouvert";
                    }
                    else{
                        unEtat = "En traitement";
                    }
                }
                intent.putExtra("unEtat", unEtat);

                startActivity(intent);

            }
        });

    }

    private void initViews() {
        mCompleteListView = (ListView) findViewById(R.id.completeList);
        position=-1;
        dossiers = new ArrayList<Dossier>();
        mListAdapter = new DossierListAdapter(this, dossiers);
        mCompleteListView.setAdapter(mListAdapter);
    }

    public boolean contient(String s, List<String> strings){
        boolean trouve = false;
        int i=0;
        while(i < strings.size() && !trouve){
            if(strings.get(i).equals(s))    trouve = true;
            i++;
        }
        return trouve;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_envoye:
                MainActivity.debut = false;
                dossiers = new ArrayList<Dossier>();
                for(int i=0; i<tous.size(); i++) {
                    if (tous.get(i).getEtat().equals("Envoyé")) {
                        dossiers.add(tous.get(i));
                    }
                }
                mListAdapter = new DossierListAdapter(this, dossiers);
                mCompleteListView.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
                unEtat = "Envoyé";
                return true;

            case R.id.action_ouvert:
                MainActivity.debut = false;
                dossiers = new ArrayList<Dossier>();
                for(int i=0; i<tous.size(); i++) {
                    if (tous.get(i).getEtat().equals("Ouvert")) {
                        dossiers.add(tous.get(i));
                    }
                }
                mListAdapter = new DossierListAdapter(this, dossiers);
                mCompleteListView.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
                unEtat = "Ouvert";
                return true;

            case R.id.action_en_traitement:
                MainActivity.debut = false;
                dossiers = new ArrayList<Dossier>();
                for(int i=0; i<tous.size(); i++) {
                    if (tous.get(i).getEtat().equals("En traitement")) {
                        dossiers.add(tous.get(i));
                    }
                }
                mListAdapter = new DossierListAdapter(this, dossiers);
                mCompleteListView.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
                unEtat = "En traitement";
                return true;

            case R.id.action_accepte:
                MainActivity.debut = false;
                dossiers = new ArrayList<Dossier>();
                for(int i=0; i<tous.size(); i++) {
                    if (tous.get(i).getEtat().equals("Accepté")) {
                        dossiers.add(tous.get(i));
                    }
                }
                mListAdapter = new DossierListAdapter(this, dossiers);
                mCompleteListView.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
                unEtat = "Accepté";
                return true;

            case R.id.action_refuse:
                MainActivity.debut = false;
                dossiers = new ArrayList<Dossier>();
                for(int i=0; i<tous.size(); i++) {
                    if (tous.get(i).getEtat().equals("Refusé")) {
                        dossiers.add(tous.get(i));
                    }
                }
                mListAdapter = new DossierListAdapter(this, dossiers);
                mCompleteListView.setAdapter(mListAdapter);
                mListAdapter.notifyDataSetChanged();
                unEtat = "Refusé";
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
