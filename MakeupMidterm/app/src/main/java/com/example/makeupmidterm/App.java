package com.example.makeupmidterm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sid on 24-10-2016.
 */

public class App implements Parcelable, Comparable<App>{

    String name, price, image;
    public App(){

    }


    static public App createApp(JSONObject js){

        App appdata = new App();
        try{
            JSONObject name =js.getJSONObject("im:name");
            appdata.setName(name.getString("label"));
            JSONArray image = js.getJSONArray("im:image");
            JSONObject imagelink = (JSONObject) image.get(0);

            appdata.setImage(imagelink.getString("label"));
            JSONObject price = js.getJSONObject("im:price");
            appdata.setPrice(price.getString("label"));



        }catch (JSONException e) {
            e.printStackTrace();
        }
        return appdata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected App(Parcel in) {
        name = in.readString();
        price = in.readString();
        image = in.readString();
    }

    public static final Creator<App> CREATOR = new Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image);
    }

    @Override
    public int compareTo(App another) {

        if(Double.parseDouble(this.getPrice().replace("$",""))> Double.parseDouble(another.getPrice().replace("$",""))){
            Log.d("compare","return1");
            return 1;
        }else if(Double.parseDouble(this.getPrice().replace("$",""))< Double.parseDouble(another.getPrice().replace("$",""))){
            Log.d("compare","return -1");
            return -1;
        }else {
            Log.d("compare","return 0");
            return 0;
        }
    }
}
