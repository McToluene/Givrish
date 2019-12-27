package com.example.givrish.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class AllItemsResponseData {
    @PrimaryKey
    @NonNull
    private String item_id;
    private String user_id;
    private String user_ip;
    private String user_browser;
    private String item_joined;
    private String item_active_status;
    private String item_status;
    private String item_ref_code;
    private String item_title;
    private String item_color;
    private String item_country;
    private String item_address;
    private String item_longitude;
    private String item_latitude;
    private String item_description;
    private String item_category_id;
    private String item_sub_category_id;

    public AllItemsResponseData(String item_id, String user_id, String user_ip, String user_browser, String item_joined, String item_active_status, String item_status, String item_ref_code, String item_title, String item_color, String item_country, String item_address, String item_longitude, String item_latitude, String item_description, String item_category_id, String item_sub_category_id) {
      this.item_id = item_id;
      this.user_id = user_id;
      this.user_ip = user_ip;
      this.user_browser = user_browser;
      this.item_joined = item_joined;
      this.item_active_status = item_active_status;
      this.item_status = item_status;
      this.item_ref_code = item_ref_code;
      this.item_title = item_title;
      this.item_color = item_color;
      this.item_country = item_country;
      this.item_address = item_address;
      this.item_longitude = item_longitude;
      this.item_latitude = item_latitude;
      this.item_description = item_description;
      this.item_category_id = item_category_id;
      this.item_sub_category_id = item_sub_category_id;
    }

    public String getItem_id() {
      return item_id;
    }

    public String getUser_id() {
      return user_id;
    }

    public String getUser_ip() {
      return user_ip;
    }

    public String getUser_browser() {
      return user_browser;
    }

    public String getItem_joined() {
      return item_joined;
    }

    public String getItem_active_status() {
      return item_active_status;
    }

    public String getItem_status() {
      return item_status;
    }

    public String getItem_ref_code() {
      return item_ref_code;
    }

    public String getItem_title() {
      return item_title;
    }

    public String getItem_color() {
      return item_color;
    }

    public String getItem_country() {
      return item_country;
    }

    public String getItem_address() {
      return item_address;
    }

    public String getItem_longitude() {
      return item_longitude;
    }

    public String getItem_latitude() {
      return item_latitude;
    }

    public String getItem_description() {
      return item_description;
    }

    public String getItem_category_id() {
      return item_category_id;
    }

    public String getItem_sub_category_id() {
      return item_sub_category_id;
    }
  }

