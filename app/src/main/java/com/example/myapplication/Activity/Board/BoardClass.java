package com.example.myapplication.Activity.Board;

public class BoardClass {
    private String username;
    private String userSex;
    private String destination;

    public BoardClass(String username, String userSex, String destination) {
        this.username = username;
        this.userSex = userSex;
        this.destination = destination;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}