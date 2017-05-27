package com.example.fella.tdm1_project;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings

public class Vehicule implements  Parcelable {

    private int idVehicule;
    private String marque;
    private String modele;
    private int numContrat;
    private long  matricule;

    public Vehicule(int id, String marqu, String model,
                    int mCOntrat, long matricul) {
        super();
        this.idVehicule=id;
        this.marque = marqu;
        this.modele = model;
        this.numContrat = mCOntrat;
        this.matricule = matricul;

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

    public String getModele() {
        return this.modele;
    }

    public String getMarque() {
        return this.marque;
    }

    public int getContrat() {
        return this.numContrat;
    }

    public long getMatricule() {
        return this.matricule;
    }

    public int getIdVehicule() {
        return this.idVehicule;
    }



    // Parcelling part
    public Vehicule(Parcel in){

        //retrieve
        this.idVehicule = in.readInt();
        this.marque = in.readString();
        this.modele = in.readString();
        this.numContrat = in.readInt();
        this.matricule = in.readLong();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idVehicule);
        dest.writeString(marque);
        dest.writeString(modele);
        dest.writeInt(numContrat);
        dest.writeLong(matricule);
    }

    @Override
    public String toString() {
        return modele;
    }
}