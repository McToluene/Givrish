package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

public class AddItemResponseData {
    @SerializedName("record")
    private String record;

    public AddItemResponseData(String record) {
      this.record = record;
    }

    public String getRecord() {
      return record;
    }
}
