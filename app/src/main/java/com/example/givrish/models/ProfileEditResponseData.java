package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

public class ProfileEditResponseData {
    @SerializedName("record")
    private String record;

    public ProfileEditResponseData(String record) {
        this.record = record;
    }

    public String getRecord() {
        return record;
    }
}
