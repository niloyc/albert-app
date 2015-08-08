package com.example.nick.themechanger;

/**
 * Created by Nick on 7/08/2015.
 */
public class Offer {
    private String url;
    private String description;

    public Offer(String url, String description){
        this.url = url;
        this.description = description;
    }

    public String getUrl(){return url;}
    public void setUrl(String url){this.url = url;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}


}
