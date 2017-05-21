package com.omniroid.tapan.movieslist.model;

import java.io.Serializable;

/**
 * Created by DELL on 5/18/2017.
 */

public class Credits implements Serializable {


    String charcterName;
    String name;
    String profile_path;

    public String getCharcterName() {
        return charcterName;
    }

    public void setCharcterName(String charcterName) {
        this.charcterName = charcterName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
