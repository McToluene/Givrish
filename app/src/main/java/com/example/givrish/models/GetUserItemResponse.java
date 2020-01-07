package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserItemResponse {
    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_status")
    private String responseStatus;

    @SerializedName("data")
    private List<GetUserItemResponseData> data;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public List<GetUserItemResponseData> getData() {
        return data;
    }
}
