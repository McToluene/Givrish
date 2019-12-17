package com.example.givrish.models;

import java.util.ArrayList;

public class ProductModel {
  private String title;
  private String location;
  private static int productsId = 0;

  public ProductModel() {
  }

  public ProductModel(String title, String location) {
    this.title = title;
    this.location = location;
  }

  public String getTitle() {
    return title;
  }

  public String getLocation() {
    return location;
  }


 static ArrayList<ProductModel> productModels = new ArrayList<>();
  public static ArrayList<ProductModel> createProduct() {
    productModels.add(new ProductModel("Pairs of jean", "3km"));
    productModels.add(new ProductModel("Tablet", "10km"));
    productModels.add(new ProductModel("HP Folio 9470m", "5km"));
    return productModels;
  }

  static ArrayList<String> titles=new ArrayList<>();
  public static ArrayList<String> getAllTitle(){
    titles.add("Pairs of jean");
    titles.add("Tablet");
    titles.add("HP Folio 9470m");
    return titles;
  }
}
