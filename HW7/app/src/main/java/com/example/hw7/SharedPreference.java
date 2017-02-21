package com.example.hw7;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SharedPreference {
    public static final String PREFS_NAME = "WEATHER_APP";
    public static final String FAVORITES = "Favorite";
    public SharedPreference() {
        super();
    }
    public void storeFavorites(Context context, List favorites) {
// used for store arrayList in json format
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }
    public ArrayList<Favorite> loadFavorites(Context context) {
// used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List favorites;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Favorite[] favoriteItems = gson.fromJson(jsonFavorites,Favorite[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Favorite>(favorites);
        } else
            return null;
        return (ArrayList<Favorite>) favorites;
    }
    public boolean addFavorite(Context context, Favorite favSampleList) {
        ArrayList<Favorite> favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Favorite>();
        Favorite updateFav = null;
        int i = 0 ;
        if(favorites.size()>0) {
            for (i = 0; i < favorites.size(); i++) {
                if (favSampleList.equals(favorites.get(i))) {
                    updateFav = favorites.get(i);
                    break;
                }
            }

            if (i == favorites.size()) {
                favorites.add(favSampleList);
                storeFavorites(context, favorites);
            } else {
                favorites.get(i).setTemperature(favSampleList.getTemperature());
                favorites.get(i).setDate(favSampleList.getDate());
                storeFavorites(context, favorites);
                return false;
            }
        }
        else
        {
            favorites.add(favSampleList);
            storeFavorites(context, favorites);
        }


        return true;
    }
    public void removeFavorite(Context context,  Favorite favSampleList, int position) {
        ArrayList<Favorite> favorites = loadFavorites(context);

        if (favorites != null) {
            favorites.remove(position);
            for(Favorite f : favorites)
            {
                Log.d("in shared", f.toString());
            }
            storeFavorites(context, favorites);
        }
    }
}