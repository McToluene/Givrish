package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemCategoryResponse {
    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_status")
    private String responseStatus;

    @SerializedName("data")
    private List<ItemCategoryData> data;

    @SerializedName("record_count")
    private String recordCount;

    public String getRecordCount() {
        return recordCount;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public List<ItemCategoryData> getData() {
        return data;
    }


}
