package com.loremipsum.demo;

public class Notifications {

    //Instance variables
    private int id;
    private int user_id;
    private String notification;
    private boolean is_read;

    //Getter and setters
    public int getId(){
        return this.id;
    }

    public int getUserId(){
        return this.user_id;
    }

    public String getNotification(){
        return this.notification;
    }

    public boolean getIsRead(){
        return this.is_read;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setUserId(int newUserId){
        this.user_id = newUserId;
    }

    public void setNotification(String newNotification){
        this.notification = newNotification;
    }

    public void setIsRead(boolean newIs_read){
        this.is_read = newIs_read;
    }
}
