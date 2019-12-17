package com.example.givrish.models;

import java.util.ArrayList;

public class ProductModel {
  private String title;
  private String location;
  private static int productsId = 0;

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

  public static ArrayList<ProductModel> createProduct() {
    ArrayList<ProductModel> productModels = new ArrayList<ProductModel>();
    productModels.add(new ProductModel("Pairs of jean", "3km"));
    productModels.add(new ProductModel("Tablet", "10km"));
    productModels.add(new ProductModel("HP Folio 9470m", "5km"));
    return productModels;
  }
}
