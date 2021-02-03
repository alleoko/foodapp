package com.example.finaljjkitchen.Model;

public class UsersForgotModel {
    private String name, phone, password, image, address, emadd;
    public UsersForgotModel()
    {

    }

    public UsersForgotModel(String name, String phone, String password, String image, String address, String emadd) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.address = address;
        this.emadd = emadd;

    }

    public String getEmadd() {
        return emadd;
    }

    public void setEmadd(String emadd) {
        this.emadd = emadd;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
