package com.company.mypaymentapplication.qrcode;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.payment.TransactionStatus;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class QRActivity extends Activity implements View.OnClickListener {

    private static String tag = ".QRActivity";

    static final int REWARD_REQUEST = 0x01;
    static final int PAY_REQUEST = 0x10;
    static String EXTRA_PAYMENT_STATUS = "payment_status";

    double price;

    IntentFilter[] intentFiltersArray;
    NfcAdapter mAdapter;
    PendingIntent pendingIntent;
    String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        TextView myAwesomeTextView = (TextView) findViewById(R.id.scan_content);
        myAwesomeTextView.setText("-");
        setResult(Activity.RESULT_CANCELED);

        findViewById(R.id.btn_scan).setOnClickListener(this);
        findViewById(R.id.btn_skip).setOnClickListener(this);
        findViewById(R.id.btn_nfc_simulate).setOnClickListener(this);

        price = getIntent().getDoubleExtra(MainActivity.EXTRA_TOTAL_PRICE, 20.0);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef};
        techListsArray = new String[][] { new String[] { NfcF.class.getName() } };

        mAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(pendingIntent==null) {
            Log.d(tag, "Pending intent");
        }

        if(intentFiltersArray==null) {
            Log.d(tag, "Filter intent");
        }

        if(techListsArray==null) {
            Log.d(tag, "Techlist intent");
        }

        mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startActivityForResult(new Intent(this, RewardActivity.class), REWARD_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qr, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
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

    // Callback function for scan result.
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == REWARD_REQUEST) {
            pay();
            return;
        }else if (requestCode == PAY_REQUEST) {
            Log.d(tag, "Pay request");
            System.err.println("QRCODE HERE");
            try {
                TransactionResult result = TransactionResult.fromIntent(intent);
                if (result.getTransactionStatus() == TransactionStatus.APPROVED) {
                    setResult(Activity.RESULT_OK);
                } else {
                    setResult(Activity.RESULT_CANCELED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                finish();
            }
        } else {

            Log.d(tag, "Activity result found.");
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
            TextView myAwesomeTextView = (TextView) findViewById(R.id.scan_content);
            myAwesomeTextView.setText(result);

            // Go to YOU CLUCKING LEGEND PAGE
            startActivityForResult(new Intent(this, RewardActivity.class), REWARD_REQUEST);
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void pay() {
        // Construct a new payment for $20.00.

        DecimalFormat df = new DecimalFormat("#.##");
        PaymentRequest payment = new PaymentRequest(new BigDecimal(df.format(price)));
        payment.setCurrency(Currency.getInstance("AUD"));

        // Launch the Payment app.
        startActivityForResult(payment.createIntent(), PAY_REQUEST);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_scan:
                IntentIntegrator integrator = new IntentIntegrator(QRActivity.this);
                integrator.initiateScan();
                break;
            case R.id.btn_skip:
                pay();
                break;
            case R.id.btn_nfc_simulate:
                startActivityForResult(new Intent(this, RewardActivity.class), REWARD_REQUEST);
                break;

        }
    }
}
