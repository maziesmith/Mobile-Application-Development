package com.example.hw6;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class UserSettingsActivity extends PreferenceActivity
{   static String UNIT = "UNIT";
    static String call = null,city = null, country = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        if(getIntent().getExtras()!= null)
        {
             call = getIntent().getExtras().getString("call");
            city = getIntent().getExtras().getString(MainActivity.CITY_KEY);
            country = getIntent().getExtras().getString(MainActivity.COUNTRY_KEY);
        }

    }


    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            if(call.equals("Main")) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
            if(call.equals("CityWeather"))
            {
                Intent intent = new Intent(getActivity(), CityWeather.class);
                intent.putExtra(MainActivity.CITY_KEY,city);
                intent.putExtra(MainActivity.COUNTRY_KEY,country);
                startActivity(intent);
            }
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            String selected = sharedPrefs.getString("prefTempUnit", "NA");


        }
    }


}