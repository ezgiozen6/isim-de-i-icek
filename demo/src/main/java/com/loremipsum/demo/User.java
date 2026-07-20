package com.loremipsum.demo;

public class User {
    private Long id;
    private String mail;

    //getters n setters
    public Long getId(){
        return this.id;
    }

    public String getMail(){
        return this.mail;
    }

    public void setId(Long newId){
        this.id = newId;
    }

    public void setMail(String newMail){
        this.mail = newMail;
    }
}
