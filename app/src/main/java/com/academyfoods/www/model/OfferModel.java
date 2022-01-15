package com.academyfoods.www.model;

public class OfferModel {
    String email,name,image,uid,offer,number,take;

    public OfferModel(String email, String name, String image, String uid, String offer, String number,String take) {
        this.email = email;
        this.name = name;
        this.image = image;
        this.uid = uid;
        this.offer = offer;
        this.number = number;
        this.take=take;
    }

    public OfferModel() {
    }

    public String getTake() {
        return take;
    }

    public void setTake(String take) {
        this.take = take;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
