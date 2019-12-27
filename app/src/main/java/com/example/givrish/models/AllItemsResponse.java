package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllItemsResponse {
  @SerializedName("response_code")
  private String responseCode;

  @SerializedName("response_status")
  private String responseStatus;

  @SerializedName("record_count")
  private String recordCount;

  @SerializedName("data")
  private List<AllItemsResponseData> data;


  public AllItemsResponse(String responseCode, String responseStatus, String recordCount, List<AllItemsResponseData> data, List<AllItemsResponseImageData> allItemsResponseImageData) {
    this.responseCode = responseCode;
    this.responseStatus = responseStatus;
    this.recordCount = recordCount;
    this.data = data;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public String getResponseStatus() {
    return responseStatus;
  }

  public String getRecordCount() {
    return recordCount;
  }

  public List<AllItemsResponseData> getData() {
    return data;
  }

}
