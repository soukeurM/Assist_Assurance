package com.example.fella.tdm1_project;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
@SuppressWarnings("serial")


public class InfoDetaille implements Serializable {

    private String dateAccident;
    private String HeureAccident;
    private String LieuS;
    private boolean existBlesses;
    private boolean AutrDegat;




    public InfoDetaille (String p1, String p2, String p3, boolean b1, boolean b2) {
        super();
     this.dateAccident=p1;
        this.HeureAccident=p2;
        this.LieuS=p3;
        this.existBlesses =b1;
        this.AutrDegat=b2;
    }

    public String getDateAccident() {
        return dateAccident;
    }

    public String getHeureAccident() {
        return HeureAccident;
    }

    public String getLieuS() {
        return LieuS;
    }
    public boolean ifExisetBless()
    {
        return this.existBlesses;
    }

    public boolean getAutreeDegat()
    {
        return this.AutrDegat;
    }
}