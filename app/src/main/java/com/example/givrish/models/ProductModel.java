package com.example.givrish.models;

import java.util.ArrayList;

public class ProductModel {
  private String title;
  private String location;
  private static int productsId = 0;
  private static ArrayList<ProductModel> productModels;
  static ArrayList<String> titles=new ArrayList<>();

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


  public static ArrayList<ProductModel> createProduct() {
    ArrayList<ProductModel> productModels = new ArrayList<ProductModel>();
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
    titles.add("Pairs of jean");
    titles.add("Tablet");
    titles.add("HP Folio 9470m");
    return titles;
  }


  public static ProductModel getProduct(int position) {
    return productModels.get(position);
  }
}
