package com.example.finaljjkitchen.Model;

public class Rate {
    private String phone;
    private com.example.finaljjkitchen.Model.Rating ratings;

    public Rate(String phone, com.example.finaljjkitchen.Model.Rating ratings) {
        this.phone = phone;
        this.ratings = ratings;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public com.example.finaljjkitchen.Model.Rating getRatings() {
        return ratings;
    }

    public void setRatings(com.example.finaljjkitchen.Model.Rating ratings) {
        this.ratings = ratings;
    }
}
