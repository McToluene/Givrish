package com.example.givrish.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "item")
public class AllItemsResponseData implements Parcelable {
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
  private String phone_number;
  private String fullname;
  private List<AllItemsResponseImageData> item_images;

  public AllItemsResponseData(@NonNull String item_id,
                                String user_id,
                                String user_ip,
                                String user_browser,
                                String item_joined,
                                String item_active_status,
                                String item_status,
                                String item_ref_code,
                                String item_title,
                                String item_color,
                                String item_country,
                                String item_address,
                                String item_longitude,
                                String item_latitude,
                                String item_description,
                                String item_category_id,
                                String item_sub_category_id,
                                String phone_number,
                                String fullname,
                                List<AllItemsResponseImageData> item_images) {
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
      this.phone_number = phone_number;
      this.fullname = fullname;
      this.item_images = item_images;
  }


  protected AllItemsResponseData(Parcel in) {
    item_id = in.readString();
    user_id = in.readString();
    user_ip = in.readString();
    user_browser = in.readString();
    item_joined = in.readString();
    item_active_status = in.readString();
    item_status = in.readString();
    item_ref_code = in.readString();
    item_title = in.readString();
    item_color = in.readString();
    item_country = in.readString();
    item_address = in.readString();
    item_longitude = in.readString();
    item_latitude = in.readString();
    item_description = in.readString();
    item_category_id = in.readString();
    item_sub_category_id = in.readString();
    phone_number = in.readString();
    fullname = in.readString();
  }

  public static final Creator<AllItemsResponseData> CREATOR = new Creator<AllItemsResponseData>() {
    @Override
    public AllItemsResponseData createFromParcel(Parcel in) {
      return new AllItemsResponseData(in);
    }

    @Override
    public AllItemsResponseData[] newArray(int size) {
      return new AllItemsResponseData[size];
    }
  };

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

  public String getPhone_number() {
    return phone_number;
  }

  public String getFullname() {
    return fullname;
  }

  public List<AllItemsResponseImageData> getItem_images() {
    return item_images;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(item_id);
    dest.writeString(user_id);
    dest.writeString(user_ip);
    dest.writeString(user_browser);
    dest.writeString(item_joined);
    dest.writeString(item_active_status);
    dest.writeString(item_status);
    dest.writeString(item_ref_code);
    dest.writeString(item_title);
    dest.writeString(item_color);
    dest.writeString(item_country);
    dest.writeString(item_address);
    dest.writeString(item_longitude);
    dest.writeString(item_latitude);
    dest.writeString(item_description);
    dest.writeString(item_category_id);
    dest.writeString(item_sub_category_id);
    dest.writeString(phone_number);
    dest.writeString(fullname);
  }
}

