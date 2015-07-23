package commbank.hackathon.kfcalbert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;


public class QRActivity extends Activity implements OnClickListener{

	private static String tag = ".QRActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        TextView myAwesomeTextView = (TextView)findViewById(R.id.scan_content);
        myAwesomeTextView.setText("-");
        ((Button)findViewById(R.id.generate_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.scan_button)).setOnClickListener(this);
    }

    // Callback function for scan result.
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(tag, "Activity result found.");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String result = "Did not work.";
        if (scanResult != null) {
            result = scanResult.getContents();
        }
        TextView myAwesomeTextView = (TextView)findViewById(R.id.scan_content);
        myAwesomeTextView.setText(result);
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.scan_button:
	        Log.d(tag, "Scan clicked.");
	        IntentIntegrator integrator = new IntentIntegrator(this);
	        integrator.initiateScan();
	        Log.d(tag, "Scan function ended.");
			break;
		case R.id.generate_button:
	        Log.d(tag, "Generate clicked.");
	        String str = "a man's anus";
	        BitMatrix result;
	        try {
	            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 500, 500, null);
	        } catch (Exception ex) {
	            // Unsupported format
	            return;
	        }
	        //
	        int w = result.getWidth();
	        int h = result.getHeight();
	        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
	        for (int x = 0; x < w; x++){
	            for (int y = 0; y < h; y++){
	                bmp.setPixel(x, y, result.get(x,y) ? Color.BLACK : Color.WHITE);
	            }
	        }
	        ImageView qr_image = (ImageView) findViewById(R.id.image);
	        qr_image.setImageBitmap(bmp);

	        Log.d(tag, "Generate click function ended.");
			break;
		}
	}
}
