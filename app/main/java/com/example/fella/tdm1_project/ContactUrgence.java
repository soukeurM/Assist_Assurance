package com.example.fella.tdm1_project;

import java.io.Serializable;

/**
 * Created by RIMA on 15/04/2017.
 */

public class ContactUrgence implements Serializable {
    private String nom;
    private String num;

    public ContactUrgence(String nom, String num){
        this.nom = nom;
        this.num = num;
    }

    public String getNom(){
        return this.nom;
    }

    public String getNum(){
        return this.num;
    }
}
