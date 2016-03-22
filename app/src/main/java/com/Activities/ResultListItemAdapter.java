package com.Activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import DataManagement.DataStorageManager;
import DataManagement.FavoritesDataContainer;
import DataManagement.IconHelper;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 3/4/2016.
 */
public class ResultListItemAdapter extends ArrayAdapter<String> {
    final Context cont;
    HashMap<String,String> stationIds;

    public ResultListItemAdapter(Context context, int resource, List<String> objects,
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

        DataStorageManager.getInstance().SetContext(cont);

        List<FavoritesDataContainer> favs = (List<FavoritesDataContainer>)
                DataStorageManager.getInstance().LoadUserData(DataStorageManager.UserDataTypes.FAVORITES_DATA);

        //Check if this is already a favorite, if it is give it the full star icon
        for(FavoritesDataContainer fav : favs)
        {
            if(fav.favName.equals(stationName))
            {
                Drawable filledStarDrawable = cont.getResources().getDrawable(R.drawable.ic_star_24dp);
                ImageButton favoritesButton = (ImageButton) view.findViewById(R.id.FavoriteBtn);
                favoritesButton.setImageBitmap(IconHelper.drawableToBitmap(filledStarDrawable));
            }
        }

        //Button click event
        ImageButton favButton = (ImageButton) view.findViewById(R.id.FavoriteBtn);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the favorite station
                DataStorageManager.getInstance().SetContext(cont);
                DataStorageManager.getInstance()
                        .SaveStationFavorite(stationIds.get(stationName), stationName);
                Drawable filledStarDrawable = cont.getResources().getDrawable(R.drawable.ic_star_24dp);
                ImageButton favoritesButton = (ImageButton) v.findViewById(R.id.FavoriteBtn);
                favoritesButton.setImageBitmap(IconHelper.drawableToBitmap(filledStarDrawable));
            }
        });

        return view;
    }
}
