package com.example.hrvoje.myapplication;

/**
 * Created by hrvoje on 6.2.2017..
 */

public class Model {
    private String image;
    private String title;
    private String description;



    public Model(String image, String title, String description) {

        this.image = image;
        this.title = title;
        this.description = description;


    }

    public Model(){

    }
    public String getDescription() {
        return description;
    }

    public void setDescritpion(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
