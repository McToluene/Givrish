package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthResponseDto {
    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_status")
    private String responseStatus;

    @SerializedName("data")
    private List<String> data;



    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public List<String> getData() {
        return data;
    }
}
