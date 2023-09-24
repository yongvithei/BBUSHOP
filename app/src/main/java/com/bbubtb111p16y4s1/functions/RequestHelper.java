package com.bbubtb111p16y4s1.functions;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHelper {
   public String Execute(String uri, String[] params, String[] values){
       try {
           //create URL
           URL url=new URL(uri);
           //HttpURLConnection
           HttpURLConnection conn =(HttpURLConnection) url.openConnection();
           //Configure the Connection
           conn.setConnectTimeout(15000);
           conn.setReadTimeout(15000);
           conn.setDoInput(true);
           conn.setDoInput(true);
           conn.setRequestMethod("POST");
           //Building parameter
           Uri.Builder builder=new Uri.Builder();
           for(int i=0;i<params.length;i++){
               builder.appendQueryParameter(params[i],values[i]);
           }
           String query =builder.build().getEncodedQuery();
           //Open connection for sending data
           OutputStream os= conn.getOutputStream();
           BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
           writer.write(query);
           writer.flush();
           writer.close();
           os.close();
           conn.connect();

           StringBuffer result=new StringBuffer();
           //check connection and read data
           if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
               //read data sent from server
               InputStream is = conn.getInputStream();
               BufferedReader reader=new BufferedReader(new InputStreamReader(is));
               String line="";
               while ((line= reader.readLine())!=null){
                   result.append(line+"\n");
               }
           }
           return result.toString();
       }catch (Exception ex){
           ex.printStackTrace();
           return null;
       }
   }
}
