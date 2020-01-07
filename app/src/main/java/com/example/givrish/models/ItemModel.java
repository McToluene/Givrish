package com.example.givrish.models;

public class ItemModel {

  private String user_id;
  private String item_title;
  private String item_color;
  private String item_country;
  private String item_state;
  private String item_address;
  private String item_longitude;
  private String item_latitude;
  private String item_description;
  private String item_category_id;
  private String item_sub_category_id;

  public ItemModel(String user_id, String item_title, String item_color, String item_country, String item_state, String item_address, String item_longitude, String item_latitude, String item_description, String item_category_id, String item_sub_category_id) {
    this.user_id = user_id;
    this.item_title = item_title;
    this.item_color = item_color;
    this.item_country = item_country;
    this.item_state = item_state;
    this.item_address = item_address;
    this.item_longitude = item_longitude;
    this.item_latitude = item_latitude;
    this.item_description = item_description;
    this.item_category_id = item_category_id;
    this.item_sub_category_id = item_sub_category_id;
  }
}
