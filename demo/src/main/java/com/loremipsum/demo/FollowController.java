package com.loremipsum.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FollowController {
    private JdbcTemplate jdbcTemplate;

    public FollowController(JdbcTemplate aTemplate){
        this.jdbcTemplate = aTemplate;
    }

    //kullanıcı KİMLERİ takip ediyor
    @GetMapping("/api/follows/following")
    public List<Follow> getFollows(@RequestParam Long followerId){
        String sql = "SELECT * FROM follows WHERE follower_id = ?";

        return jdbcTemplate.query(sql, (resultset, rownum) ->{
            Follow follow = new Follow();
            follow.setFollowedId(resultset.getLong("followed_id"));
            follow.setFollowerId(followerId);
            return follow;
        }, followerId);
    }

    //kullanıcıyı kimler takip ediyor
    @GetMapping("/api/follows/followers")
    public List<Follow> getFollowing(@RequestParam Long followedId){
        String sql = "SELECT * FROM follows WHERE followed_id = ?";

        return jdbcTemplate.query(sql, (resultset, rownum) -> {
            Follow follow = new Follow();
            follow.setFollowedId(followedId);
            follow.setFollowerId(resultset.getLong("follower_id"));
            return follow;
        }, followedId);
    }

    @PostMapping("/api/follows")
    public String createFollow(@RequestBody Follow newFollow){
        String sql = "SELECT * FROM follows WHERE followed_id = ? AND follower_id = ?";

        if(newFollow.getFollowedId().equals(newFollow.getFollowerId())){
            return "cant follow urself";
        }

        List<Follow> queryList = jdbcTemplate.query(sql, (resultset, rownum) -> {
            Follow follow = new Follow();
            follow.setFollowedId(newFollow.getFollowedId());
            follow.setFollowerId(newFollow.getFollowerId());
            return follow;
        }, newFollow.getFollowedId(), newFollow.getFollowerId());

        if (queryList.isEmpty()){
            String createSql = "INSERT INTO follows (followed_id, follower_id) VALUES (?, ?)";
            jdbcTemplate.update(createSql, newFollow.getFollowedId(), newFollow.getFollowerId());
            return "created follow";
        }

        else{
            return "followed already";
        }

    }

    @DeleteMapping("/api/follows/{followerId}/{followedId}")
    public String unFollow(@PathVariable Long followedId, @PathVariable Long followerId){
        String sql = "DELETE FROM follows WHERE follower_id = ? AND followed_id = ?";
        jdbcTemplate.update(sql, followerId, followedId);

        return "deleted follow";
    }



}
