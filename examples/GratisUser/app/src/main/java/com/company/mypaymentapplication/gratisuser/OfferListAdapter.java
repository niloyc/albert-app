package com.company.mypaymentapplication.gratisuser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OfferListAdapter extends ArrayAdapter<OfferItem> {

    private Context c;
    private int resId;
    private List<OfferItem> items;

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

            if (name != null)
                name.setText(item.getName());
        }

        return v;
    }
}