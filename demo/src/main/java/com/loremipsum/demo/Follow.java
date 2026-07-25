package com.loremipsum.demo;

public class Follow {
    //instances
    private Long followerId;
    private Long followedId;

    public Long getFollowerId(){
        return this.followerId;
    }

    public Long getFollowedId(){
        return this.followedId;
    }

    public void setFollowerId(Long newId){
        this.followerId = newId;
    }

    public void setFollowedId(Long newId){
        this.followedId = newId;
    }
}
