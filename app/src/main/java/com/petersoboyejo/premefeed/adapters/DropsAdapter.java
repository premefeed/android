package com.petersoboyejo.premefeed.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.petersoboyejo.premefeed.PremeApp;
import com.petersoboyejo.premefeed.BitmapLruCache;
import com.petersoboyejo.premefeed.R;
import com.petersoboyejo.premefeed.fragments.ItemFragment;
import com.petersoboyejo.premefeed.fragments.RecentsFragment;
import com.petersoboyejo.premefeed.records.DropsRecord;

import java.util.List;

public class DropsAdapter extends ArrayAdapter<DropsRecord> {

    private ImageLoader mImageLoader;
    private FragmentManager mFragmentManager;
    private Context context;

    public DropsAdapter(Context context) {
        super(context, R.layout.item_drops);
        this.context = context;
        mImageLoader = new ImageLoader(PremeApp.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_drops, parent, false);
        }


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

        final DropsRecord dropsRecord = getItem(position);

        // Pt 2

        // Images
        itemImageView.setImageUrl(dropsRecord.getImage(), mImageLoader);

        // TODO second image

        // Text
        name.setText(dropsRecord.getTitle());
        desc.setText(dropsRecord.getDescription());
        style.setText(dropsRecord.getStyle());

        availability.setText(dropsRecord.getAvailability());

        if (dropsRecord.getAvailability() == "Available") {
            availability.setTextColor(Color.parseColor("#4CAF50"));
        } else if (dropsRecord.getAvailability() == "Sold Out") {
            availability.setTextColor(Color.parseColor("#F44336"));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle data = new Bundle();

                data.putString("image", dropsRecord.getImage());
                data.putString("images", dropsRecord.getImages());
                data.putString("id", dropsRecord.getId());
                data.putString("title", dropsRecord.getTitle());
                data.putString("style", dropsRecord.getStyle());
                data.putString("link", dropsRecord.getLink());
                data.putString("description", dropsRecord.getDescription());
                data.putInt("price", dropsRecord.getPrice());
                data.putString("availability", dropsRecord.getAvailability());

                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                Fragment mFrag = new ItemFragment();
                mFrag.setArguments(data);
                ft.replace(R.id.flContent, mFrag);
                ft.commit();
                

            }
        });

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