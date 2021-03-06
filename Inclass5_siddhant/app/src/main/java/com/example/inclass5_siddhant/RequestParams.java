package com.example.inclass5_siddhant;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by sid on 19-09-2016.
 */
public class RequestParams {
    String method, baseUrl;
    HashMap<String, String> params = new HashMap<String, String>();

    public RequestParams(String method, String baseUrl) {
        this.method = method;
        this.baseUrl = baseUrl;
    }

    public void addParam(String key, String value){
        params.put(key, value);
    }

    public String getEncodedParams(){
        //loop over key/value pairs
        //append to a sb
        //figure out how to add the '&'
        //param1=value1%20value1&param2=value2&param3=value3
        StringBuilder sb = new StringBuilder();
        for(String key: params.keySet()){
            try {
                String value = URLEncoder.encode(params.get(key),"UTF-8");
                if(sb.length()>0){
                    sb.append("&");
                }
                sb.append(key+"="+value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public String getEncodedUrl(){
        return this.baseUrl + "?" + getEncodedParams();
    }
    public HttpURLConnection setupConnection() throws IOException{
        if(method.equals("GET")){
            URL url = new URL(getEncodedUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");//con.setRequestMethod("POST")
            return con;

        }else{
            //POST
            URL url = new URL(this.baseUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(getEncodedParams());
            writer.flush();
            return con;
        }
    }
}
