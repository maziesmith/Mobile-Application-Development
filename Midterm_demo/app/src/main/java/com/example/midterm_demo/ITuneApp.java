package com.example.midterm_demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by skaul on 10/6/2016.
 */
public class ITuneApp implements Comparable<ITuneApp> {
    String name, thumb_url , price , dollarimage,  currency ,filtered;


    @Override
    public String toString() {
        return "ITuneApp{" +
                "name='" + name + '\'' +
                ", thumb_url='" + thumb_url + '\'' +
                ", price='" + price + '\'' +
                ", dollarimage='" + dollarimage + '\'' +
                ", currency='" + currency + '\'' +
                ", filtered='" + filtered + '\'' +
                '}';
    }

    public String getFiltered() {
        return filtered;
    }

    public void setFiltered(String filtered) {
        this.filtered = filtered;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    static public ITuneApp createITune(JSONObject js)
    {
        ITuneApp app = new ITuneApp();
        try {
            JSONObject name = js.getJSONObject("im:name");
            app.setName(name.getString("label"));
            JSONArray imageArray = js.getJSONArray("im:image");
            JSONObject img = imageArray.getJSONObject(0);
            app.setThumb_url(img.getString("label"));
            JSONObject price = js.getJSONObject("im:price");
            JSONObject amount = price.getJSONObject("attributes");
            app.setPrice(amount.getString("amount"));
            app.setCurrency(amount.getString("currency"));
            double app_price = Double.parseDouble(app.getPrice());
            if(app_price >= 0 && app_price <= 1.99)
            {
                app.setDollarimage("1");
            }else if(app_price >= 2 && app_price <= 5.99)
            {
                app.setDollarimage("2");
            }else
            {
                app.setDollarimage("3");
            }
            app.setFiltered("0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  app;
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

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getDollarimage() {
        return dollarimage;
    }

    public void setDollarimage(String dollarimage) {
        this.dollarimage = dollarimage;
    }


    @Override
    public int compareTo(ITuneApp another) {
        if(Double.parseDouble(this.getPrice()) > Double.parseDouble(another.getPrice()))
        {
            return 1 ;
        }
        else if (Double.parseDouble(this.getPrice()) < Double.parseDouble(another.getPrice()))
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
