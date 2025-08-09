package com.example.rahmat_ux.model;

import java.io.Serializable;

public class DonatedItem implements Serializable {
    private String category;
    private String name;
    private String quantity;

    public DonatedItem(String category, String name, String quantity) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCategory() { return category; }
    public String getName() { return name; }
    public String getQuantity() { return quantity; }
}