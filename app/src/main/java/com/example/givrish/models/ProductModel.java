package com.example.givrish.models;

import android.content.Context;

import java.util.ArrayList;

public class ProductModel {
  private String user_id;
  private String item_title;
  private String item_color;
  private String item_country;
  private String item_address;
  private String item_longitude;
  private String item_latitude;
  private String item_description;
  private String item_category_id;
  private String item_sub_category_id;

  private static int productsId = 0;
  private static ArrayList<ProductModel> productModels;
  static ArrayList<String> titles=new ArrayList<>();

  public ProductModel() {
  }

  public ProductModel(String item_title, String location) {
    this.item_title = item_title;
  }

  public ProductModel(String user_id, String item_title, String item_color, String item_country, String item_address, String item_longitude, String item_latitude, String item_description, String item_category_id, String item_sub_category_id) {
    this.user_id = user_id;
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

  public String getItem_title() {
    return item_title;
  }



  public static ArrayList<ProductModel> createProduct() {
    ArrayList<ProductModel> productModels = new ArrayList<ProductModel>();
    productModels.clear();
    productModels.add(new ProductModel("Pairs of jean", "3km"));
    productModels.add(new ProductModel("Ipad pro", "10km"));
    productModels.add(new ProductModel("HP Folio 9470m", "5km"));
    productModels.add(new ProductModel("Television", "3km"));
    productModels.add(new ProductModel("Table", "10km"));
    productModels.add(new ProductModel("Pro XD display", "5km"));
    productModels.add(new ProductModel("JBL Headset", "3km"));
    productModels.add(new ProductModel("Pencil", "10km"));
    productModels.add(new ProductModel("Biro", "5km"));
    productModels.add(new ProductModel("Shelves", "3km"));
    productModels.add(new ProductModel("Standing Fan", "10km"));
    productModels.add(new ProductModel("Mac Book Pro", "5km"));
    return productModels;
  }

  public static ArrayList<String> getAllTitle(){
    titles.clear();
    titles.add("Pairs of jean");
    titles.add("Ipad pro");
    titles.add("HP Folio 9470m");
    titles.add("Television");
    titles.add("Table");
    titles.add("Pro XD display");
    titles.add("JBL Headset");
    titles.add("Pencil");
    titles.add("Biro");
    titles.add("Shelves");
    titles.add("Standing Fan");
    titles.add("Mac Book Pro");

 //   titles.add("");
    return titles;
  }


  public static ProductModel getProduct(int position) {
    return productModels.get(position);
  }

//  public void setProductImg(String prodImg) {
//    this.productImg = prodImg.replace(" ","").toLowerCase();
//  }

//  public int getProductImgResourcesId(Context context){
//    return context.getResources().getIdentifier(this.productImg, "drawable", context.getPackageName());
//  }

}
