package com.petersoboyejo.premefeed.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.ZoomOutTransformer;
import com.petersoboyejo.premefeed.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class ItemFragment extends Fragment implements BaseSliderView.OnSliderClickListener{

    Toolbar toolbar;
    private SliderLayout mSlider;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        Bundle extras = getArguments();

        String image = extras.getString("image");
        String images = extras.getString("images");;
        String id = extras.getString("id");
        String title = extras.getString("title");
        String style = extras.getString("style");
        final String link = extras.getString("link");
        String description = extras.getString("description");
        int price = extras.getInt("price");
        String availability = extras.getString("availability");


        JSONArray imagesArray = null;
        try {
            imagesArray = new JSONArray(images);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        mSlider.setDuration(5000);
        for(int i=0;i<images.length();i++){

            TextSliderView textSliderView = new TextSliderView(getContext());
            try {
                textSliderView
                        .image(imagesArray.getString(i))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", imagesArray.getString(i));

                mSlider.addSlider(textSliderView);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        TextView title_TV = (TextView) rootView.findViewById(R.id.textView5);
        TextView description_TV = (TextView) rootView.findViewById(R.id.textView6);
        TextView price_TV = (TextView) rootView.findViewById(R.id.textView7);

        Button goToBrowser = (Button) rootView.findViewById(R.id.button);

        goToBrowser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(getContext())
                        .setTitle("Are you sure you want to open up your browser?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(link));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

            }

        });

        Button onClickPurchase = (Button) rootView.findViewById(R.id.button2);
        onClickPurchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle("¯\\_(ツ)_/¯")
                        .setMessage("This feature is quite not ready...")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
            }

        });


        title_TV.setText(title);
        description_TV.setText(description);
        price_TV.setText("$" + price);

        return rootView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}