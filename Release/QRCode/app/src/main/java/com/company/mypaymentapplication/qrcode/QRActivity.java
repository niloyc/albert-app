package com.company.mypaymentapplication.qrcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class QRActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        TextView myAwesomeTextView = (TextView)findViewById(R.id.scan_content);
        myAwesomeTextView.setText("-");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Called upon payment button click.
    public void onButtonClick(View view){
        Log.d("mytag", "Button clicked.");
        IntentIntegrator integrator = new IntentIntegrator(QRActivity.this);
        integrator.initiateScan();
        Log.d("mytag", "Button click function ended.");
    }

    // Callback function for scan result.
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("mytag", "Activity result found.");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String result = "Did not work.";
        if (scanResult != null) {
            result = scanResult.getContents();
        }

        // Call DB.
        RestClient.get().getCustomer(result, new Callback<Customer>() {
            @Override
            public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub

            }
            @Override
            public void success(Customer arg0, Response arg1) {
                Log.i("App", "Individual customer: " + arg0.getName());
            }
        });
        // Update text.
        TextView myAwesomeTextView = (TextView)findViewById(R.id.scan_content);
        myAwesomeTextView.setText(result);

        // Go to YOU CLUCKING LEGEND PAGE
        startActivity(new Intent(this, RewardActivity.class));
    }

    // Called upon skip.
    public void onButtonClick2(View view){
        Log.d("mytag", "Button2 clicked.");


        Log.d("mytag", "Button2 click function ended.");
    }
}
