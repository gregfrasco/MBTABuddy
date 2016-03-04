package com.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import DataManagement.DataStorageManager;
import mbta.Station;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 3/4/2016.
 */
public class SearchResultListItemAdapter extends ArrayAdapter<String> {
    Context cont;
    HashMap<String,String> stationIds;

    public SearchResultListItemAdapter(Context context, int resource, List<String> objects,
                                       HashMap<String, String> stationIdForName) {
        super(context, resource, objects);
        cont = context;
        stationIds = stationIdForName;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inf = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(R.layout.search_item_station, null);

        final String stationName = getItem(position);

        //Give it the result Name
        TextView theText = (TextView) view.findViewById(R.id.result_Name);
        theText.setText(stationName);

        //Button click event
        ImageButton favButton = (ImageButton) view.findViewById(R.id.FavoriteBtn);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the favorite station
                DataStorageManager.getInstance().SetContext(cont);
                DataStorageManager.getInstance()
                        .SaveStationFavorite(stationIds.get(stationName), stationName);
            }
        });

        return view;
    }
}
