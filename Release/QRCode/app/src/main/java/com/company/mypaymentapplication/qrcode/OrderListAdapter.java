package com.company.mypaymentapplication.qrcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<OrderItem> {

    private Context c;
    private int resId;
    private List<OrderItem> items;

    public OrderListAdapter(Context context, int resource, List<OrderItem> objects) {
        super(context, resource, objects);

        c = context;
        resId = resource;
        items = objects;
    }

    @Override
    public OrderItem getItem(int position) {
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

        final OrderItem item = items.get(position);
        if (item != null) {   //Put the name/data and icon according to the file data
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView data = (TextView) v.findViewById(R.id.data);
            TextView price = (TextView) v.findViewById(R.id.price);

            if (name != null)
                name.setText(item.getName());

            if (data != null) {
                data.setText("Quantity: " + item.getQuantity());
            }
            
            if (price != null){
            	price.setText(" $" + (item.getQuantity() * item.getPrice()) );
            }
            
        }

        return v;
    }
}