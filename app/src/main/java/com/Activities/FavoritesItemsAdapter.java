package com.Activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import DataManagement.DataStorageManager;
import DataManagement.FavoritesDataContainer;
import mbta.mbtabuddy.R;

/**
 * Created by Joey on 2/26/2016.
 */
public class FavoritesItemsAdapter extends ArrayAdapter<FavoritesDataContainer> {

    private Boolean isRemoving = false;
    private Context cont;

    public FavoritesItemsAdapter(Context context, int resource, List<FavoritesDataContainer> objects) {
        super(context, resource, objects);
        cont = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(R.layout.favorites_item, null);

        Log.v("FavoritesItemsAdapter", getItem(position).favName);

        //Set our text
        TextView theText = (TextView) view.findViewById(R.id.Favorite_Name);
        theText.setText(getItem(position).favName);

        //Set up the button for removing this item as a favorite
        ImageButton RemoveFavButton = (ImageButton) view.findViewById(R.id.UnfavoriteBtn);
        RemoveFavButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DataStorageManager.getInstance().removeFavorite(getItem(position).favName);
                remove(getItem(position));
            }
        });

        //The view is refreshed when an item is removed, need to cache the state
        if(isRemoving)
        {
            RemoveFavButton.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void setIsRemoving(Boolean IsRemoving)
    {
        isRemoving = IsRemoving;
    }

}
