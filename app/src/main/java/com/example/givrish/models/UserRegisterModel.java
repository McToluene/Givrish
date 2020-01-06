package com.example.givrish.models;

public class UserRegisterModel {
  private String fullname;
  private String phone_number;
  private String password;
  private String email;
  private String reg_source;
  private String app_version;
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

  public UserRegisterModel(String fullname, String phone_number,String email,String password, String reg_source, String app_version) {
    this.fullname = fullname;
    this.phone_number = phone_number;
    this.email = email;
    this.password = password;
    this.reg_source = reg_source;
    this.app_version = app_version;
  }
}
