package com.clairvoyant.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.clairvoyant.Activities.ImageActivity;
import com.clairvoyant.Adapters.ImagePagerAdapter;
import com.clairvoyant.Utils.BackgroundNetwork;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Image;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.restra.R;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RestaurantDetails {

    ImageView imageView;

    ViewPager viewPager;

    ImagePagerAdapter viewPagerAdapter;
    Restaurant objRestaurant;
    Activity activity;

    public View getFragmentView(View rootView, Restaurant objRestaurant, Activity activity) {
        this.objRestaurant = objRestaurant;
        this.activity = activity;
        init();
        findView(rootView);
//        getImages(objRestaurant.getPrimary_image(), imageView);
        listeners();
        getImages(objRestaurant.getId());
        return rootView;
    }


    private void init() {
        if(objRestaurant != null){
            Toast.makeText(activity, "Object", Toast.LENGTH_LONG).show();
        }
    }

    private void findView(View rootView) {
        imageView = (ImageView) rootView
                .findViewById(R.id.imageView_restraImage);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager_images);
    }

    public void listeners(){
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageActivity.class);
                intent.putExtra(Constants.PARAMETER_IMAGE_ID, objRestaurant.getPic_id());
                activity.startActivity(intent);
            }
        });*/
    }

    private void getImages(String imageId) {
        if (imageId == null) {
            return;
        }
        new RestWebService(activity) {
            @Override
            public void onSuccess(String data) {
                Image.ImageList array = new Gson().fromJson(data, Image.ImageList.class);
                Image[] arrays = array.getImage();
                ArrayList<Image> list = new ArrayList<Image>(Arrays.asList(arrays));
                setAdapter(list);
            }
        }.serviceCall(Constants.API_GET_RESTAURANT_IMAGE, imageId, true);
    }

    private void setAdapter(ArrayList<Image> list) {
        viewPagerAdapter = new ImagePagerAdapter(activity, list);
        viewPager.setAdapter(viewPagerAdapter);

    }

    /*private void getImages(final String id, final ImageView imageView) {


        new BackgroundNetwork(activity) {
            String url = "";

            protected Void doInBackground(Void... params) {
                Map<String, String> config = new HashMap<String, String>();
                config.put("cloud_name", Constants.CLOUDINARY_NAME);
                config.put("api_key", Constants.CLOUDINARY_API_KEY);
                config.put("api_secret", Constants.CLOUDINARY_API_SECRET);

                try {
                    Cloudinary cloudinary = new Cloudinary(config);
                    Transformation transformation = new Transformation();
                    transformation.width(300);
                    transformation.height(300);
                    transformation.crop("fit");
                    url = cloudinary.url().transformation(transformation).generate(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            };

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                if (!url.equalsIgnoreCase("") || url != null) {
                    Picasso.with(activity).load(url)
                            .placeholder(R.drawable.image_uploading)
                            .error(R.drawable.image_not_found).into(imageView);
                }

            };
        }.execute();
    }*/
}
