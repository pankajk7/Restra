package com.clairvoyant.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.restra.R;

/**
 * Created by Pankaj on 17/11/15.
 */
public class RestaurantDetails {

    ImageView imageView;


    Restaurant objRestaurant;
    Activity activity;

    public View getFragmentView(View rootView, Restaurant objRestaurant, Activity activity) {
        this.objRestaurant = objRestaurant;
        this.activity = activity;
        init();
        findView(rootView);
        return rootView;
    }


    private void init() {
        if(objRestaurant != null){
            Toast.makeText(activity, "Object", Toast.LENGTH_LONG).show();
        }
    }

    private void findView(View rootView) {
        imageView = (NetworkImageView) rootView
                .findViewById(R.id.imageView_parcelInfo);

    }
}
