package com.loremipsum.demo;

public class Photo {
    //instances
    private int id;
    private int user_id;
    private String date;
    private String url;

    public int getId(){
        return this.id;
    }

    public int getUserId(){
        return this.user_id;
    }

    public String getDate(){
        return this.date;
    }

    public String getUrl(){
        return this.url;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setUserId(int newUserId){
        this.user_id = newUserId;
    }

    public void setDate(String newDateString){
        this.date = newDateString;
    }

    public void setUrl(String newContentURL){
        this.url = newContentURL;
    }


}
