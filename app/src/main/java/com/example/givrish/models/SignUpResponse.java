package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

  @SerializedName("user_id")
  private String  userId;

  public SignUpResponse(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }
}
