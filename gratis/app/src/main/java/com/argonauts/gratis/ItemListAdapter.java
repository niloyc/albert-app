package com.argonauts.gratis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Niloy on 13-Aug-15.
 */
public class ItemListAdapter extends ArrayAdapter<Item> {

    Context c;
    int resId;
    List<Item> items;

    public ItemListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        c = context;
        resId = resource;
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Return the custom view used in the file array
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resId, null);
        }

        final Item item = items.get(position);
        if (item != null) {   //Put the name/data and icon according to the file data
            TextView name = (TextView) v.findViewById(R.id.text_menu_item_name);
            TextView data = (TextView) v.findViewById(R.id.text_menu_item_data);
            ImageView icon = (ImageView) v.findViewById(R.id.img_menu_item);

            if (name != null)
                name.setText(item.getName());

            if (data != null){
                if(item.is_cat()){
                    data.setText("Category");
                }else {
                    DecimalFormat df = new DecimalFormat("0.00");
                    data.setText("$" + df.format(item.getPrice()));
                }
            }

            Ion.with(icon)
                    .placeholder(R.drawable.store_logo)
                    .error(R.drawable.store_logo)
                    .animateIn(R.anim.abc_grow_fade_in_from_bottom)
                    .load(item.getImage_url());

        }

        return v;
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }
}
