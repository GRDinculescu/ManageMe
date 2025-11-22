package com.manageme.models;

public class Product {
    String name;
    double price;
    int quantity;
    String description;
    String supplier;
    String category;
    String subcategory;
    String brand;

    public Product(String name, double price, int quantity, String description, String supplier, String category, String subcategory, String brand) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.supplier = supplier;
        this.category = category;
        this.subcategory = subcategory;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
