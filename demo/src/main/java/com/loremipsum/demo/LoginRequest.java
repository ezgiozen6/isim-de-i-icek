package com.loremipsum.demo;

public class LoginRequest {
    private String mail;
    private String password;

    public String getPassword() { return password; }
    public String getMail() { return mail; } 

    public void setMail (String newmail) { this.mail = newmail; }
    public void setPassword(String newpassword) { this.password = newpassword; } 
}
