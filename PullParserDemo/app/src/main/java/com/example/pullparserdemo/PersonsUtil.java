package com.example.pullparserdemo;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sid on 26-09-2016.
 */
public class PersonsUtil {
    static public class PersonsPullParser{
        static ArrayList<Person> parsePersons(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Person person = null;
            ArrayList<Person> personsList = new ArrayList<Person>();
            int event = parser.getEventType();

            while(event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("person")){
                            person = new Person();
                            person.setId(parser.getAttributeValue(null,"id"));
                        }else if(parser.getName().equals("name")){
                            person.setName(parser.nextText().trim());
                        }else if(parser.getName().equals("age")){
                            person.setName(parser.nextText().trim());
                        }else if(parser.getName().equals("department")){
                            person.setName(parser.nextText().trim());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("person")){
                            personsList.add(person);
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
            return personsList;
        }

        }


}
