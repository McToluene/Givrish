package com.example.givrish.models;


public class ApiKey {
  private String api_key;
  private String user_id;
  private String item_category_id;
  private String item_subcategory_id;

  public ApiKey(String api_key) {
    this(api_key, "");
  }

  public ApiKey(String api_key, String user_id) {
    this(api_key, user_id, "", "");
  }

  public ApiKey(String api_key, String item_category_id, String item_subcategory_id) {
    this(api_key, "", item_category_id, item_subcategory_id);
  }

  private ApiKey(String api_key, String user_id, String item_category_id, String item_subcategory_id) {
    this.api_key = api_key;
    this.user_id = user_id;
    this.item_category_id = item_category_id;
    this.item_subcategory_id = item_subcategory_id;
  }
}
