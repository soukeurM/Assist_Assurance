package com.example.fella.tdm1_project;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
@SuppressWarnings("serial")


public class personne implements Serializable {

    private String nom;
    private String prenom;
    private String Adresse;
    private String Assurance;
    private int numPolice;
    private int numPermis;
    private int numTel;






    public personne (String p1, String p2, String p3, String p4, int i1, int i2, int i3) {
        super();
        this.nom=p1;
        this.prenom =p2;
        this.Adresse= p3;
        this.Assurance=p4;
        this.numPolice=i1;
        this.numPermis=i2;
        this.numTel=i3;


    }

    public int getNumPermis() {
        return numPermis;
    }

    public int getNumPolice() {
        return numPolice;
    }

    public int getNumTel() {
        return numTel;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getAssurance() {
        return Assurance;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}