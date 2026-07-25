package com.loremipsum.demo;

public class Follows {
    //instances
    private Long followerId;
    private Long followedId;

    public Long getfollowerId(){
        return this.followerId;
    }

    public Long followedId(){
        return this.followedId;
    }

    public void setFollowerId(Long newId){
        this.followerId = newId;
    }

    public void setFollowedId(Long newId){
        this.followedId = newId;
    }
}
