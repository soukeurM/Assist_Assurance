package com.example.fella.tdm1_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import layout.IP_assure;
import layout.IP_conducteur;
import layout.IP_observations;
import layout.IP_societe;
import layout.IP_vehicule;


public class Infos_Pers_Activity extends ActionBarActivity  {

    private ViewPager pager;
    private AppSectionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perso);
        adapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.vpPager);
        pager.setAdapter(adapter);
    }




    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        private static int NUM_ITEMS = 5;

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0 :
                    return new IP_assure();
                case 1 :
                    return new IP_societe();
                case 2 :
                    return new IP_vehicule();
                case 3 :
                    return new IP_conducteur();
                case 4 :
                    return new IP_observations();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position)
            {
                case 0 :
                    return "Assuré";
                case 1 :
                    return "Société d'assurance";
                case 2 :
                    return "Véhicule";
                case 3 :
                    return "Conducteur";
                case 4 :
                    return "Dégâts apparants";

            }
            return "";
        }
    }

    void chargerSuivant(View view) {
        if (view == (View) findViewById(R.id.button6)) {

            DétailActivity();

        }

    }
    void chargerPrecedent(View view) {
        if (view == (View) findViewById(R.id.button5)) {
            TypesAccidentActivity();

        }
    }



   private void TypesAccidentActivity() {
        Intent intent = new Intent(this, TypesAccident.class);
        startActivity(intent);
    }


    private void DétailActivity() {
        Intent intent = new Intent(this, DetailsAccident.class);
        startActivity(intent);
    }
}
