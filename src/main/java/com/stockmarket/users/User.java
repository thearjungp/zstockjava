package com.stockmarket.users;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String pan;
    private String acntNumber;
    private int role;
    private float availableMargin;


    public User() {}

    public User(String name, String email, String phone, String pan, String acntNumber, int role, float availableMargin) {
        this.name = name;
        this.phone = phone;
        this.pan = pan;
        this.acntNumber = acntNumber;
        this.role = role;
        this.availableMargin = availableMargin;
//        this.errorDetails = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAcntNumber() {
        return acntNumber;
    }

    public void setAcntNumber(String acntNumber) {
        this.acntNumber = acntNumber;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public float getAvailableMargin() {
        return availableMargin;
    }

    public void setAvailableMargin(float availableMargin) {
        this.availableMargin = availableMargin;
    }

}