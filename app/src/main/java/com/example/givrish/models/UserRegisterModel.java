package com.example.givrish.models;

public class UserRegisterModel {

  private String fullname;
  private String phone_number;
  private String password;
  private String email = "";
  private String reg_source = "3";
  private String app_version = "1.14.0";
  private String mac_address;

  public UserRegisterModel(String fullName, String number, String password) {
    this.fullname = fullName;
    this.phone_number = number;
    this.password = password;
  }


  public UserRegisterModel(String phone_number, String mac_address) {
    this.phone_number = phone_number;
    this.mac_address = mac_address;
  }
}
