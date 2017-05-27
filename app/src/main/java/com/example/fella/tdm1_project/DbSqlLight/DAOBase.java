package com.example.fella.tdm1_project.DbSqlLight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by RIMA on 21/05/2017.
 */

public abstract class DAOBase {
    protected final static int VERSION = 2;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "AssistAssurance.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
