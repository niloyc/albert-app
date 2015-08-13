package com.argonauts.gratisuser.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Niloy on 07/08/2015.
 */
public class Utilities {

    private static Typeface black;
    private static Typeface bold;
    private static Typeface bold_italic;
    private static Typeface italic;
    private static Typeface regular;
    private static Typeface light;

    public static void loadFonts(Context mContext) {
        black = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato-Black.ttf");
        bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato-Bold.ttf");
        bold_italic = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato-BoldItalic.ttf");
        italic = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato-BoldItalic.ttf");
        regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato-Regular.ttf");
        light = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato-Light.ttf");
    }

    public static void setFont(ViewGroup group) {
        int count = group.getChildCount();
        View v;
        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView || v instanceof Button) {

                Typeface typeface = ((TextView) v).getTypeface();

                if(typeface.getStyle()==Typeface.BOLD_ITALIC){
                    ((TextView) v).setTypeface(bold_italic);
                }else if(typeface.getStyle()==Typeface.BOLD){
                    ((TextView) v).setTypeface(bold);
                }else if(typeface.getStyle()==Typeface.ITALIC){
                    ((TextView) v).setTypeface(italic);
                }else{
                    ((TextView) v).setTypeface(regular);
                }

            }else if(v instanceof ViewGroup) {
                setFont((ViewGroup) v);
            }
        }
    }

}
