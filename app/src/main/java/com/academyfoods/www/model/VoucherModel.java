package com.academyfoods.www.model;

public class VoucherModel {
    String image,name,type,code,valid,value;

    public VoucherModel() {
    }

    public VoucherModel(String image, String name, String type, String code, String valid,String value) {
        this.image = image;
        this.name = name;
        this.type = type;
        this.code = code;
        this.valid = valid;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "VoucherModel{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", valid='" + valid + '\'' +
                '}';
    }
}
