package com.example.gross.mobileclient.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Gross on 14.01.2017.
 */

public class Goods implements Serializable {

    @Expose
    private int id;
    @Expose
    private String title;
    @Expose
    private String img;
    @Expose
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) { this.img = img; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
