package com.Activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import DataManagement.FavoritesDataContainer;

/**
 * Created by Joey on 2/26/2016.
 */
public class FavoritesItemsAdapter extends ArrayAdapter<FavoritesDataContainer> {

    private Context cont;

    public FavoritesItemsAdapter(Context context, int resource, List<FavoritesDataContainer> objects) {
        super(context, resource, objects);
        cont = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(android.R.layout.simple_list_item_1, null);

        Log.v("FavoritesItemsAdapter", getItem(position).favName);

        //Set our text
        TextView theText = (TextView) view.findViewById(android.R.id.text1);
        theText.setText(getItem(position).favName);
        return view;
    }

}
