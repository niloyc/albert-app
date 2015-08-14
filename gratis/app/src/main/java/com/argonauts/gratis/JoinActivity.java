package com.argonauts.gratis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class JoinActivity extends Activity {

    private static String tag = ".JoinActivity";

    Timer timer;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setResult(RESULT_OK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        time = 5;
        ((TextView)findViewById(R.id.txt_join_timer)).setText("Preparing payment screen " + time + "...");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                JoinActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(time<=1){
                            timer.cancel();
                            timer.purge();
                            onBackPressed();
                        }

                        if(time>0)
                            ((TextView)findViewById(R.id.txt_join_timer)).setText("Preparing payment screen " + --time + "...");
                    }
                });
            }
        }, 1000, 1000);
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

    //Called upon payment button click.
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Add the buttons
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.rewards);

        dialog.setView(image);

        dialog.show();
        dialog.getWindow().setLayout(750, 605);
    }
}
