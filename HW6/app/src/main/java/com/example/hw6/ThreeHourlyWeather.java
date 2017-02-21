/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by skaul on 10/6/2016.
 */
public class ThreeHourlyWeather implements Parcelable, Comparable<ThreeHourlyWeather> {
    String Date, Hour, condition, temperature, humidity, pressure, icon_url, windspeed, winddirection, datefull;

    public ThreeHourlyWeather() {
    }

    public String getDatefull() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.util.Date dateinformat = null;
        try {
            dateinformat = format.parse(datefull);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("MMM dd, yyyy");
        return format.format(dateinformat);

    }

    public void setDatefull(String datefull) {
        this.datefull = datefull;
    }

    @Override
    public String toString() {
        return "ThreeHourlyWeather{" +
                "Date='" + Date + '\'' +
                ", Hour='" + Hour + '\'' +
                ", condition='" + condition + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", windspeed='" + windspeed + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", datefull='" + datefull + '\'' +
                '}';
    }

    public String getDate() {
        return Date;
    }


    public void setDate(String date) {
        Date = date;
    }

    static public ThreeHourlyWeather createHour(JSONObject js) {
        ThreeHourlyWeather hour = new ThreeHourlyWeather();
        try {
            JSONObject main = js.getJSONObject("main");
            String temperature = main.getString("temp");
            double tempK = Double.parseDouble(temperature);
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            hour.setTemperature(numberFormat.format(tempK) + "");
            hour.setPressure(main.getString("pressure") + " hPa");
            hour.setHumidity(main.getString("humidity") + "%");
            JSONArray weather = js.getJSONArray("weather");
            JSONObject weather0 = (JSONObject) weather.get(0);
            hour.setCondition(weather0.getString("main"));
            String icon_id = weather0.getString("icon");

            hour.setIcon_url("http://openweathermap.org/img/w/" + icon_id + ".png");
            Log.d("inpojo", "image url is " + hour.getIcon_url() + "icon id is " + icon_id);
            JSONObject wind = js.getJSONObject("wind");
            hour.setWindspeed(wind.getString("speed") + " mps");
            hour.setWinddirection(wind.getString("deg") + " °");
            String datetime = js.getString("dt_txt");
            hour.setDatefull(datetime.substring(0, 10));
            hour.setDate(datetime.substring(8, 10));
            int time = Integer.parseInt(datetime.substring(11, 13));
            if (time >= 12) {
                if (time != 12) {
                    time = time - 12;
                    hour.setHour("" + time + " PM");
                } else {
                    hour.setHour("" + time + " PM");
                }
            } else {
                hour.setHour("" + time + " AM");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hour;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }


    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Date);
        dest.writeString(Hour);
        dest.writeString(condition);
        dest.writeString(temperature);
        dest.writeString(windspeed);
        dest.writeString(winddirection);
        dest.writeString(humidity);
        dest.writeString(pressure);
        dest.writeString(icon_url);
    }

    public static final Parcelable.Creator<ThreeHourlyWeather> CREATOR
            = new Parcelable.Creator<ThreeHourlyWeather>() {
        public ThreeHourlyWeather createFromParcel(Parcel in) {
            return new ThreeHourlyWeather(in);
        }

        public ThreeHourlyWeather[] newArray(int size) {
            return new ThreeHourlyWeather[size];
        }
    };


    private ThreeHourlyWeather(Parcel in) { //order matters of unmarshalling
        this.Date = in.readString();
        this.Hour = in.readString();
        this.condition = in.readString();
        this.temperature = in.readString();
        this.windspeed = in.readString();
        this.winddirection = in.readString();
        this.humidity = in.readString();
        this.pressure = in.readString();
        this.icon_url = in.readString();

    }

    @Override
    public int compareTo(ThreeHourlyWeather another) {
        if (Integer.parseInt(this.getDate()) > Integer.parseInt(another.getDate())) {
            return 1;
        } else if (Integer.parseInt(this.getDate()) < Integer.parseInt(another.getDate())) {
            return -1;
        } else {
            return 0;
        }
    }
}
