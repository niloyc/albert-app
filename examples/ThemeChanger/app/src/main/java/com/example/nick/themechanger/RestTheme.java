package com.example.nick.themechanger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 7/08/2015.
 */
public class RestTheme {


    private String background;
    private String foreground;
    private String buttons;
    private String tile_cat;
    private String tile_item;
    private String logo;
    private List<Offer> offer = new ArrayList<Offer>();
    private String reward_img;
    private String id;

    public RestTheme(String background, String foreground, String buttons, String tile_cat,
                     String tile_item, String logo, List<Offer> offer, String reward_img,
                     String id){
        this.background=background;
        this.foreground=foreground;
        this.buttons=buttons;
        this.tile_cat=tile_cat;
        this.tile_item=tile_item;
        this.logo=logo;
        this.offer=offer;
        this.reward_img=reward_img;
        this.id=id;
    }

    public String getBackground(){return background;}
    public void setBackground(String background){ this.background=background;}
    public String getForeground(){return foreground;}
    public void setForeground(String foreground){this.foreground = foreground;}
    public String getButtons(){return buttons;}
    public void setButtons(String buttons){this.buttons = buttons;}
    public String getTile_cat(){return tile_cat;}
    public void setTile_cat(String tile_cat){this.tile_cat=tile_cat;}
    public String getTile_item(){return tile_item;}
    public void setTile_item(String tile_item){this.tile_item=tile_item;}
    public String getLogo(){return logo;}
    public void setLogo(String logo){this.logo=logo;}
    public List<Offer> getOffer(){return offer;};
    public void setOffer(List<Offer> offer){this.offer=offer;}
    public String getReward_img(){return reward_img;}
    public void setReward_img(String reward_img){this.reward_img=reward_img;}
    public String getId(){return id;}
    public void setId(String id){this.id=id;}

}
