package com.bbubtb111p16y4s1.models;

public class ContactModel {
    // data member
    private int ImageID;
    private String ContactName;
    private String PhoneNumber;

    // constructor
    public ContactModel(int imgId, String name, String phone){
        this.ImageID = imgId;
        this.ContactName = name;
        this.PhoneNumber = phone;
    }
    // getter methods
    public int getImageID() {
        return ImageID;
    }
    public String getContactName() {
        return ContactName;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
