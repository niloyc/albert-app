package com.company.mypaymentapplication.gratisuser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.company.mypaymentapplication.gratisuser.util.Utilities;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.LinkedList;
import java.util.List;

import static com.company.mypaymentapplication.gratisuser.util.Utilities.setFont;

public class MainActivity extends Activity implements View.OnClickListener {
    public static String tag = "NICK";
    private static final String PREFS_NAME = "GratisUserPrefs";

    private String user_id = "";

    private NfcAdapter mAdapter;
    private NdefMessage mMessage;

    private ViewGroup layoutNoQR;
    private ImageView qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "Started GratisUser");
        // Set Listeners
        Button btn_link_qr = (Button) findViewById(R.id.btn_link_qr);
        btn_link_qr.setOnClickListener(this);

        layoutNoQR = (ViewGroup) findViewById(R.id.layout_no_qr);
        qrImage = (ImageView) findViewById(R.id.img_qr);
        // Load preferences and ID.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        user_id = settings.getString("user_id", "default");


        if(user_id.equals("default")){
            layoutNoQR.setVisibility(View.VISIBLE);
            qrImage.setVisibility(View.GONE);
        }else{
            qrImage.setVisibility(View.VISIBLE);
            qrImage.setImageBitmap(createQRImage(user_id));
            layoutNoQR.setVisibility(View.GONE);
        }

        Log.d(MainActivity.tag, "ma i think user_id is " + user_id);
        // Set the offer list.
        ListView offerList = (ListView) findViewById(R.id.list_offers);
            // this should be from a REST call:
        List<OfferItem> offers = new LinkedList<OfferItem>();
        offers.add(new OfferItem("Free coffee Bevi espresso", "0.5km away", getResources().getDrawable(R.drawable.coffee_icon)));
        offers.add(new OfferItem("Free upgrade when you buy a salmon bagel", "1.2km away", getResources().getDrawable(R.drawable.drink_icon)));
        offers.add(new OfferItem("2 for 1 Cappuccinos, Bring a friend", "2.3km away", getResources().getDrawable(R.drawable.coffee_icon)));
            // end
        offerList.setAdapter(new OfferListAdapter(getApplicationContext(),
                R.layout.offer_item,
                offers));

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mMessage = new NdefMessage(NdefRecord.createMime("text/plain", user_id.getBytes()));
        mAdapter.setNdefPushMessage(mMessage, this);

        Utilities.loadFonts(getApplicationContext());

        ViewGroup root = (ViewGroup)findViewById(R.id.layout_root_main);
        setFont(root);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // Handle button presses.
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.btn_link_qr:
                Log.d(tag, "Linking QR");
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
                break;
        }
    }

    // Callback function for scan result, sets the user_id in preferences.
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(tag, "Activity result found.");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String result = "Did not work.";
        if (scanResult != null) {
            result = scanResult.getContents();
            // Check that the result is a valid user ID.
            // TO DO
            user_id = result;
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user_id", user_id);
            editor.commit();
            //user_id = "A12345";
            Log.d(tag, "user_id is now "+result);

            qrImage.setVisibility(View.VISIBLE);
            qrImage.setImageBitmap(createQRImage(user_id));
            layoutNoQR.setVisibility(View.GONE);
        }
    }

    // Create Bitmap of QR Code.
    public static Bitmap createQRImage(String user_id){
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(user_id, BarcodeFormat.QR_CODE, 500, 500, null);
        } catch (Exception ex) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                bmp.setPixel(x, y, result.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

}
