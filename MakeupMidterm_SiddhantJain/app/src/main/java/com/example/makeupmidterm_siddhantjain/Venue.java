package com.example.makeupmidterm_siddhantjain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.Exclude;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sid on 21-11-2016.
 */

public class Venue implements Parcelable{

    String venue_id;
    String name;
    String icon_url;
    String category;
    String address;
    int checkin_count;
    int users_count;
    int here_now;
    String key;

    public Venue(String venue_id, String name, String icon_url, String category, String address, int checkin_count, int users_count, int here_now, String key){
        this.venue_id = venue_id;
        this.name = name;
        this.icon_url = icon_url;
        this.category = category;
        this.address = address;
        this.checkin_count = checkin_count;
        this.users_count = users_count;
        this.here_now = here_now;
        this.key = key;

    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("venue_id", venue_id);
        result.put("name", name);
        result.put("category", category);
        result.put("address", address);
        result.put("checkin_count", checkin_count);
        result.put("users_count", users_count);
        result.put("here_now", here_now);
        result.put("key", key);

        return result;
    }

    public Venue(Parcel in) {
        this.venue_id = in.readString();
        this.name= in.readString();
        this.icon_url = in.readString();
        this.category = in.readString();
        this.address = in.readString();
        this.checkin_count = in.readInt();
        this.users_count = in.readInt();
        this.here_now = in.readInt();
        this.key = in.readString();

    }
    public Venue(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCheckin_count() {
        return checkin_count;
    }

    public void setCheckin_count(int checkin_count) {
        this.checkin_count = checkin_count;
    }

    public int getUsers_count() {
        return users_count;
    }

    public void setUsers_count(int users_count) {
        this.users_count = users_count;
    }

    public int getHere_now() {
        return here_now;
    }

    public void setHere_now(int here_now) {
        this.here_now = here_now;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venue_id='" + venue_id + '\'' +
                ", name='" + name + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", checkin_count=" + checkin_count +
                ", users_count=" + users_count +
                ", here_now=" + here_now +
                ", key='" + key + '\'' +
                '}';
    }

    static public Venue createVenue(JSONObject js)
    {
        Venue venue = new Venue();
        try {

            //JSONObject id = js.getJSONObject("id");
            venue.setVenue_id(js.getString("id"));

//            JSONObject name = js.getJSONObject("name");
            venue.setName(js.getString("name"));

            JSONObject add = js.getJSONObject("location");
            JSONArray addr = add.getJSONArray("formattedAddress");
//            JSONObject adds = (JSONObject) addr.get(0);
            venue.setAddress(addr.toString());
            //Log.d("demo", venue.getAddress());

            JSONArray categoryArray = js.getJSONArray("categories");
            JSONObject cate = (JSONObject) categoryArray.get(0);
            venue.setCategory(cate.getString("name"));

            JSONObject img = cate.getJSONObject("icon");
            String pre = img.getString("prefix");
            String suf = img.getString("suffix");
           // String primary = cate.getString("primary");
            venue.setIcon_url(pre+"bg_32"+suf);

            JSONObject stats = js.getJSONObject("stats");
            String checkin = stats.getString("checkinsCount");
            int i = Integer.parseInt(checkin);
            venue.setCheckin_count(i);
            String userscount = stats.getString("usersCount");
            int j = Integer.parseInt(userscount);
            venue.setUsers_count(j);

            JSONObject herenow = js.getJSONObject("hereNow");
            String here = herenow.getString("count");
            int h = Integer.parseInt(here);
            venue.setHere_now(h);





        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  venue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(venue_id);
        dest.writeString(name);
        dest.writeString(icon_url);
        dest.writeString(category);
        dest.writeString(address);
        dest.writeInt(checkin_count);
        dest.writeInt(users_count);
        dest.writeInt(here_now);
    }
    public static final Parcelable.Creator<Venue> CREATOR
            = new Parcelable.Creator<Venue>() {
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

}
