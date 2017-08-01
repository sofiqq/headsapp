package com.example.n17r.headsapp.models;

import java.io.Serializable;

/**
 * Created by sofiq on 7/5/17.
 */

public class Answer implements Serializable{
    private String name;
    private boolean right;
    public Answer (String Name, boolean Right) {
        name = Name;
        right = Right;
    }
    public String getName() {return name;}
    public boolean getRight() {return right;}
}
