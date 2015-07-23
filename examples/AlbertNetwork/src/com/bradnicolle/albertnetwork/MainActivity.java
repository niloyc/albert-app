package com.bradnicolle.albertnetwork;

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

	}

}
