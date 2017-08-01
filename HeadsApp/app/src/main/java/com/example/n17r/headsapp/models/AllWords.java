package com.example.n17r.headsapp.models;

import java.io.Serializable;

/**
 * Created by sofiq on 7/21/17.
 */

public class AllWords implements Serializable {

    private String picture;
    private String name;
    public AllWords() {
    }

    public AllWords (String Name, String Picture) {
        name = Name;
        picture = Picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
