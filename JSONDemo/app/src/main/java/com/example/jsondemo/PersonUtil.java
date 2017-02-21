package com.example.jsondemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sid on 19-09-2016.
 */
public class PersonUtil {
    static public class PersonsJSONParser{
        static ArrayList<Person> parsePersons(String in) throws JSONException {
            ArrayList<Person> personList = new ArrayList<Person>();
            JSONObject root = new JSONObject(in);
            JSONArray personJSONArray = root.getJSONArray("entry");

            for(int i=0; i<personJSONArray.length();i++){
                JSONObject personsJSONObject = personJSONArray.getJSONObject(i);
                Person person = Person.createPerson(personsJSONObject);

                personList.add(person);
            }
            return personList;
        }
    }
}
