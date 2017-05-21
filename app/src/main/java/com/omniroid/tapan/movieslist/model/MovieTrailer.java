package com.omniroid.tapan.movieslist.model;

import java.io.Serializable;

/**
 * Created by DELL on 4/28/2017.
 */

public class MovieTrailer implements Serializable {

    String key;
    String name;
    String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
