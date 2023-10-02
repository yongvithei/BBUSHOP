package com.bbubtb111p16y4s1.functions;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Sessions {
    //data member
    private SharedPreferences sp;
    //constructor
    public Sessions(Context context){
        sp= PreferenceManager.getDefaultSharedPreferences(context);

    }
    //setter methods
    public void setUserID(int userID){
        sp.edit().putInt("USER_ID",userID).commit();
    }
    public void setUserName(String userName){
        sp.edit().putString("USER_NAME",userName).commit();
    }
    public void setUserPassword(String userPassword){
        sp.edit().putString("USER_PASSWORD",userPassword).commit();
    }
    public void setUserFullName(String userFullName){
        sp.edit().putString("FULL_NAME",userFullName).commit();
    }
    public void setUserType(String userType){
        sp.edit().putString("USER_TYPE",userType).commit();
    }
    public void setUserEmail(String email){
        sp.edit().putString("USER_EMAIL",email).commit();
    }
    public void setUserImage(String Image){
        sp.edit().putString("USER_IMAGE",Image).commit();
    }

    //getter method
    public int getUserID(){
        return sp.getInt("USER_ID",0);
    }
    public String getUserName(){
        return sp.getString("USER_NAME","");
    }
    public String getUserPassword(){
        return sp.getString("USER_PASSWORD","");
    }
    public String getUserFullName(){
        return sp.getString("FULL_NAME","");
    }
    public String getUserType(){
        return sp.getString("USER_TYPE","");
    }
    public String getUserEmail(){
        return sp.getString("USER_EMAIL","");
    }
    public String getUserImage(){
        return sp.getString("USER_IMAGE","");
    }

    public void ClearSession(){
        sp.edit().clear().commit();
    }
}

