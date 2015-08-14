package com.argonauts.gratis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {

    public static final int HANDLE_LOYALTY_PAYMENT = 0x100;
    static final String EXTRA_TOTAL_PRICE = "total_price";
    private static String tag = ".MainActivity";
    private Button confirmButton;
    private TextView title;

    private ListView orderItemList;
    private GridView itemList;
    private List<Item> orderItems;
    private List<Item> currentItems;
    private List<Item> allItems;
    private Stack<List<Item>> itemStack;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Log.d(tag, "Started KFCAlbert");

        orderItems = new ArrayList<>();
        currentItems = new ArrayList<>();
        itemStack = new Stack<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds Items to the action bar if it is present.
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        itemList = (GridView) findViewById(R.id.itemGridList);
        orderItemList = (ListView) findViewById(R.id.orderItemList);

        orderItemList.setAdapter(new OrderListAdapter(getApplicationContext(), R.layout.order_item, orderItems));

        currentItems.clear();

        RestClient.get().getItems(new Callback<List<Item>>() {
            @Override
            public void success(List<Item> items, Response response) {

                allItems = items;

                for (Item i : items) {
                    if (i.getParent().equals("root")) {
                        currentItems.add(i);
                    }
                }

                itemList.setAdapter(new ItemListAdapter(getApplicationContext(),
                        R.layout.menu_item,
                        currentItems));

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(tag, retrofitError.getMessage());
            }
        });

        itemList.setOnItemClickListener(this);
        title = (TextView) findViewById(R.id.text_order_title);
        confirmButton = (Button) findViewById(R.id.btn_confirm_order);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_back).setVisibility(View.GONE);
        confirmButton.setOnClickListener(this);
        confirmButton.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_confirm_order:
                Log.d(tag, "Order Confirmed");
                double total = Double.parseDouble(((TextView) findViewById(R.id.text_total)).getText().toString());
                Intent intent = new Intent(this, QRActivity.class);
                intent.putExtra(EXTRA_TOTAL_PRICE, total);
                startActivityForResult(intent, HANDLE_LOYALTY_PAYMENT);
                break;
            case R.id.btn_back:
                traverseBack();
                break;
        }
    }

    private void traverseBack(){
        if(!itemStack.isEmpty()){
            currentItems = itemStack.pop();
            itemList.setAdapter(new ItemListAdapter(getApplicationContext(),
                    R.layout.menu_item,
                    currentItems));
        }

        if(itemStack.isEmpty())
            findViewById(R.id.btn_back).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(!itemStack.isEmpty())
            traverseBack();
        else
            super.onBackPressed();
    }

    private void resetOrder() {

        itemList.setAdapter(new ItemListAdapter(getApplicationContext(),
                R.layout.menu_item,
                currentItems));
        ((TextView) findViewById(R.id.text_total)).setText("0.00");
        ((OrderListAdapter) orderItemList.getAdapter()).clear();
        ((OrderListAdapter) orderItemList.getAdapter()).notifyDataSetChanged();

        confirmButton.setVisibility(View.GONE);
        orderItems.clear();
        orderItemList.setAdapter(new OrderListAdapter(getApplicationContext(), R.layout.order_item, orderItems));

        title.setText("Order");
        confirmButton.setText("Confirm Order");
        confirmButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        title.setBackgroundColor(getResources().getColor(R.color.primaryForegroundColor));
        confirmButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case HANDLE_LOYALTY_PAYMENT:
                Button confirm = (Button) findViewById(R.id.btn_confirm_order);
                confirm.setEnabled(false);
                TextView title = (TextView) findViewById(R.id.text_order_title);
                if (resultCode == Activity.RESULT_OK) {
                    title.setText("Order Complete");
                    confirm.setText("Please wait to collect");
                    confirm.setBackgroundColor(Color.parseColor("#FF0F9D58"));
                    title.setBackgroundColor(Color.parseColor("#FF0F9D58"));
                } else {
                    Toast.makeText(this, "Unsuccessful payment", Toast.LENGTH_SHORT).show();
                    title.setText("Order Failed");
                    confirm.setText("Payment Failed");
                    confirm.setBackgroundColor(Color.parseColor("#AA0000"));
                    title.setBackgroundColor(Color.parseColor("#AA0000"));
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetOrder();
                    }
                }, 6000);

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        ItemListAdapter adapter = (ItemListAdapter) adapterView.getAdapter();
        Item item = adapter.getItem(index);

        //item is a category
        if (item.is_cat()) {
            itemStack.push(currentItems);
            currentItems = new ArrayList<>();

            for(Item i: allItems){
                if(i.getParent().equals(item.getId())){
                    currentItems.add(i);
                }
            }

            itemList.setAdapter(new ItemListAdapter(getApplicationContext(),
                    R.layout.menu_item,
                    currentItems));

            findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
            return;
        }

        //Item is not a category
        if (orderItems.contains(item)) {
            int qty = orderItems.get(orderItems.indexOf(item)).getQuantity();
            orderItems.get(orderItems.indexOf(item)).setQuantity(qty + 1);
        } else {
            item.setQuantity(1);
            orderItems.add(item);
        }

        ((OrderListAdapter) orderItemList.getAdapter()).notifyDataSetChanged();

        double totalPrice = Double.parseDouble(((TextView) findViewById(R.id.text_total)).getText().toString());
        totalPrice += item.getPrice();
        DecimalFormat df = new DecimalFormat("0.00");
        ((TextView) findViewById(R.id.text_total)).setText(df.format(totalPrice));

        confirmButton.setVisibility(View.VISIBLE);
    }
}
