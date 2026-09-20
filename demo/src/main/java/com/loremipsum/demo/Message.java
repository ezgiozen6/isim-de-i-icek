package com.loremipsum.demo;

public class Message {

    //instance variables
    private int id;
    private int sender_id;
    private int receiver_id;
    private String content;
    private String date;

    //Getter and Setters
    public int getId(){
        return this.id;
    }

    public int getSenderId(){
        return this.sender_id;
    }

    public int getReceiverId(){
        return this.receiver_id;
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

    public void setSenderId(int newSenderId){
        this.sender_id = newSenderId;
    }

    public void setReceiverId(int newReceiverId){
        this.receiver_id = newReceiverId;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }
}
