package com.company.mypaymentapplication.qrcode;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {

	public static final int HANDLE_LOYALTY_PAYMENT = 0x100;
	static final String EXTRA_TOTAL_PRICE = "total_price";
	private static String tag = ".MainActivity";
	private Button confirmButton;
	private TextView title;

	private ListView itemList, orderItemList;
	private ArrayList<OrderItem> orderItems;
	
	private final Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_up_panel);
		Log.d(tag, "Started KFCAlbert");

		orderItems = new ArrayList<>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		itemList = (ListView) findViewById(R.id.itemList);
		orderItemList = (ListView) findViewById(R.id.orderItemList);

		orderItemList.setAdapter(new OrderListAdapter(getApplicationContext(), R.layout.order_item, orderItems));

		RestClient.get().getOrders(new Callback<List<Order>>() {
			@Override
			public void success(List<Order> orders, Response response) {
				itemList.setAdapter(new OrderListAdapter(getApplicationContext(),
						R.layout.menu_item,
						orders.get(0).getItems()));
			}

			@Override
			public void failure(RetrofitError retrofitError) {

			}
		});

		itemList.setOnItemClickListener(this);


		title = (TextView) findViewById(R.id.text_order_title);
		confirmButton = (Button) findViewById(R.id.btn_confirm_order);
		confirmButton.setOnClickListener(this);
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
		switch(id){
		case R.id.btn_confirm_order:
			Log.d(tag, "Order Confirmed");
			double total = Double.parseDouble(((TextView) findViewById(R.id.text_total)).getText().toString());
			Intent intent = new Intent(this, QRActivity.class);
			intent.putExtra(EXTRA_TOTAL_PRICE, total);
			startActivityForResult(intent, HANDLE_LOYALTY_PAYMENT);
			break;
		}
	}

	private void resetOrder(){
		RestClient.get().getOrders(new Callback<List<Order>>() {
			@Override
			public void success(List<Order> orders, Response response) {
				itemList.setAdapter(new OrderListAdapter(getApplicationContext(),
						R.layout.menu_item,
						orders.get(0).getItems()));
				((TextView)findViewById(R.id.text_total)).setText("0.0");

				((OrderListAdapter)orderItemList.getAdapter()).clear();
				((OrderListAdapter) orderItemList.getAdapter()).notifyDataSetChanged();
			}

			@Override
			public void failure(RetrofitError retrofitError) {

			}
		});

		orderItems.clear();
		orderItemList.setAdapter(new OrderListAdapter(getApplicationContext(), R.layout.order_item, orderItems));

		title.setText("Order");
		confirmButton.setText("Confirm Order");
		confirmButton.setBackgroundColor(Color.parseColor("#f5846c"));
		title.setBackgroundColor(Color.parseColor("#f5846c"));
		confirmButton.setEnabled(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode){
			case HANDLE_LOYALTY_PAYMENT:
				Button confirm = (Button) findViewById(R.id.btn_confirm_order);
				confirm.setEnabled(false);
				TextView title = (TextView) findViewById(R.id.text_order_title);
				if(resultCode == Activity.RESULT_OK) {
					title.setText("Order Complete");
					confirm.setText("Please wait to collect");
					confirm.setBackgroundColor(Color.parseColor("#FF0F9D58"));
					title.setBackgroundColor(Color.parseColor("#FF0F9D58"));
				}else{
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
		OrderListAdapter adapter = (OrderListAdapter) adapterView.getAdapter();
		OrderItem item = adapter.getItem(index);
		item.setQuantity(1);
		((OrderListAdapter) orderItemList.getAdapter()).add(item);
		orderItems.add(item);

		double totalPrice = Double.parseDouble(((TextView) findViewById(R.id.text_total)).getText().toString());
		totalPrice+=item.getPrice();
		DecimalFormat df = new DecimalFormat("#.##");
		((TextView)findViewById(R.id.text_total)).setText(df.format(totalPrice));
	}
}
