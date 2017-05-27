package com.example.fella.tdm1_project;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
@SuppressWarnings("serial")


public class Assure implements Serializable {

    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String numTel;
    private String mdp;
    private Permis infosPermis=new Permis();




    public Assure(){}

    public Assure (String nomA, String prenomA, String adresseA,String emailA,String numPermisA, String typePemisA, String numTel,String mdp) {
        super();

        this.nom=nomA;
        this.prenom =prenomA;
        this.adresse= adresseA;
        this.email=emailA;
        this.numTel=numTel;
        this.infosPermis.numPermis=numPermisA;
        this.infosPermis.typePermis=typePemisA;
        this.mdp=mdp;



    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }


    public String getNumTel() {
        return numTel;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public Permis getInfosPermis() {
        return infosPermis;
    }

}