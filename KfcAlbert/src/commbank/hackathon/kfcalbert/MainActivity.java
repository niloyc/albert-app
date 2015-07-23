package commbank.hackathon.kfcalbert;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener{
	
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
		
		getActionBar().hide();
		
		orderList = (ListView) findViewById(R.id.itemList);
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		items.add(new OrderItem("Popcorn Chicken", 2, 2.99));
		items.add(new OrderItem("Crispy Strips Pk. 3", 1, 6.99));
		
		orderList.setAdapter(new OrderListAdapter(this, R.layout.order_item, items));
		
		confirmButton = (Button) findViewById(R.id.btn_confirm_order);
		confirmButton.setOnClickListener(this);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
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
			Intent intent = new Intent(this, QRActivity.class);
			startActivity(intent);
			break;
		}
	}
}
