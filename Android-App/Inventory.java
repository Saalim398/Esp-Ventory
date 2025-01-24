package com.example.inventory_esp;

public class Inventory {
    private String status;
    private int quantity;

    public Inventory() {
        // Default constructor required for Firebase
    }
    public Inventory(String status, int quantity) {
        this.status = status;
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}