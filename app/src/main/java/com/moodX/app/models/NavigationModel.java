package com.moodX.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NavigationModel {

    @SerializedName("img")
    @Expose
    String img;
    @SerializedName("title")
    @Expose
    String title;

    public NavigationModel(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}