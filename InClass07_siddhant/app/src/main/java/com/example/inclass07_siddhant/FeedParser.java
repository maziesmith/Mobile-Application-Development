package com.example.inclass07_siddhant;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sid on 03-10-2016.
 */

public class FeedParser {

    static ArrayList<Feed> ParsePod (InputStream in) throws XmlPullParserException, IOException {

        ArrayList<Feed> podList;

        Feed feed = null;
        XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();

        parser.setInput(in,"UTF-8");

        podList= new ArrayList<Feed>();

        int event=parser.getEventType();
        while(event!=XmlPullParser.END_DOCUMENT)
        {
            switch(event)
            {
                case XmlPullParser.START_TAG:
                    if(parser.getName().equals("entry"))
                    {
                        Log.d("demo","if of xmlpull");
                        feed=new Feed();

                    }else  if(parser.getName().equals("title"))
                    {
                        Log.d("demo","else if title of xmlpull");
                        if(feed!=null) {
                            feed.setTitle(parser.nextText().trim());
                        }
                    }else if(parser.getName().equals("summary"))
                    {
                        Log.d("demo","else if summary of xmlpull");
                        if(feed!=null) {
                            feed.setSummary(parser.nextText().trim());
                        }
                    }else if(parser.getName().equals("im:releaseDate"))
                    {
                        if(feed!=null) {
                            feed.setDate(parser.nextText().trim());
                        }
                    }else if(parser.getName().equals("updated"))
                    {
                        if(feed!=null) {
                            feed.setUpdate(parser.nextText().trim());
                        }
                    }else if(parser.getName().equals("im:image"))
                    {
                        if(feed.getThumbnail()==null||feed.getImage()==null)
                        {

                            if(feed.getImage()==null){
                                Log.d("demo","else if image of xmlpull");
                                feed.setImage(parser.nextText().trim());

                            }else{
                                Log.d("demo","else  image of xmlpull");

                               // feed.setThumbnail(parser.getAttributeValue(null,"url").trim());
                                feed.setThumbnail(parser.nextText().trim());

                            }
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:

                    if(parser.getName().equals("entry")){

                    podList.add(feed);
                }
                break;

                default:
                    break;

            }
            event=parser.next();
        }
        return podList;
    }

}
