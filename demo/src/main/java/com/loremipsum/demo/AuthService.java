package com.loremipsum.demo;

import org.springframework.stereotype.Service;
import java.util.Date;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class AuthService {
    
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository aUserRepository){
        this.userRepository = aUserRepository;
    }

    //signup/register method
    public String signUp(String mail, String username, String password, String confirmPassword){

        //error messages to display
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty";
        }
        
        if (!password.equals(confirmPassword)) {
            return "Passwords don't match";
        }
        
        if (password.length() < 8) {
            return "Password must be at least 8 characters";
        }
        
        if (userRepository.findByEmail(mail) != null) {
            return "Email already in use";
        }
        
        if (userRepository.findByUsername(username) != null) {
            return "Username already taken";
        }

        //password hashing
        String hashedPassword = encoder.encode(password);

        User user = new User();
        user.setMail(mail);
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setCreatedAt(new Date());

        userRepository.save(user); 
        return "success";      
    }

    public String login(String mail, String password){

        User user = userRepository.findByEmail(mail);
        if(user == null) {
            return "A user with this mail does not exist.";
        }

        if (encoder.matches(password, user.getPassword())) {
            return "success";
        } else {
            return "Wrong password. Please try again."; 
        }
    }
}
