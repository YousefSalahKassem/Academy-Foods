package com.academyfoods.www.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularModel implements Parcelable {
    String category, description, id, image, name, price,quantity,stars,restaurantName,restaurantImage;




    public PopularModel(String category, String description, String id, String image, String name, String price, String stars,String restaurantName,String restaurantImage,String quantity) {
        this.category = category;
        this.description = description;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.stars = stars;
        this.restaurantName=restaurantName;
        this.restaurantImage=restaurantImage;
        this.quantity=quantity;
    }
    public PopularModel() {
    }

    protected PopularModel(Parcel in) {
        category = in.readString();
        description = in.readString();
        id = in.readString();
        image = in.readString();
        name = in.readString();
        price = in.readString();
        stars = in.readString();
        restaurantName=in.readString();
        restaurantImage=in.readString();
        quantity=in.readString();
    }

    public static final Creator<PopularModel> CREATOR = new Creator<PopularModel>() {
        @Override
        public PopularModel createFromParcel(Parcel in) {
            return new PopularModel(in);
        }

        @Override
        public PopularModel[] newArray(int size) {
            return new PopularModel[size];
        }
    };

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "PopularModel{" +
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", stars='" + stars + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantImage='" + restaurantImage + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(id);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeString(stars);
        parcel.writeString(restaurantName);
        parcel.writeString(restaurantImage);
        parcel.writeString(quantity);
    }
}
