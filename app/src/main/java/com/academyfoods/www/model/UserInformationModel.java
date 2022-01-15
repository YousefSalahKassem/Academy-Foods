package com.academyfoods.www.model;

public class UserInformationModel {
    String name, email, phone, uid, image;

    public UserInformationModel(String name, String email, String phone, String uid, String image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.uid = uid;
        this.image = image;
    }

    public UserInformationModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
