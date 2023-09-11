package com.bbubtb111p16y4s1.models;

public class ItemModel {
    // data members
    private int ImageID;
    private String ItemName;
    private double UnitPrice;
    private String Status;

    // constructor
    public ItemModel(int imgId, String name, double unitPrice, String status){
        this.ImageID = imgId;
        this.ItemName = name;
        this.UnitPrice = unitPrice;
        this.Status = status;
    }
    // getter methods
    public int getImageID() {
        return ImageID;
    }
    public String getItemName() {
        return ItemName;
    }
    public double getUnitPrice() {
        return UnitPrice;
    }
    public String getStatus() {
        return Status;
    }
}
