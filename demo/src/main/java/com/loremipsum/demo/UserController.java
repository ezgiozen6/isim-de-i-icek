package com.loremipsum.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController 
public class UserController {
    private final JdbcTemplate jdbcTemplate;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(JdbcTemplate aTemplate, AuthService anAuthService, UserRepository aUserRepository,JwtUtil aJwtUtil){
        this.jdbcTemplate = aTemplate;
        this.authService = anAuthService;
        this.userRepository = aUserRepository;
        this.jwtUtil= aJwtUtil;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        String result = authService.signUp(request.getMail(), request.getUsername(), request.getPassword(), request.getConfirmPassword());

        if (result.equals("success")) {
            return ResponseEntity.status(201).body(Map.of("message", "User created successfully"));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", result));
        }
    }


    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        String result = authService.login(request.getMail(), request.getPassword());

        if (result.equals("success")) {
            User user = userRepository.findByEmail(request.getMail());
            String token = jwtUtil.generateToken(String.valueOf(user.getId()));
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getMail()
            ));
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", result));

        }

    }

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        String userSql = "SELECT * FROM users";

        return jdbcTemplate.query(userSql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setMail(rs.getString("mail"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getDate("created_at"));
            return user;
        });
    }

    //
    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable int id){
        String getUserSql = "SELECT * FROM users WHERE id = ?";

        List<User> resultUser = jdbcTemplate.query(getUserSql, (rs, rownum) ->{
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setMail(rs.getString("mail"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getDate("created_at"));
            return user;
        }, id);

        if(resultUser.isEmpty()){return null;}
        else{return resultUser.get(0);}
    }

    @GetMapping("/api/users/username/{username}")
    public User getUserByUsername(@PathVariable String username){
        String getUserSql = "SELECT * FROM users WHERE username = ?";

        List<User> resultUser = jdbcTemplate.query(getUserSql, (rs, rownum) ->{
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setMail(rs.getString("mail"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setCreatedAt(rs.getDate("created_at"));
            return user;
        }, username);

        if(resultUser.isEmpty()){return null;}
        else{return resultUser.get(0);}
    }


    @DeleteMapping("/api/users/{id}")
    public String deleteUser(@PathVariable int id){
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "User deleted";
    }

    @PutMapping("/api/users/{id}")
    public String updateMail(@PathVariable int id, @RequestBody User updatedUser){
        String updateSql = "UPDATE users SET mail = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, updatedUser.getMail(), id);
        return "User mail updated";
    }


}
