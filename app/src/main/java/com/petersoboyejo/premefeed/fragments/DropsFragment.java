package com.petersoboyejo.premefeed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import com.petersoboyejo.premefeed.PremeApp;
import com.petersoboyejo.premefeed.R;
import com.petersoboyejo.premefeed.adapters.DropsAdapter;
import com.petersoboyejo.premefeed.records.DropsRecord;


public class DropsFragment extends Fragment {

    private DropsAdapter mAdapter;
    private ProgressBar progressBar;

    public DropsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle("Drops");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drops, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DropsAdapter(getActivity());
        ListView listView = (ListView) getView().findViewById(R.id.lv_drops);
        listView.setAdapter(mAdapter);

        progressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFcc0000,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);

        fetch();
    }

    private void fetch() {
        JsonObjectRequest request = new JsonObjectRequest(
                ("http://premefeed.herokuapp.com/api/v1/items/all"),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            List<DropsRecord> dropsRecord = parse(jsonObject);
                            progressBar.setVisibility(View.INVISIBLE);
                            mAdapter.swapRecords(dropsRecord);
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        PremeApp.getInstance().getRequestQueue().add(request);
    }


    // thought to self - intent must have a view pager containing item images

    private List<DropsRecord> parse(JSONObject json) throws JSONException {
        ArrayList<DropsRecord> records = new ArrayList<DropsRecord>();

        JSONArray jsonData = json.getJSONArray("items");

        for (int i = 0; i < jsonData.length(); i++) {
            JSONObject jsonDrop = jsonData.getJSONObject(i);

            // imgs
            String image = jsonDrop.getString("image");
            JSONArray imgs = jsonDrop.getJSONArray("images");
            String images = imgs.toString();

            String id = jsonDrop.getString("id");
            String title = jsonDrop.getString("title");
            String style = jsonDrop.getString("style");
            String link = jsonDrop.getString("link");
            String description = jsonDrop.getString("description");
            int price = jsonDrop.getInt("price");
            String availability = jsonDrop.getString("availability");

            DropsRecord record = new DropsRecord(image, images, id, title, style, link, description, price, availability);
            records.add(record);
        }

        return records;
    }
}