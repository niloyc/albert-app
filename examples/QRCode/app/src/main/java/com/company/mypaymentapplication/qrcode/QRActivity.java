package com.company.mypaymentapplication.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


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
        TextView myAwesomeTextView = (TextView)findViewById(R.id.scan_content);
        myAwesomeTextView.setText(result);
    }
}
