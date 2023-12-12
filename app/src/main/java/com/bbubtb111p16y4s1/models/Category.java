package com.bbubtb111p16y4s1.models;

public class Category {
    private int CategoryID;
    private String CategoryName;
    //constructor
    public Category(int categoryID,String categoryName){
        this.CategoryID = categoryID;
        this.CategoryName = categoryName;
    }
    public int getCategoryID(){
        return CategoryID;
    }
    public String toString(){
        return CategoryName;
    }
}
