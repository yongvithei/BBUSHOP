package com.bbubtb111p16y4s1.functions;

import java.security.MessageDigest;
import java.util.Formatter;

public class EncryptPassword {
    public static String MD5(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(password.getBytes("UTF-8"));
            return ConvertToHexa(md.digest());
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static String ConvertToHexa(byte[] password) {
        Formatter form = new Formatter();
        for(byte pwd : password){
            form.format("%02x", pwd);
        }
        String result = form.toString();
        form.close();
        return result;
    }
}
