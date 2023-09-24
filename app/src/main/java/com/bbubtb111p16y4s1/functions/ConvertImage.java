package com.bbubtb111p16y4s1.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ConvertImage {
    public static Bitmap StringToImage(String image){
        Bitmap bitmap=null;
        try{
            byte[] img= Base64.decode(image,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(img,0, img.length);
            return bitmap;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
