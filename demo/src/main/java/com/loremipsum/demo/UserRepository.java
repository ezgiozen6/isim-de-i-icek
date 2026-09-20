package com.loremipsum.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate aTemplate){
        this.jdbcTemplate = aTemplate;
    }

    //finding a user by mail
    public User findByEmail(String mail){

        String sql = "SELECT * FROM users WHERE mail = ?";
         
        try{
            
            List<User> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setMail(rs.getString("mail"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getDate("created_at"));

                return user;
            }, mail);
            
            if(results.isEmpty()){return null;}
            else{return results.get(0);}

        } catch (Exception e) {
            return null;
        }
    }

    //finding a user by username
    public User findByUsername(String username){

        String sql = "SELECT * FROM users WHERE useername = ?";
         
        try{
            
            List<User> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setMail(rs.getString("mail"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getDate("created_at"));

                return user;
            }, username);
            
            if(results.isEmpty()){return null;}
            else{return results.get(0);}
            
        } catch (Exception e) {
            return null;
        }
    }

    //finding a user by id
    public User findById(int id){

        String sql = "SELECT * FROM users WHERE id = ?";
         
        try{
            
            List<User> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setMail(rs.getString("mail"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getDate("created_at"));

                return user;
            }, id);
            
            if(results.isEmpty()){return null;}
            else{return results.get(0);}
            
        } catch (Exception e) {
            return null;
        }
    }

    //finding a user by email
    public List<User> findAll(){

        String sql = "SELECT * FROM users";
            
        List<User> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setMail(rs.getString("mail"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getDate("created_at"));

            return user;
        });

        return results;
    }

    //saving a user
    public void save(User user){
        String sql = "INSERT INTO users (mail, username, password, created_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getMail(), user.getUsername(), user.getPassword(), user.getCreatedAt());
    }
}
