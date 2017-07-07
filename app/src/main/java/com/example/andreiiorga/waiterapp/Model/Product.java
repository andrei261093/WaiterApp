package com.example.andreiiorga.waiterapp.Model;

/**
 * Created by andreiiorga on 27/06/2017.
 */

public class Product {
    private String name;
    private String imageUrl;

    public Product(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
