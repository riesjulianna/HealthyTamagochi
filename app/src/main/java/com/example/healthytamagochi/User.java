package com.example.healthytamagochi;

public class User {
    String username;
    String password;
    String email;
    String education;
    String residence;
    String loginData;

    public User(String username, String password, String email, String education, String residence, String loginData) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.education = education;
        this.residence = residence;
        this.loginData = loginData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getLoginData() {
        return loginData;
    }

    public void setLoginData(String loginData) {
        this.loginData = loginData;
    }
}
