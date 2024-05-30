package com.example.productsystem;

public class Product {
    private int id;
    private final String name;
    private final double price;
    private final int quantity;
    private final String imageUrl; // Add imageUrl field

    public Product(int id, String name, double price, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl; // Initialize imageUrl
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
