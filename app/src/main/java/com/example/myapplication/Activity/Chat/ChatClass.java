package com.example.myapplication.Activity.Chat;

public class ChatClass {
    String name;
    String chat;
    String time;
    private int viewType;

    public ChatClass(String chat, String time, int viewType){
        this.chat = chat;
        this.time = time;
        this.viewType = viewType;
    }
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getChat(){
        return chat;
    }
    public void setChat(String chat){
        this.chat=chat;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }
    public int getViewType() { return viewType;}
    public void setViewType(int viewType) { this.viewType = viewType; }
}
