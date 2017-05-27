package com.example.fella.tdm1_project.DbSqlLight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.fella.tdm1_project.Dossier;
import com.example.fella.tdm1_project.PersonneTiers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RIMA on 21/05/2017.
 */

public class DossierDAO extends DAOBase{

    public static final String TABLE_NAME = "dossier";
    public static final String KEY = "id";
    public static final String DOSSIER_ASSUREID = "assureID";
    public static final String DOSSIER_DATE = "date";
    public static final String DOSSIER_HEURE = "heure";
    public static final String DOSSIER_MONTANT = "montantEstime";
    public static final String DOSSIER_ETAT = "etat";
    public static final String DOSSIER_TYPE = "typeAccident";
    public static final String DOSSIER_LIEU = "lieu";
    public static final String DOSSIER_MATRICULE = "matricule";

    public static final String PERSONNE_NOM = "nom";
    public static final String PERSONNE_PRENOM = "prenom";
    public static final String PERSONNE_ADRESSE = "adresse";
    public static final String PERSONNE_ASSURANCE = "assurance";
    public static final String PERSONNE_POLICE = "numPolice";
    public static final String PERSONNE_PERMIS = "numPermis";
    public static final String PERSONNE_TEL = "numTel";

    public static final String DOSSIER_IMAGE = "imagePath";
    public static final String DOSSIER_VIDEO = "videoPath";
    public static final String DOSSIER_DEGAT = "degatPath";


    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY + " TEXT PRIMARY KEY, "
            + DOSSIER_ASSUREID + " TEXT, "
            + DOSSIER_DATE + " TEXT, "
            + DOSSIER_HEURE + " TEXT,"
            + DOSSIER_MONTANT + " TEXT,"
            + DOSSIER_ETAT + " TEXT,"
            + DOSSIER_TYPE + " TEXT,"
            + DOSSIER_LIEU + " TEXT,"
            + DOSSIER_MATRICULE + " TEXT,"

            + PERSONNE_NOM + " TEXT,"
            + PERSONNE_PRENOM + " TEXT,"
            + PERSONNE_ADRESSE + " TEXT,"
            + PERSONNE_ASSURANCE + " TEXT,"
            + PERSONNE_POLICE + " TEXT,"
            + PERSONNE_PERMIS + " TEXT,"
            + PERSONNE_TEL + " TEXT, "

            + DOSSIER_IMAGE + " TEXT,"
            + DOSSIER_VIDEO + " TEXT,"
            + DOSSIER_DEGAT + " TEXT )";


    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public DossierDAO(Context pContext) {
        super(pContext);
        open();
        getDb().execSQL(TABLE_CREATE);
    }

    public void viderTable() {
        getDb().execSQL(TABLE_DROP);
    }

