package com.loremipsum.demo;

public class Blog {
    //instances
    private Long id;
    private Long userId;
    private String content;
    private String date;

    public Long getId(){
        return this.id;
    }

    public Long getUserId(){
        return this.userId;
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

    public void setUserId(Long newUserId){
        this.userId = newUserId;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }
}
