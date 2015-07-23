package com.company.mypaymentapplication.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {
	
	private static String tag = ".MainActivity";
	private Button confirmButton;
	
	private ListView orderList;
	
	private final Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(tag, "Started KFCAlbert");
		
		//getSupportActionBar().hide();

		orderList = (ListView) findViewById(R.id.itemList);

		List<OrderItem> items = new ArrayList<OrderItem>();
		//items.add(new OrderItem("Popcorn Chicken", 2, 2.99));
		//items.add(new OrderItem("Crispy Strips Pk. 3", 1, 6.99));
		
		orderList.setAdapter(new OrderListAdapter(this, R.layout.order_item, items));
		
		confirmButton = (Button) findViewById(R.id.btn_confirm_order);
		confirmButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
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
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.btn_confirm_order:
			Log.d(tag, "Order Confirmed");
			startActivity(new Intent(this, QRActivity.class));
			break;
		}
	}
}
