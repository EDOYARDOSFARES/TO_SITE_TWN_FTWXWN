package com.athletico.eshop;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String imagePath;

    public Product(int id, String name, double price, int stock, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getImagePath() { return imagePath; }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.err.println("ERROR: Attempt of inserting a negative price.");
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            System.err.println("ERROR: The quantity cannot be negative.");
        }
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void updateDetails(String name, double price, int stock) {
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}