package com.mbtabuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DataManagement.IconHelper;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 3/16/2016.
 */
public class DrawerMenuItemAdapter extends ArrayAdapter<DrawerMenuItem> {

    private Context cont;
    public DrawerMenuItemAdapter(Context context, int resource, List<DrawerMenuItem> objects) {
        super(context, resource, objects);
        cont = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drawer_menu_item_layout, null);

        if(position == 0)
        {
            view.findViewById(R.id.drawer_header).setVisibility(View.VISIBLE);
        }

        //Set up name
        String itemName = getItem(position).menuItemName;
        TextView itemText = (TextView) view.findViewById(R.id.drawer_item_text);
        itemText.setText(itemName);

        //Set up image
        int iconResource = getItem(position).menuItemResource;
        Drawable iconDrawable = cont.getResources().getDrawable(iconResource);
        Bitmap iconBmp = IconHelper.drawableToBitmap(iconDrawable);
        ImageView menuItemImage = (ImageView) view.findViewById(R.id.drawer_item_icon);
        menuItemImage.setImageBitmap(iconBmp);

        return view;
    }


}
