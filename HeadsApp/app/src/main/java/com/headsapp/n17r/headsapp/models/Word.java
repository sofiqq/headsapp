package com.headsapp.n17r.headsapp.models;

import java.io.Serializable;

/**
 * Created by sofiq on 7/4/17.
 */

public class Word implements Serializable{

    private String name;
    private String picture;

    public Word (String Name, String Picture) {
        name = Name;
        picture = Picture;
    }

    public String getName() {return name;}
    public String getPicture() {return picture;}
}
