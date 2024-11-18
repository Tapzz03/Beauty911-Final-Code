package com.beauty911;

public class ModelServices {
    private String name;
    private double price;

    public ModelServices() {
        // Empty constructor required for Firestore
    }

    public ModelServices(String name, String description, double price) {
        this.name = name;
        this.price = price;
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
}
