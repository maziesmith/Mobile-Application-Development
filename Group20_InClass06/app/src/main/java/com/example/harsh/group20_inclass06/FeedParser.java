package com.example.harsh.group20_inclass06;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class FeedParser {

    static ArrayList<Feed> ParseNews (InputStream in) throws XmlPullParserException, IOException {

        ArrayList<Feed> newsList;

        Feed feed = null;
        XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();

        parser.setInput(in,"UTF-8");

        newsList= new ArrayList<Feed>();

        int event=parser.getEventType();
        while(event!=XmlPullParser.END_DOCUMENT)
        {
            switch(event)
            {
                case XmlPullParser.START_TAG:
                    if(parser.getName().equals("item"))
                    {

                        feed=new Feed();

                    }else  if(parser.getName().equals("title")){
                        Log.d("demo","else if title");
                        if(feed!=null) {

                            feed.setTitle(parser.nextText().trim());
                        }

                    }else  if(parser.getName().equals("description")){
                        Log.d("demo","else if desc of xmlpull");

                        if(feed!=null) {

                            feed.setDescription(parser.nextText().trim());
                        }

                    }else  if(parser.getName().equals("link")){

                        if(feed!=null) {

                            feed.setLink(parser.nextText().trim());
                        }

                    }else  if(parser.getName().equals("pubDate")){

                        if(feed!=null) {

                            feed.setPubdate(parser.nextText().trim());
                        }

                    }else  if(parser.getName().equals("media:content")){

                        if(feed.getThumbnail()==null||feed.getImage()==null)
                        {

                            if(feed.getImage()==null){

                                feed.setImage(parser.getAttributeValue(null,"url").trim());

                            }else{

                                feed.setThumbnail(parser.getAttributeValue(null,"url").trim());
                            }
                        }


                    }
                    break;

                case XmlPullParser.END_TAG:

                    if(parser.getName().equals("item"))
                    {
                        newsList.add(feed);
                    }
                    break;

                default:

                    break;
            }

             event=parser.next();
        }


       return newsList;


    }

}
