package com.bradnicolle.albertnetwork;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textView;
	private Button updateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textview);
		updateButton = (Button) findViewById(R.id.updateButton);
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		items.add(new OrderItem("Bacon strips", "http://images.teamsugar.com/files/users/1/12981/39_2007/bacon.jpg", 5, 29.99));
		items.add(new OrderItem("Zinger burger", "https://kfc.com.au/media/339334/burger_zinger.jpg", 69, 12.00));
		Order order = new Order("55b0506c1bc7eebe6c85e4e0", false, items);
		
		RestClient.get().addOrder(order, new Callback<List<Order>>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(List<Order> arg0, Response arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		RestClient.get().updateCustomer("55b0506c1bc7eebe6c85e4e0", new Customer("Nylon Chowdhury", 68), new Callback<Customer>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(Customer arg0, Response arg1) {
				// TODO Auto-generated method stub
				
			}});
		
		RestClient.get().getCustomer("55b0506c1bc7eebe6c85e4e0", new Callback<Customer>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(Customer arg0, Response arg1) {
				Log.i("App", "Individual customer: " + arg0.getName());
				
			}
		});
		

		updateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RestClient.get().getCustomers(new Callback<List<Customer>>() {

					@Override
					public void failure(RetrofitError arg0) {
						Log.i("App", "FAILED!!!!!" + arg0);
					}

					@Override
					public void success(List<Customer> customerResponse,
							Response response) {
						textView.setText("Users: \n");
						for (Customer c : customerResponse) {
							Log.i("App", "ID: " + c.getId());
							Log.i("App", "Name: " + c.getName());
							Log.i("App", "Points: " + c.getPoints());
							textView.append("ID: " + c.getId() + "\nName: "
									+ c.getName() + "\nPoints: "
									+ c.getPoints() + "\n\n");
						}
					}

				});
			}
		});
		
		RestClient.get().addCustomer(new Customer("BLAH", 32), new Callback<List<Customer>>() {

			@Override
			public void failure(RetrofitError arg0) {
				Log.i("App", "FAILED");
			}

			@Override
			public void success(List<Customer> arg0, Response arg1) {
				Log.i("App", "DONE");
			}
			
		});

	}

}
