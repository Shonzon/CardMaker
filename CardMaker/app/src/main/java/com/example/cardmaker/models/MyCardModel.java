package com.example.cardmaker.models;

import android.net.Uri;

public class MyCardModel {
    private String image;

    public MyCardModel() {

    }

    public MyCardModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
