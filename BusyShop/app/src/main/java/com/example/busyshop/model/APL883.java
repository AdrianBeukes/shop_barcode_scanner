package com.example.busyshop.model;

import com.j256.ormlite.field.DatabaseField;

public class APL883 {
    private String description;
    private String image;
    private float price;


    // Getter Methods

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    // Setter Methods

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @DatabaseField(generatedId=true)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
