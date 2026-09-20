package com.loremipsum.demo;

public class Blog {
    //instances
    private int id;
    private int userId;
    private String content;
    private String date;

    public int getId(){
        return this.id;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getContent(){
        return this.content;
    }

    public String getDate(){
        return this.date;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setUserId(int newUserId){
        this.userId = newUserId;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }
}
