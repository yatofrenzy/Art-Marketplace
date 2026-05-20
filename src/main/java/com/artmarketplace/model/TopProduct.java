package com.artmarketplace.model;

public class TopProduct {

    private String name;
    private String image;
    private double price;

    public TopProduct() {
    }

    public TopProduct(String name, String image) {
        this.name = name;
        this.image = image;
    }

    // GET NAME
    public String getName() {
        return name;
    }

    // SET NAME
    public void setName(String name) {
        this.name = name;
    }

    // GET IMAGE
    public String getImage() {
        return image;
    }

    // SET IMAGE
    public void setImage(String image) {
        this.image = image;
    }
    
    // GET PRICE
    public double getPrice() {
        return price;
    }

    // SET PRICE
    public void setPrice(double price) {
        this.price = price;
    }
}