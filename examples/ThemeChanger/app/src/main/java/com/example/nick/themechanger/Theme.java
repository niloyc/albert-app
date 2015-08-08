package com.example.nick.themechanger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Nick on 7/08/2015.
 */
public class Theme {
    public Context context;

    public GradientDrawable btn_pressed_gradient;
    public int btn_pressed_text_color;
    public int btn_pressed_height;
    public int btn_pressed_text_size;
    public Typeface btn_pressed_typeface;

    public GradientDrawable btn_released_gradient;
    public int btn_released_text_color;
    public int btn_released_height;
    public int btn_released_text_size;
    public Typeface btn_released_typeface;

    public Drawable logo_drawable;
    public boolean logo_image_is_fetching;
    public List<ImageView> logo_waiting_list;

    public int background_color;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Static methods for creating and applying Themes. Standard creation is from a RestTheme,
    // then applying the created theme to buttons/backgrounds/logo etc.
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Create a new theme from a RestTheme.
    public static Theme createFromRestTheme(Context context, RestTheme rest_theme){
        Theme theme = new Theme(context);

        theme.background_color = Color.parseColor(rest_theme.getBackground());
        theme.btn_released_gradient.setColor(Color.parseColor(rest_theme.getButtons()));
        //rest_theme.getForeground();
        //rest_theme.getId();
        theme.setLogoStyle(rest_theme.getLogo());
        //rest_theme.getOffer();
        //rest_theme.getReward_img();
        //rest_theme.getTile_cat();
        //rest_theme.getTile_item();

        return theme;
    }

    // Applies a theme to a Button.
    public static void applyToButton(Theme theme, Button button, boolean pressed){
        if (pressed){
            button.setTextColor(theme.btn_pressed_text_color);
            button.setHeight(theme.btn_pressed_height);
            button.setTextSize(theme.btn_pressed_text_size);
            button.setTypeface(theme.btn_pressed_typeface);
            button.setBackground(theme.btn_pressed_gradient);
        } else {
            button.setTextColor(theme.btn_released_text_color);
            button.setHeight(theme.btn_released_height);
            button.setTextSize(theme.btn_released_text_size);
            button.setTypeface(theme.btn_released_typeface);
            button.setBackground(theme.btn_released_gradient);
        }
    }

    // Applies a theme to a background.
    public static void applyToBackground(Theme theme, View v){
        v.setBackgroundColor(theme.background_color);
    }

    // Applies a theme to a logo, or add it to the waiting list if we don't have one yet.
    public static void applyToLogo(Theme theme, ImageView logo){
        Log.d(MainActivity.DEBUG_TAG, "Applying logo");
        if (theme.logo_image_is_fetching){
            theme.logo_waiting_list.add(logo);
            Log.d(MainActivity.DEBUG_TAG, "added to list");
        } else {
            logo.setImageDrawable(theme.logo_drawable);
            Log.d(MainActivity.DEBUG_TAG, "instant draw");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Alternative manual creation.
    //////////////////////////////////////////////////////////////////////////////////////////////

    // Constructor. Create default theme.
    public Theme(Context context){
        this.context = context;

        btn_pressed_text_color = 0xFF888888;
        btn_pressed_height = 100;
        btn_pressed_text_size = 25;
        btn_pressed_typeface = Typeface.SERIF;

        btn_released_text_color = 0xFF888888;
        btn_released_height = 100;
        btn_released_text_size = 25;
        btn_released_typeface = Typeface.SERIF;

        btn_pressed_gradient = new GradientDrawable();
        btn_pressed_gradient.setColor(0xFFFFFFFF);
        btn_pressed_gradient.setCornerRadius(10);
        btn_pressed_gradient.setStroke(5, 0xFF888888);

        btn_released_gradient = new GradientDrawable();
        btn_released_gradient.setColor(0xFF000000);
        btn_released_gradient.setCornerRadius(10);
        btn_released_gradient.setStroke(5, 0xFF888888);

        logo_waiting_list = new LinkedList<ImageView>();
    }

    // Sets the background style.
    public void setBackgroundStyle(int color) {
        background_color = color;
    }

    // Sets the logo image to be used for all logos.
    public void setLogoStyle(String url){
        logo_image_is_fetching=true;
        new SetImageFromUrlTask(this).execute(url);
    }

    // Sets the button pressed style to be used for all buttons.
    public void setButtonPressedStyle(int background_color, int height, int corner_radius,
                      int stroke_width, int stroke_color, int text_color, int text_size,
                      Typeface typeface){
        btn_pressed_gradient = new GradientDrawable();
        btn_pressed_gradient.setColor(background_color);
        btn_pressed_gradient.setCornerRadius(corner_radius);
        if (stroke_width > 0) btn_pressed_gradient.setStroke(stroke_width, stroke_color);
        btn_pressed_text_color = text_color;
        btn_pressed_height = height;
        btn_pressed_text_size = text_size;
        btn_pressed_typeface = typeface;
    }

    // Sets the released button style to be used for all buttons.
    public void setButtonReleasedStyle(int background_color, int height, int corner_radius,
                                      int stroke_width, int stroke_color, int text_color, int text_size,
                                      Typeface typeface){
        btn_released_gradient = new GradientDrawable();
        btn_released_gradient.setColor(background_color);
        btn_released_gradient.setCornerRadius(corner_radius);
        if (stroke_width > 0) btn_released_gradient.setStroke(stroke_width, stroke_color);
        btn_released_text_color = text_color;
        btn_released_height = height;
        btn_released_text_size = text_size;
        btn_released_typeface = typeface;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Callback for image fetching.
    //////////////////////////////////////////////////////////////////////////////////////////////

    // Sets the logo for everything in the waiting list.
    public void setLogo(Drawable d){
        Log.d(MainActivity.DEBUG_TAG, "image fetched!");
        this.logo_drawable = resizeDrawable(context, d, 250, 250);
        logo_image_is_fetching = false;
        ListIterator<ImageView> iter = logo_waiting_list.listIterator();
        while (iter.hasNext()){
            ImageView next = iter.next();
            next.setImageDrawable(logo_drawable);
            iter.remove();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Helper functions.
    //////////////////////////////////////////////////////////////////////////////////////////////

    // Set a drawable image to the correct size.
    private static Drawable resizeDrawable(Context c, Drawable image, int size_x, int size_y) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, size_x, size_y, false);
        return new BitmapDrawable(c.getResources(), bitmapResized);
    }
}
