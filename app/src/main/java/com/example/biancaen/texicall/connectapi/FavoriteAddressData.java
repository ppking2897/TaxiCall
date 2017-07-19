package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/28.
 */

public class FavoriteAddressData implements Serializable{
    private String favorite;
    private String favorite_lat;
    private String favorite_lon;

    
    public String getFavorite() {
        return favorite;
    }

    public String getFavorite_lat() {
        return favorite_lat;
    }

    public String getFavorite_lon() {
        return favorite_lon;
    }
}