    public void ajouter(Dossier dossier) {
        // CODE
        ContentValues value = new ContentValues();
        value.put(KEY, dossier.getId());
        value.put(DOSSIER_ASSUREID, dossier.getAssureID());
        value.put(DOSSIER_DATE, dossier.getDate());
        value.put(DOSSIER_HEURE, dossier.getHeure());
        value.put(DOSSIER_MONTANT, dossier.getMontantEstime());
        value.put(DOSSIER_ETAT, dossier.getEtat());
        value.put(DOSSIER_TYPE, dossier.getTypeAccident());
        value.put(DOSSIER_LIEU, dossier.getLieu());
        value.put(DOSSIER_MATRICULE, dossier.getMatricule());

        if(dossier.getImagePath() != null)  value.put(DOSSIER_IMAGE, dossier.getImagePath().toString());
        if(dossier.getVideoPath() != null)  value.put(DOSSIER_VIDEO, dossier.getVideoPath());
        if(dossier.getDegatPath() != null)  value.put(DOSSIER_DEGAT, dossier.getDegatPath().toString());

        if(dossier.getInfoTiers() != null){
            value.put(PERSONNE_NOM, dossier.getInfoTiers().getNom());
            value.put(PERSONNE_PRENOM, dossier.getInfoTiers().getPrenom());
            value.put(PERSONNE_ADRESSE, dossier.getInfoTiers().getAdresse());
            value.put(PERSONNE_ASSURANCE, dossier.getInfoTiers().getAssurance());
            value.put(PERSONNE_POLICE, dossier.getInfoTiers().getNumPoliceContrat());
            value.put(PERSONNE_PERMIS, dossier.getInfoTiers().getNumPermis());
            value.put(PERSONNE_TEL, dossier.getInfoTiers().getNumTel());
        }


        mDb.insert(DossierDAO.TABLE_NAME, null, value);

    }


    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(String id) {
        // CODE
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {id});
    }


    public void modifier(Dossier dossier) {
        // CODE
        ContentValues value = new ContentValues();
        value.put(KEY, dossier.getId());
        value.put(DOSSIER_ASSUREID, dossier.getAssureID());
        value.put(DOSSIER_DATE, dossier.getDate());
        value.put(DOSSIER_HEURE, dossier.getHeure());
        value.put(DOSSIER_MONTANT, dossier.getMontantEstime());
        value.put(DOSSIER_ETAT, dossier.getEtat());
        value.put(DOSSIER_TYPE, dossier.getTypeAccident());
        value.put(DOSSIER_LIEU, dossier.getLieu());
        value.put(DOSSIER_MATRICULE, dossier.getMatricule());

        if(dossier.getInfoTiers() != null){
            value.put(PERSONNE_NOM, dossier.getInfoTiers().getNom());
            value.put(PERSONNE_PRENOM, dossier.getInfoTiers().getPrenom());
            value.put(PERSONNE_ADRESSE, dossier.getInfoTiers().getAdresse());
            value.put(PERSONNE_ASSURANCE, dossier.getInfoTiers().getAssurance());
            value.put(PERSONNE_POLICE, dossier.getInfoTiers().getNumPoliceContrat());
            value.put(PERSONNE_PERMIS, dossier.getInfoTiers().getNumPermis());
            value.put(PERSONNE_TEL, dossier.getInfoTiers().getNumTel());
        }

        if(dossier.getImagePath() != null)  value.put(DOSSIER_IMAGE, dossier.getImagePath().toString());
        if(dossier.getVideoPath() != null)  value.put(DOSSIER_VIDEO, dossier.getVideoPath());
        if(dossier.getDegatPath() != null)  value.put(DOSSIER_DEGAT, dossier.getDegatPath().toString());

        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {dossier.getId()});

    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public Dossier selectionner(String id) {
        // CODE
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + KEY +" = ?", new String[]{id});
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            Dossier dossier;
            PersonneTiers pers = new PersonneTiers(
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15)
            );

            if (cursor.getString(16) !=null && cursor.getString(18) != null){
                dossier = new Dossier(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        pers,
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        Uri.parse(cursor.getString(16)),
                        cursor.getString(17),
                        Uri.parse(cursor.getString(18))
                );
            }else{
                 dossier = new Dossier(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        pers,
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        null,
                        cursor.getString(17),
                        null
                );
            }
            return dossier;
        }else {
            return null;
        }


    }

    public List getDossiers() {    //String
        List<String> dossiers = new ArrayList<String>();

        // Name of the columns we want to select
        String[] tableColumns = new String[] {KEY, DOSSIER_ASSUREID, DOSSIER_DATE, DOSSIER_HEURE, DOSSIER_MONTANT, DOSSIER_ETAT,
                DOSSIER_TYPE, DOSSIER_LIEU, DOSSIER_MATRICULE, PERSONNE_NOM, PERSONNE_PRENOM, PERSONNE_ADRESSE, PERSONNE_ASSURANCE,
                PERSONNE_POLICE, PERSONNE_PERMIS, PERSONNE_TEL, DOSSIER_IMAGE, DOSSIER_VIDEO, DOSSIER_DEGAT};

        // Query the database
        Cursor cursor = mDb.query(TABLE_NAME, tableColumns, null, null, null, null, null, null);
        cursor.moveToFirst();

        // Iterate the results
        while (!cursor.isAfterLast()) {
            String dossier = cursor.getString(0);   // ID DOSSIER

            // Add to the DB
            dossiers.add(dossier);

            // Move to the next result
            cursor.moveToNext();
        }
        return dossiers;
    }
}


