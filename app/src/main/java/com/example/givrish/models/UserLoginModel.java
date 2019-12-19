package com.example.givrish.models;

public class UserLoginModel {
  private String password;
  private String phone_number;

  public UserLoginModel(String number, String password) {
    this.phone_number = number;
    this.password = password;
  }

}
