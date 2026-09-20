package com.loremipsum.demo;

public class Follow {
    //instances
    private int followerId;
    private int followedId;

    public int getFollowerId(){
        return this.followerId;
    }

    public int getFollowedId(){
        return this.followedId;
    }

    public void setFollowerId(int newId){
        this.followerId = newId;
    }

    public void setFollowedId(int newId){
        this.followedId = newId;
    }
}
