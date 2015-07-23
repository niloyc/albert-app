package com.company.mypaymentapplication.qrcode;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RewardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reward, menu);
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

    /*// Called upon payment button click.
    public void onButtonClick(View view){
        Log.d("mytag", "Button clicked.");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Add the buttons
        builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog - do nothing
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.reward);

        dialog.setView(image);

        dialog.show();
        dialog.getWindow().setLayout(750, 605);

        Log.d("mytag", "Button click function ended.");
    }*/
}
