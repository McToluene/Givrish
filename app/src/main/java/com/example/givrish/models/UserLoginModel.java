package com.example.givrish.models;

public class UserLoginModel {
  private String password;
  private String phone_number;
  private String user_id;

  public UserLoginModel(String number, String password) {
    this.phone_number = number;
    this.password = password;
  }

    public UserLoginModel(String user_id, String phone_number, String password) {

this.user_id = user_id;
      this.phone_number = phone_number;
      this.password = password;
    }

    public UserLoginModel(String pass) {
      this.phone_number = pass;
    }
}
