package com.loremipsum.demo;

import java.util.Date;


public class User{
    private int id;
    private String mail;
    private String username;
    private String password;
    private Date createdAt;


    //getters n setters
    public int getId(){
        return this.id;
    }

    public String getMail(){
        return this.mail;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public Date getCreatedAt(){
        return this.createdAt;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setMail(String newMail){
        this.mail = newMail;
    }

    public void setUsername(String newUserName){
        this.username = newUserName;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public void setCreatedAt(Date newDate){
        this.createdAt = newDate;
    }
}
