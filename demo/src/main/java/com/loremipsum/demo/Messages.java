package com.loremipsum.demo;

public class Messages {

    //instance variables
    private Long id;
    private Long sender_id;
    private Long receiver_id;
    private String content;
    private String date;

    //Getter and Setters
    public Long getId(){
        return this.id;
    }

    public Long getSenderId(){
        return this.sender_id;
    }

    public Long getReceiverId(){
        return this.receiver_id;
    }

    public String getContent(){
        return this.content;
    }

    public String getDate(){
        return this.date;
    }

    public void setId(Long newId){
        this.id = newId;
    }

    public void setSenderId(Long newSenderId){
        this.sender_id = newSenderId;
    }

    public void setReceiverId(Long newReceiverId){
        this.receiver_id = newReceiverId;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }
}
