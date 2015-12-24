package com.petersoboyejo.premefeed.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.petersoboyejo.premefeed.PremeApp;
import com.petersoboyejo.premefeed.BitmapLruCache;
import com.petersoboyejo.premefeed.R;
import com.petersoboyejo.premefeed.records.DropsRecord;

import java.util.List;

public class DropsAdapter extends ArrayAdapter<DropsRecord> {
    private ImageLoader mImageLoader;


    public DropsAdapter(Context context) {
        super(context, R.layout.item_drops);
        mImageLoader = new ImageLoader(PremeApp.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_drops, parent, false);
        }

        // NOTE: You would normally use the ViewHolder pattern here
        // Pt 1

        ImageView browserImg = (ImageView)convertView.findViewById(R.id.browser_img);
        ImageView shareImg = (ImageView)convertView.findViewById(R.id.share_img);
        int color = Color.parseColor("#757575");
        browserImg.setColorFilter(color);
        shareImg.setColorFilter(color);

        // Images
        NetworkImageView itemImageView = (NetworkImageView) convertView.findViewById(R.id.imageView2);

        // Text
        TextView name = (TextView) convertView.findViewById(R.id.textView);
        TextView desc = (TextView) convertView.findViewById(R.id.textView2);
        TextView style = (TextView) convertView.findViewById(R.id.textView4);
        TextView availability = (TextView) convertView.findViewById(R.id.textView3);

        DropsRecord dropsRecord = getItem(position);

        // Pt 2

        // Images
        itemImageView.setImageUrl(dropsRecord.getImage(), mImageLoader);

        // TODO second image

        // Text
        name.setText(dropsRecord.getTitle());
        desc.setText(dropsRecord.getDescription());
        style.setText(dropsRecord.getStyle());
        availability.setText(dropsRecord.getAvailability());

        return convertView;
    }

    public void swapRecords(List<DropsRecord> objects) {
        clear();
        for(DropsRecord object : objects) {
            add(object);
        }

        notifyDataSetChanged();

    }
}