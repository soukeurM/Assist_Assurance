package com.example.fella.tdm1_project;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
@SuppressWarnings("serial")


public class PersonneTiers implements Serializable {

    private String nom;
    private String prenom;
    private String adresse;
    private String numTel;
    private String assurance;
    private String numPoliceContrat;
    private String  numPermis;




    public PersonneTiers(){}

    public PersonneTiers (String nomA, String prenomA, String adresseA,String numPermisA, String numTel,String numContrat, String assuranceA) {
        super();

        this.nom=nomA;
        this.prenom =prenomA;
        this.adresse= adresseA;
        this.numTel=numTel;
        this.numPoliceContrat=numContrat;
        this.numPermis=numPermisA;
        this.assurance= assuranceA;


    }

    public String getNumPermis() {
        return numPermis;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getAssurance() {
        return assurance;
    }

    public String getNom() {
        return nom;
    }

    public String getNumPoliceContrat() {
        return numPoliceContrat;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getPrenom() {
        return prenom;
    }

}