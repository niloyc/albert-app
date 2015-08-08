package com.example.nick.themechanger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
    public static String DEBUG_TAG = "NICK_DEBUG";
    Button btn_theme1;
    Button btn_theme2;
    Button btn_theme3;
    Button btn_example;
    ImageView logo;

    Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEBUG_TAG, "start of create");
        setContentView(R.layout.activity_main);
        // Create a theme.
        theme = new Theme(this);
        // Set logo.
        logo = (ImageView) findViewById(R.id.logo);


        btn_theme1 = (Button) findViewById(R.id.btn_theme1);
        btn_theme2 = (Button) findViewById(R.id.btn_theme2);
        btn_theme3 = (Button) findViewById(R.id.btn_theme3);
        btn_example = (Button) findViewById(R.id.btn_example);
        btn_theme1.setOnClickListener(this);
        btn_theme2.setOnClickListener(this);
        btn_theme3.setOnClickListener(this);
        btn_example.setOnClickListener(this);

        btn_example.setOnTouchListener(this);
        Log.d(DEBUG_TAG, "end of create");
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

    // Called whenever a button is clicked.
    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case (R.id.btn_theme1):
                Log.d(DEBUG_TAG, "Theme 1 pressed");
                logo.setImageResource(R.drawable.buffer);

                theme.setButtonReleasedStyle(0xFFFFFFFF, 100, 10, 5, 0xFF888888, 0xFF888888, 25, Typeface.SERIF);
                theme.setButtonPressedStyle(0xFFEFF8FB, 100, 10, 5, 0xFF888888, 0xFF888888, 25, Typeface.SERIF);
                theme.setBackgroundStyle(0xFFCEECF5);
                theme.setLogoStyle("http://gainweighthow.com/wp-content/uploads/2013/05/How-to-Add-Weight-to-a-Cat.jpg");
                Theme.applyToLogo(theme, logo);
                Theme.applyToButton(theme, btn_example, false);
                Theme.applyToBackground(theme, findViewById(android.R.id.content));
                break;
            case (R.id.btn_theme2):
                Log.d(DEBUG_TAG, "Theme 2 pressed");
                logo.setImageResource(R.drawable.buffer);

                theme.setButtonReleasedStyle(0xFF000000, 20, 20, 0, 0xFFFFFFFF, 0xFFFFFFFF, 10, Typeface.DEFAULT);
                theme.setButtonPressedStyle(0xFF6E6E6E, 20, 20, 1, 0xFF000000, 0xFF000000, 10, Typeface.DEFAULT);
                theme.setBackgroundStyle(0xFF585858);
                theme.setLogoStyle("http://www.puppetgallery.com/gallery/2828_SmallDuck.jpg");
                Theme.applyToLogo(theme, logo);
                Theme.applyToButton(theme, btn_example, false);
                Theme.applyToBackground(theme, findViewById(android.R.id.content));
                break;
            case (R.id.btn_theme3):
                Log.d(DEBUG_TAG, "Theme 3 pressed");
                logo.setImageResource(R.drawable.buffer);

                final Context c = this;
                RestClient.get().getCustoms("55c226c2a69edf5e19951567", new Callback<RestTheme>() {
                    @Override
                    public void success(RestTheme rest_theme, Response response) {
                        Log.d(DEBUG_TAG, "success");
                        theme = Theme.createFromRestTheme(c, rest_theme);
                        Theme.applyToButton(theme, btn_example, false);
                        Theme.applyToBackground(theme, findViewById(android.R.id.content));
                        Theme.applyToLogo(theme, logo);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d(DEBUG_TAG, "failure");
                        if (retrofitError.getResponse() == null) Log.d(DEBUG_TAG, "null1");
                        if (retrofitError.getBody() == null) Log.d(DEBUG_TAG, "null2");
                        if (retrofitError.getUrl() == null) Log.d(DEBUG_TAG, "null3");
                        if (retrofitError.getKind() == null) Log.d(DEBUG_TAG, "null4");
                        if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) Log.d(DEBUG_TAG, "is network");
                        Log.d(DEBUG_TAG, retrofitError.getUrl());
                        Log.d(DEBUG_TAG, retrofitError.getKind().name());
                        Log.d(DEBUG_TAG, retrofitError.getKind().toString());
                        //String json = new String(((TypedByteArray) retrofitError.getResponse().getBody()).getBytes());
                        //Log.d(DEBUG_TAG, json.toString());
                    }
                });

                break;
        }

    }

    // Called whenever a button is pressed or released.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(DEBUG_TAG, "UP");
            Theme.applyToButton(theme, btn_example, false);
        } else {
            Log.d(DEBUG_TAG, "DOWN");
            Theme.applyToButton(theme, btn_example, true);
        }
        return false;
    }
}
