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

public class OrderListAdapter extends ArrayAdapter<Item> {

    private Context c;
    private int resId;
    private List<Item> items;

    public OrderListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        c = context;
        resId = resource;
        items = objects;
    }

    @Override
    public Item getItem(int position) {
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

        final Item item = items.get(position);
        if (item != null) {   //Put the name/data and icon according to the file data
            TextView name = (TextView) v.findViewById(R.id.text_item_name);
            TextView data = (TextView) v.findViewById(R.id.text_item_data);
            TextView price = (TextView) v.findViewById(R.id.text_item_price);
            ImageView icon = (ImageView) v.findViewById(R.id.img_item);

            if (name != null)
                name.setText(item.getName());

            if (price != null){
                DecimalFormat df = new DecimalFormat("#.00");
                price.setText("$" + df.format(item.getPrice()));
            }

            if(item.getName().equals("Total")){
                data.setText("Tips not included");
                icon.setImageResource(R.drawable.kfc_logo);
                return v;
            }

            if (data != null) {
                data.setText("Quantity: " + item.getQuantity());
            }

            Ion.with(icon)
                    .placeholder(R.drawable.kfc_logo)
                    .error(R.drawable.kfc_logo)
                    .animateLoad(R.anim.abc_fade_out)
                    .animateIn(R.anim.abc_fade_in)
                    .load(item.getImage_url());
            
        }

        return v;
    }
}