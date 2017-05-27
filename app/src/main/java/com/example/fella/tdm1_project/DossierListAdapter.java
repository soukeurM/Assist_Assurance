package com.example.fella.tdm1_project;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DossierListAdapter extends BaseAdapter {
    private Activity mContext;
    private List<Dossier> mList;
    private LayoutInflater mLayoutInflater = null;
    String matricule="";
    public DossierListAdapter(Activity context, List<Dossier> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.fragment_champs_dossier,null);
            viewHolder = new ListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ListViewHolder) v.getTag();
        }

        Dossier dossier = mList.get(position);
        matricule = dossier.getMatricule();
        viewHolder.mTVItem.setText(matricule.substring(0, matricule.length()-5)+"-"+matricule.substring(matricule.length()-5, matricule.length()-2)+"-"+matricule.substring(matricule.length()-2, matricule.length()));
        viewHolder.mMarqueItem.setText(dossier.getTypeAccident());

        return v;
    }





}

class ListViewHolder {
    public TextView mTVItem;
    public TextView mMarqueItem;
    public ListViewHolder(View base) {
        mTVItem = (TextView) base.findViewById(R.id.typeAcc);
        mMarqueItem= (TextView) base.findViewById(R.id.marqueVeh);

    }
}