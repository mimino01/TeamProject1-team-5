package com.example.myapplication.option;

import android.provider.ContactsContract;

public class Option {
    private String name;
    private String id;
    private String password;
    private Boolean gender;
    private String phoneNumber;

    public void Option () {

    }

    public void Option (String name, String id, String password, Boolean gender, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.gender = gender; //true = man , false = woman
        this.phoneNumber = phoneNumber;
    }

    public void setOption (String name, String id, String password, Boolean gender, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.gender = gender; //true = man , false = woman
        this.phoneNumber = phoneNumber;
    }

    public String getOption () {
        return name + "|" + id + "|" + password + "|" + gender.toString() + "|" + phoneNumber;
    }

    //Getter Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

