package com.academyfoods.www.model;

public class SpinnerModel {
    String name,number,offer,image;

    public SpinnerModel(String name, String number, String offer,String image) {
        this.name = name;
        this.number = number;
        this.offer = offer;
        this.image=image;
    }

    public SpinnerModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
