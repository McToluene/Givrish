package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

public class ProfileEditResponse {
    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_status")
    private String responseStatus;

    @SerializedName("data")
    private ProfileEditResponseData data;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public ProfileEditResponseData getData() {
        return data;
    }
}
