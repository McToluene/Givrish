package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemSubCategoryResponse {
	@SerializedName("response_code")
	private String responseCode;
	
	@SerializedName("response_status")
	private String responseStatus;
	
	@SerializedName("data")
	private List<ItemSubCategoryData> data;
	
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
	
	public List<ItemSubCategoryData> getData() {
		return data;
	}
}
