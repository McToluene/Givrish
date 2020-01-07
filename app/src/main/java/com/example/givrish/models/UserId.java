package com.example.givrish.models;

public class UserId {

    String user_id;
    String fullname;

    public String getUser_id() {
        return user_id;
    }

    public UserId(String user_id) {
        this.user_id = user_id;
    }

    public UserId(String user_id, String fullname) {
        this.user_id = user_id;
        this.fullname=fullname;
    }
}
