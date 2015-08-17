package com.argonauts.gratis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Nick on 7/08/2015.
 */
public class Theme {
    //public Context context;

    // ITEMSACTIVITY - Order items section, listed from top to bottom. //
    // Title banner.
    public int title_banner_color;
    // Title banner text.
    public int title_banner_text_size;
    public int title_banner_text_color;
        // font?
    public String title_banner_text;
    // Title banner back button.
    public int title_banner_back_btn_released_color;
    public int title_banner_back_btn_released_radius;
    public int title_banner_back_btn_released_stroke_thickness;
    public int title_banner_back_btn_released_stroke_color;
    public int title_banner_back_btn_released_text_color;
    public int title_banner_back_btn_released_text_size;
        // font?

    // Item buttons.

    // Slider banner.
    public int slider_banner_background_color;
    public int slider_banner_color;
    public Drawable slider_banner_logo;
    public String slider_banner_text;
    public int slider_banner_text_size;
    public int slider_banner_text_color;

    // Order banner.

    // Order list.

    // Confirm order button.

    // Create a new theme from a RestTheme.
    /*public static Theme createFromRestTheme(Context context, RestTheme rest_theme){
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
    }*/

    // Alternative Constructor. Creates default theme.
    public Theme(){
        title_banner_color = 0xFF5a534b;
        title_banner_text = "Inventory";
        title_banner_text_color = 0xFFFFFFFF;
        title_banner_text_size = 34;
        title_banner_back_btn_released_color = 0xFF808080;
        title_banner_back_btn_released_radius = 1;
        title_banner_back_btn_released_stroke_thickness = 5;
        title_banner_back_btn_released_stroke_color = 0xFF888888;
        title_banner_back_btn_released_text_color = 0xFFefeac6;
        title_banner_back_btn_released_text_size = 25;
        slider_banner_background_color = 0xFFF2F2F2;
        slider_banner_color = 0xFF2E2E2E;
        //slider_banner_logo = ;
        slider_banner_text = "Order Total";
        slider_banner_text_size = 30;
        slider_banner_text_color = 0xFFefeac6;
    }

    // Apply while in the ItemsActivity view.
    public void applyToItemsActivity(View view){
        Log.d("theme", "lets try it");
        // Get ItemsActivity components.
        LinearLayout title_banner = (LinearLayout) view.findViewById(R.id.title_banner);
        TextView title_banner_text_view = (TextView) view.findViewById(R.id.text_items_title);
        Button title_banner_button = (Button) view.findViewById(R.id.btn_back);
        SlidingUpPanelLayout slider = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        LinearLayout slider_banner = (LinearLayout) view.findViewById(R.id.slider_banner);
        TextView slider_banner_text_view = (TextView) view.findViewById(R.id.slider_text);
        // Apply theme to the components.
        title_banner.setBackgroundColor(title_banner_color);
        title_banner_text_view.setText(title_banner_text);
        title_banner_text_view.setTextColor(title_banner_text_color);
        title_banner_text_view.setTextSize(title_banner_text_size);
        title_banner_button.setTextColor(title_banner_back_btn_released_text_color);
        title_banner_button.setTextSize(title_banner_back_btn_released_text_size);
        GradientDrawable title_banner_back_btn_released_gradient = new GradientDrawable();
        title_banner_back_btn_released_gradient.setColor(title_banner_back_btn_released_color);
        title_banner_back_btn_released_gradient.setCornerRadius(title_banner_back_btn_released_radius);
        title_banner_back_btn_released_gradient.setStroke(title_banner_back_btn_released_stroke_thickness, title_banner_back_btn_released_stroke_color);
        title_banner_button.setBackground(title_banner_back_btn_released_gradient);
        slider.setBackgroundColor(slider_banner_background_color);
        //slider_banner.setBackgroundColor(slider_banner_color);
        slider_banner_text_view.setText(slider_banner_text);
        slider_banner_text_view.setTextSize(slider_banner_text_size);
        slider_banner_text_view.setTextColor(slider_banner_text_color);
        //slider_banner_text_view.setBackgroundColor(slider_banner_color);


    }



}
