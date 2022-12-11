package com.example.myapplication.Activity.Board;

public class BoardClass {

    private String username;
    private String userSex;
    private String destination;
    private String userid;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getManner_point() {
        return manner_point;
    }

    public void setManner_point(double manner_point) {
        this.manner_point = manner_point;
    }

    private double manner_point;

    public BoardClass(String username, String userSex, String destination, long time, String userid, double manner_point) {
        this.username = username;
        this.userSex = userSex;
        this.destination = destination;
        this.time = time;
        this.userid = userid;
        this.manner_point = manner_point;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}