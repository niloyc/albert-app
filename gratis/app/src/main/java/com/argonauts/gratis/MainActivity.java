package com.argonauts.gratis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements OnClickListener{

    private static String tag = ".MainActivity";
    public static Theme theme;

    private Button btn_theme1;
    private Button btn_theme2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        // Get buttons.
        btn_theme1 = (Button) findViewById(R.id.btn_theme1);
        btn_theme2 = (Button) findViewById(R.id.btn_theme2);
        btn_theme1.setOnClickListener(this);
        btn_theme2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, ItemsActivity.class);
        switch (id) {
            case R.id.btn_theme1:
                Log.d(tag, "Theme 1 pressed");
                // Create a theme.
                theme = new Theme();
                // Start the items activity.
                startActivity(intent);
                break;
            case R.id.btn_theme2:
                Log.d(tag, "Theme 2 pressed");
                // Create a theme.
                theme = new Theme();
                theme.title_banner_color = 0xFF9FF781;
                theme.title_banner_text_size = 15;
                theme.title_banner_text_color = 0xFF000000;
                theme.title_banner_text = "1nv3nt0ry";
                theme.title_banner_back_btn_released_color = 0xFF666666;
                theme.title_banner_back_btn_released_radius = 30;
                theme.title_banner_back_btn_released_stroke_thickness = 2;
                theme.title_banner_back_btn_released_stroke_color = 0xFFFFFFFF;
                theme.title_banner_back_btn_released_text_color = 0xFF000000;
                theme.title_banner_back_btn_released_text_size = 20;
                theme.slider_banner_background_color = 0xFFFFFFFF;
                theme.slider_banner_color = 0xFFFF0000;
                //slider_banner_logo = ;
                theme.slider_banner_text = "0rd3r T0t4l";
                theme.slider_banner_text_size = 15;
                theme.slider_banner_text_color = 0xFF0000FF;
                // Start the items activity.
                startActivity(intent);
                break;
        }
    }
}
