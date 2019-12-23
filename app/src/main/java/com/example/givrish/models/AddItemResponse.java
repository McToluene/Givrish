package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

public class AddItemResponse {
  @SerializedName("response_code")
  private String responseCode;

  @SerializedName("response_status")
  private String responseStatus;

  @SerializedName("data")
  private AddItemResponseData data;


  public String getResponseCode() {
    return responseCode;
  }

  public String getResponseStatus() {
    return responseStatus;
  }

  public AddItemResponseData getData() {
    return data;
  }
}
