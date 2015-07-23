package com.bradnicolle.albertnetwork;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RestClient.get().getCustomers(new Callback<List<Customer>>() {

			@Override
			public void failure(RetrofitError arg0) {
				Log.i("App", "FAILED!!!!!" + arg0);
			}

			@Override
			public void success(List<Customer> customerResponse,
					Response response) {
				for (Customer c : customerResponse) {
					Log.i("App", "ID: " + c.getId());
					Log.i("App", "Name: " + c.getName());
					Log.i("App", "Points: " + c.getPoints());
				}
			}

		});
	}

}
