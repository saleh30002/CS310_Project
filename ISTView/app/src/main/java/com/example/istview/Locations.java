package com.example.istview;

import java.io.Serializable;

public class Locations implements Serializable {

    private String id;

    private String name;
    private String category;
    private int fee;
    private String address;
    private String description;
    private String image;

    public Locations() {
        // TODO Auto-generated constructor stub
    }


    public Locations(String id, String name, String category, int fee, String address, String description, String image) {
        super();
        this.id = id;
        this.name = name;
        this.category = category;
        this.fee = fee;
        this.address = address;
        this.description = description;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public int getFee() {
        return fee;
    }


    public void setFee(int fee) {
        this.fee = fee;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    /*@Override
    public String toString() {
        return "Locations [id=" + id + ", name=" + name + ", category=" + category + ", fee=" + fee + ", address="
                + address + ", decription=" + description + ", image=" + image + "]";
    }*/
}

