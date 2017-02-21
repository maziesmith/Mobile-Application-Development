package com.example.hw7;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by skaul on 10/6/2016.
 */
public class HourlyWeather implements Parcelable {
    String Hour, condition , temperature , dewpoint, windspeed, winddirection, climate, humidity, feelsLike,
    pressure, icon_url;

    public HourlyWeather() {
    }

    @Override
    public String toString() {
        return "HourlyWeather{" +
                "Hour='" + Hour + '\'' +
                ", condition='" + condition + '\'' +
                ", temperature='" + temperature + '\'' +
                ", dewpoint='" + dewpoint + '\'' +
                ", windspeed='" + windspeed + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", climate='" + climate + '\'' +
                ", humidity='" + humidity + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", pressure='" + pressure + '\'' +
                ", icon_url='" + icon_url + '\'' +
                '}';
    }

    static public HourlyWeather createHour(JSONObject js)
    {
        HourlyWeather hour = new HourlyWeather();
        try {
            JSONObject fct = js.getJSONObject("FCTTIME");
            hour.setHour(fct.getString("civil"));
            JSONObject temp = js.getJSONObject("temp");
            hour.setTemperature(temp.getString("english")+" °F");
            JSONObject dewpoint = js.getJSONObject("dewpoint");
            hour.setDewpoint(dewpoint.getString("english")+" Fahrenheit");
            hour.setCondition(js.getString("condition"));
            hour.setIcon_url(js.getString("icon_url"));
            JSONObject wspd = js.getJSONObject("wspd");
            hour.setWindspeed(wspd.getString("english")+" mph");
            JSONObject wdir = js.getJSONObject("wdir");
            hour.setWinddirection(wdir.getString("degrees")+" °"+wdir.getString("dir"));
            hour.setClimate(js.getString("wx"));
            hour.setHumidity(js.getString("humidity")+"%");
            JSONObject feelslike = js.getJSONObject("feelslike");
            hour.setFeelsLike(feelslike.getString("english")+" Fahrenheit");
            JSONObject mslp = js.getJSONObject("mslp");
            hour.setPressure(mslp.getString("metric")+" hPa");
          //  Log.d("icon",js.getString("icon_url"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  hour;
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

    public String getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
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

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climateType) {
        climate = climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
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
        dest.writeString(Hour);
        dest.writeString(condition);
        dest.writeString(temperature);
        dest.writeString(dewpoint);
        dest.writeString(windspeed);
        dest.writeString(winddirection);
        dest.writeString(climate);
        dest.writeString(humidity);
        dest.writeString(feelsLike);
        dest.writeString(pressure);
        dest.writeString(icon_url);
    }

    public static final Parcelable.Creator<HourlyWeather> CREATOR
            = new Parcelable.Creator<HourlyWeather>() {
        public HourlyWeather createFromParcel(Parcel in) {
            return new HourlyWeather(in);
        }

        public HourlyWeather[] newArray(int size) {
            return new HourlyWeather[size];
        }
    };


    private HourlyWeather(Parcel in) { //order matters of unmarshalling
        this.Hour = in.readString();
        this.condition = in.readString();
        this.temperature = in.readString();
        this.dewpoint = in.readString();
        this.windspeed = in.readString();
        this.winddirection = in.readString();
        this.climate = in.readString();
        this.humidity = in.readString();
        this.feelsLike = in.readString();
        this.pressure = in.readString();
        this.icon_url = in.readString();

    }
}
