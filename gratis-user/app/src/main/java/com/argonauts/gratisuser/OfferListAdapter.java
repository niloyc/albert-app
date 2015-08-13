package com.argonauts.gratisuser;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.argonauts.gratisuser.util.Utilities;

import java.util.List;

public class OfferListAdapter extends ArrayAdapter<OfferItem> {

    private Context c;
    private int resId;
    private List<OfferItem> items;
    Typeface mFont;

    public OfferListAdapter(Context context, int resource, List<OfferItem> objects) {
        super(context, resource, objects);

        c = context;
        resId = resource;
        items = objects;

    }

    @Override
    public OfferItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Return the custom view used in the file array

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resId, null);
        }

        final OfferItem item = items.get(position);
        if (item != null) {   //Put the name/data and icon according to the file data
            TextView name = (TextView) v.findViewById(R.id.text_item_name);
            TextView location = (TextView) v.findViewById(R.id.text_item_location);
            ImageView icon = (ImageView) v.findViewById(R.id.text_item_icon);

            if (name != null)
                name.setText(item.getName());

            if (location != null)
                location.setText(item.getLocation());

            if (icon != null) {
                icon.setImageDrawable(item.getIcon());
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

        }

        Utilities.setFont((ViewGroup) v.findViewById(R.id.layout_root_item));

        return v;
    }
}