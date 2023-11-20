package com.bbubtb111p16y4s1.models;

public class ContactModel {
    // data member
    private String Id;
    private int ImageID;
    private String ImageURL;
    private String ContactName;
    private String PhoneNumber;
    private String Email;


    // constructor
    public ContactModel(int imgId, String name, String phone){
        this.ImageID = imgId;
        this.ContactName = name;
        this.PhoneNumber = phone;


    }
    public ContactModel(String strId,String imageURL, String name, String phone,String email){
        this.Id = strId;
        this.ImageURL = imageURL;
        this.ContactName = name;
        this.PhoneNumber = phone;
        this.Email = email;
    }
    // getter methods
    public String getId() {
        return Id;
    }
    public int getImageID() {
        return ImageID;
    }
    public String getImageURL() {
        return ImageURL;
    }

    public String getContactName() {
        return ContactName;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public String getEmail() {
        return Email;
    }
}
