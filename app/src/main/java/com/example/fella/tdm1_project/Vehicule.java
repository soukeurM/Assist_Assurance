package com.example.fella.tdm1_project;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings

public class Vehicule implements  Parcelable {


    private String marque;
    private String modele;
    private String numContrat;
    private String matricule;
    private String propriétaire;
    private String idVehicule;


    public Vehicule(){}

    public Vehicule(String marqu, String model,String mContrat,String matricul,String prop) {
        this.marque = marqu;
        this.modele = model;
        this.numContrat = mContrat;
        this.matricule = matricul;
        this.propriétaire=prop;
    }

    public String getModele() {
        return this.modele;
    }

    public String getMarque() {
        return this.marque;
    }


    public String getMatricule() {
        return this.matricule;
    }

    public String getNumContrat() {return this.numContrat;}

    public String getIdVehicule() {return this.idVehicule;}

    public void setIdVehicule(String idVehicule) {this.idVehicule = idVehicule;}

    public String getPropriétaire() {
        return this.propriétaire;
    }

    public static final Creator<Vehicule> CREATOR = new Creator<Vehicule>() {
        @Override
        public Vehicule createFromParcel(Parcel in) {
            return new Vehicule(in);
        }

        @Override
        public Vehicule[] newArray(int size) {
            return new Vehicule[size];
        }
    };


    // Parcelling part
    public Vehicule(Parcel in){

        //retrieve
        //this.idVehicule = in.readInt();
        this.marque = in.readString();
        this.modele = in.readString();
        this.numContrat = in.readString();
        this.matricule = in.readString();
        this.idVehicule = in.readString();


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marque);
        dest.writeString(modele);
        dest.writeString(numContrat);
        dest.writeString(matricule);
        dest.writeString(idVehicule);
    }

    @Override
    public String toString() {
        return modele;
    }
}