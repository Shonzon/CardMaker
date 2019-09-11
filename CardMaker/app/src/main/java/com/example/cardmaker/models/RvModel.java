package com.example.cardmaker.models;

public class RvModel {
    private String name,designation,phone,email,dec1,dec2,dec3,logo;
    private String address;

    public RvModel() {
    }

    public RvModel(String name, String designation, String phone, String email, String
                     dec1, String dec2, String dec3, String logo) {
        this.name = name;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.dec1 = dec1;
        this.dec2 = dec2;
        this.dec3 = dec3;
        this.logo = logo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDec1() {
        return dec1;
    }

    public void setDec1(String dec1) {
        this.dec1 = dec1;
    }

    public String getDec2() {
        return dec2;
    }

    public void setDec2(String dec2) {
        this.dec2 = dec2;
    }

    public String getDec3() {
        return dec3;
    }

    public void setDec3(String dec3) {
        this.dec3 = dec3;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "name=" + name +
                ", designation=" + designation +
                ", phone=" + phone +
                ", email=" + email +
                ", dec1=" + dec1 +
                ", dec2=" + dec2 +
                ", dec3=" + dec3 +
                ", logo=" + logo +
                '}';
    }
}
