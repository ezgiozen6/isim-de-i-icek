package com.loremipsum.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController 
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate aTemplate){
        this.jdbcTemplate = aTemplate;
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        String userSql = "SELECT id, mail FROM users";

        return jdbcTemplate.query(userSql, (resultset, rowNum) -> {
            User user = new User();
            user.setId(resultset.getLong("id"));
            user.setMail(resultset.getString("mail"));
            return user;
        });
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Long id){
        String getUserSql = "SELECT id, mail FROM users WHERE id = ?";

        List<User> resultUser = jdbcTemplate.query(getUserSql, (resultset, rownum) ->{
            User user = new User();
            user.setId(resultset.getLong("id"));
            user.setMail(resultset.getString("mail"));
            return user;
        }, id);

        if(resultUser.isEmpty()){return null;}
        else{return resultUser.get(0);}
    }

    //Creating a user
    @PostMapping("/api/users")
    public String createUser(@RequestBody User newUser){
        String sql = "INSERT INTO users (mail, password) VALUES (?,?)";
        jdbcTemplate.update(sql, newUser.getMail(), "temporary_placeholder");
        return "CONGGRATSSS U CREATED A USER!!!!!!! - JONATHAN ORACLE";
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUser(@PathVariable Long id){
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "OOPS U DELETED A USER";
    }

    @PutMapping("/api/users/{id}")
    public String updateMail(@PathVariable Long id, @RequestBody User updatedUser){
        String updateSql = "UPDATE users SET mail = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, updatedUser.getMail(), id);
        return "YAY U UPDATED UR USER MAIL";
    }


}
