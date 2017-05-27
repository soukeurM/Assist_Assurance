package com.example.fella.tdm1_project;

import android.net.Uri;
import android.text.style.SuperscriptSpan;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by RIMA on 18/05/2017.
 */

public class Dossier {
    private String id;
    private String assureID;
    private String date;
    private String heure;
    private PersonneTiers infoTiers;
    private String montantEstime;
    private String etat;
    private String typeAccident;
    private String lieu;
    private String matricule;

    private Uri imagePath;
    private String videoPath;
    private Uri degatPath;

    public Dossier() {
        int min = 100000;
        int max = 999999;
        Random rn = new Random();
        int n = max - min + 1;
        int i = rn.nextInt() % n;
        this.id =  String.valueOf(Math.abs(min + i));

        this.montantEstime = "0";
        this.assureID = "fella";
        this.infoTiers = null;
    }

    public Dossier(String id, String assureID, String date, String heure, PersonneTiers infoTiers, String montantEstime, String etat,
                   String typeAccident, String lieu, String matricule, Uri imagePath, String videoPath, Uri degatPath) {
        this.id = id;
        this.assureID = assureID;
        this.date = date;
        this.heure = heure;
        this.infoTiers = infoTiers;
        this.montantEstime = montantEstime;
        this.etat = etat;
        this.typeAccident = typeAccident;
        this.lieu = lieu;
        this.matricule = matricule;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.degatPath = degatPath;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Uri getDegatPath() {
        return degatPath;
    }

    public void setDegatPath(Uri degatPath) {
        this.degatPath = degatPath;
    }

    public String getAssureID() {

        return assureID;
    }

    public void setAssureID(String assureID) {
        this.assureID = assureID;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(String montantEstime) {
        this.montantEstime = montantEstime;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public PersonneTiers getInfoTiers() {
        return infoTiers;
    }

    public void setInfoTiers(PersonneTiers infoTiers) {
        this.infoTiers = infoTiers;
    }


    public String getTypeAccident() {
        return typeAccident;
    }

    public void setTypeAccident(String typeAccident) {
        this.typeAccident = typeAccident;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
}
