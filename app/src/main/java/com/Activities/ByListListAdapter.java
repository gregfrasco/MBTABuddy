package com.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;
import java.util.zip.Inflater;

import gmap.TrainMarker;
import mbta.Line;
import mbta.Lines;
import mbta.MBTA;
import mbta.Station;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 3/16/2016.
 */
public class ByListListAdapter extends ArrayAdapter<ByLineListContainer> {
    private Context cont;

    public ByListListAdapter(Context context, int resource, List<ByLineListContainer> objects) {
        super(context, resource, objects);
        cont = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        //Inflate our view
        LayoutInflater inflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItem = inflater.inflate(R.layout.by_line_list_item, null);

        //Get our imageView
        ImageView image = (ImageView) listItem.findViewById(R.id.lineListItem_image);

        //Utilize the trainMarker class's method for getting appropriate icon and set it
        int trainIconRes = TrainMarker.GetIconResource(getItem(position).lineColor);
        image.setImageBitmap(BitmapFactory.decodeResource(cont.getResources(), trainIconRes));

        //Assign the line name
        TextView lineName = (TextView) listItem.findViewById(R.id.lineListItem_text);
        lineName.setText(getItem(position).lineName);

        //When an item is clicked get all of the stations for that line
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActIntent = new Intent(cont, SearchActivity.class);
                searchActIntent.putExtra("stationsForLine", getItem(position).lineColor);
                cont.startActivity(searchActIntent);
            }
        });

        return listItem;
    }
}
