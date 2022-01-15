package com.academyfoods.www.model;

import java.util.ArrayList;

public class OrderModel {
    ArrayList<PopularModel>cart;
    String address,phone,time,coupon,subtotal,delivery,discount,total,username,email,status,date,restaurantName,restaurantImage,type;

    public OrderModel() {
    }

    public OrderModel(String phone, String status, String date, String restaurantName, String restaurantImage) {
        this.phone = phone;
        this.status = status;
        this.date = date;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
    }

    public OrderModel(ArrayList<PopularModel> cart, String address, String phone, String time, String coupon, String subtotal, String delivery, String discount, String total, String username, String email, String status, String date, String restaurantName, String restaurantImage,String type) {
        this.cart = cart;
        this.address = address;
        this.phone = phone;
        this.time = time;
        this.coupon = coupon;
        this.subtotal = subtotal;
        this.delivery = delivery;
        this.discount = discount;
        this.total = total;
        this.username = username;
        this.email = email;
        this.status = status;
        this.date = date;
        this.restaurantName=restaurantName;
        this.restaurantImage=restaurantImage;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PopularModel> getCart() {
        return cart;
    }

    public void setCart(ArrayList<PopularModel> cart) {
        this.cart = cart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "cart=" + cart +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", coupon='" + coupon + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", delivery='" + delivery + '\'' +
                ", discount='" + discount + '\'' +
                ", total='" + total + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
