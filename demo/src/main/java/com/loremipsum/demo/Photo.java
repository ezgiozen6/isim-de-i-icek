package com.loremipsum.demo;

public class Photo {
    //instances
    private Long id;
    private Long user_id;
    private String date;
    private String url;

    public Long getId(){
        return this.id;
    }

    public Long getUserId(){
        return this.user_id;
    }

    public String getDate(){
        return this.date;
    }

    public String getUrl(){
        return this.url;
    }

    public void setId(Long newId){
        this.id = newId;
    }

    public void setUserId(Long newUserId){
        this.user_id = newUserId;
    }

    public void setDate(String newDateString){
        this.date = newDateString;
    }

    public void setUrl(String newContentURL){
        this.url = newContentURL;
    }


}
