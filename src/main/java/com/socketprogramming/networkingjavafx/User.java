package com.socketprogramming.networkingjavafx;

import java.io.Serializable;

public class User implements Serializable {

    //Username
    private String username;
    public String getUsername() {
        return username;
    }

    //Nickname
    private String nickname;
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.username = nickname;
    }

    //Password
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //Email
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //Constructor
    User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
