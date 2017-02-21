package com.example.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sid on 01-10-2016.
 */
public class ColorAdapter extends ArrayAdapter<Color> {
    List<Color> mData;
    Context mContext;
    int mResource;


    public ColorAdapter(Context context, int resource, List<Color> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        Color color = mData.get(position);
        TextView colorNameTextView = (TextView) convertView.findViewById(R.id.textViewColorName);
        colorNameTextView.setText(color.colorName);
        TextView colorHexTextView = (TextView) convertView.findViewById(R.id.textViewColorHex);
        colorHexTextView.setText(color.colorHex);

        colorHexTextView.setTextColor(android.graphics.Color.parseColor(color.colorHex));

        if(position%2==0){
            convertView.setBackgroundColor(android.graphics.Color.WHITE);
        }else{
            convertView.setBackgroundColor(android.graphics.Color.RED);
        }

        return convertView;
    }

}
