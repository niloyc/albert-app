package com.example.nick.themechanger;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Nick on 8/08/2015.
 */
public class SetImageFromUrlTask extends AsyncTask<String, Void, Drawable> {
    private Theme theme;

    public SetImageFromUrlTask(Theme theme){
        this.theme = theme;
    }

    // Either returns the image, or returns null - do not access UI elements in this.
    public Drawable doInBackground(String... urls){
        if (urls.length <= 0) return null;
        Drawable d = null;
        String url = urls[0];
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            d = Drawable.createFromStream(is, "src name");

        } catch (Exception ex){
            Log.d(MainActivity.DEBUG_TAG, "exception: " + ex.toString());
        }

        return d;
    }

    // Sets the logo image.
    public void onPostExecute(Drawable d){
        if (d == null) return;
        theme.setLogo(d);
    }

}
