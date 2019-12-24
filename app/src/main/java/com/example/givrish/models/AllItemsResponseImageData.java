package com.example.givrish.models;

import com.google.gson.annotations.SerializedName;

public class AllItemsResponseImageData {
  @SerializedName("item_image_id")
  private String itemImageId;

  @SerializedName("item_id")
  private String itemId;

  @SerializedName("item_large_image_name")
  private String itemLargeImageName;

  @SerializedName("item_small_image_name")
  private String itemSmallImageName;

  @SerializedName("item_image_ref_code")
  private String itemImageRefCode;

  @SerializedName("item_image_status")
  private String itemImageStatus;

  public AllItemsResponseImageData(String itemImageId, String itemId, String itemLargeImageName, String itemSmallImageName, String itemImageRefCode, String itemImageStatus) {
    this.itemImageId = itemImageId;
    this.itemId = itemId;
    this.itemLargeImageName = itemLargeImageName;
    this.itemSmallImageName = itemSmallImageName;
    this.itemImageRefCode = itemImageRefCode;
    this.itemImageStatus = itemImageStatus;
  }

  public String getItemImageId() {
    return itemImageId;
  }

  public String getItemId() {
    return itemId;
  }

  public String getItemLargeImageName() {
    return itemLargeImageName;
  }

  public String getItemSmallImageName() {
    return itemSmallImageName;
  }

  public String getItemImageRefCode() {
    return itemImageRefCode;
  }

  public String getItemImageStatus() {
    return itemImageStatus;
  }
}
