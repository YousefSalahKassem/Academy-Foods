package com.academyfoods.www.model;

public class CategoryModel {
    int image;
    String name;


    public CategoryModel( int image, String name) {
        this.image = image;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
