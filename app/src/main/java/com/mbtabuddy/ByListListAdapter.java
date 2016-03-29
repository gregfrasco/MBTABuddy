package com.mbtabuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DataManagement.IconHelper;
import DataManagement.LoadingDialogManager;
import gmap.TrainMarker;
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
        int trainIconRes = TrainMarker.getIconResource(getItem(position).lineColor);
        image.setImageBitmap(IconHelper.drawableToBitmap(cont.getResources().getDrawable(trainIconRes)));

        //Assign the line name
        TextView lineName = (TextView) listItem.findViewById(R.id.lineListItem_text);
        lineName.setText(getItem(position).lineName);

        //When an item is clicked get all of the stations for that line
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialogManager.getInstance().ShowLoading(cont);
                Intent searchActIntent = new Intent(cont, SearchActivity.class);
                searchActIntent.putExtra("stationsForLine", getItem(position).lineColor.getColor());
                LoadingDialogManager.getInstance().DismissLoading();
                cont.startActivity(searchActIntent);

            }
        });

        return listItem;
    }
}
