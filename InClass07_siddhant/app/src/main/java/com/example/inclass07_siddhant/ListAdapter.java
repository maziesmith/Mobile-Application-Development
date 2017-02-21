package com.example.inclass07_siddhant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sid on 03-10-2016.
 */

public class ListAdapter extends ArrayAdapter<Feed> implements GetImage.imageInterface {
    private ImageView thumbImageView;

    List<Feed> mData;
    Context mContext;
    int mResource;
    Feed ni;
    public ListAdapter(Context context, int resource, List<Feed> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
           // holder = new ViewHolder();
           // holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            //convertView.setTag(holder);
        }
           // holder = (ViewHolder) convertView.getTag();

        Feed feed = mData.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textViewTitle);
        titleTextView.setText(feed.getTitle());//<----------can be feed.title--------------------
        ImageView thumbImageView = (ImageView) convertView.findViewById(R.id.imageViewThumb);
        Picasso.with(mContext).load(feed.getThumbnail()).into(thumbImageView);
       // holder.imageView.setImageResource(feed.getThumbnail());
      //  Log.d("demo","thumb"+feed.getThumbnail());
        new GetImage(this).execute(feed.getThumbnail());
        //new GetImage(this).execute(ni.getThumbnail());//<--------can be thumbnail-----------
        Log.d("demo1","flag value"+ feed.isA());
        if(feed.isA()){
            Log.d("demo1","enter green");
            convertView.setBackgroundColor(Color.GREEN);
        }else{
            Log.d("demo1","enter white");

            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }
/*
    @Override
    public void setImage(Bitmap bitmap, String viewId) {

    }
*/
    @Override
    public void setImage(Bitmap bitmap) {
//        this.thumbImageView.setImageBitmap(bitmap);
    }
}
