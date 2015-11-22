package com.clairvoyant.Fragments;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.clairvoyant.Adapters.ImagePagerAdapter;
import com.clairvoyant.Components.VerticalScrollView;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Image;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.entities.Tag;
import com.clairvoyant.restra.R;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;


public class RestaurantDetails {

    ImageView imageView;

    TextView addressTextView, areaTextView, costTextView, cuisineTextView;

    LinearLayout tagsLinearLayout;

    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    VerticalScrollView scrollView;

    ImagePagerAdapter viewPagerAdapter;
    Restaurant objRestaurant;
    Activity activity;

    public View getFragmentView(View rootView, Restaurant objRestaurant, Activity activity) {
        this.objRestaurant = objRestaurant;
        this.activity = activity;
        init();
        findView(rootView);
        listeners();
        setValues();
        getImages(objRestaurant.getId());
        getTags(objRestaurant.getId());
        return rootView;
    }


    private void init() {

    }

    private void findView(View rootView) {

        scrollView = (VerticalScrollView)rootView.findViewById(R.id.scrollView_restraDetails);

        imageView = (ImageView) rootView
                .findViewById(R.id.imageView_restraImage);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager_images);
        circlePageIndicator = (CirclePageIndicator)rootView.findViewById(R.id.circlePageIndicator);
        addressTextView = (TextView) rootView.findViewById(R.id.textview_restraDetails_address);
        areaTextView = (TextView) rootView.findViewById(R.id.textview_restraDetails_area);
        costTextView = (TextView) rootView.findViewById(R.id.textview_restraDetails_cost);
        cuisineTextView = (TextView) rootView.findViewById(R.id.textview_restraDetails_cuisines);
        tagsLinearLayout = (LinearLayout)rootView.findViewById(R.id.layout_restraDetails_tags);
    }

    public void listeners(){

    }

    private void setValues(){
        if(objRestaurant != null){
            addressTextView.setText(objRestaurant.getAddress());
            areaTextView.setText(objRestaurant.getArea());
            costTextView.setText(objRestaurant.getCost());
            cuisineTextView.setText(objRestaurant.getCuisines());
        }
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
        circlePageIndicator.setViewPager(viewPager);
    }

    private void getTags(String tagId) {
        if (tagId == null) {
            return;
        }
        new RestWebService(activity) {
            @Override
            public void onSuccess(String data) {
                /*Tag.TagList array = new Gson().fromJson(data, Tag.TagList.class);
                Tag[] arrays = array.getTag();
                ArrayList<Tag> list = new ArrayList<Tag>(Arrays.asList(arrays));
                addTags(list);*/
                Log.d("Tag====>",data);

            }
        }.serviceCall(Constants.API_GET_TAG, tagId, false);
    }

    private void addTags(ArrayList<Tag> list){
        if(list != null){
            /*LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);*/
            TextView textView[] = new TextView[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Tag obj = list.get(i);
                textView[i] = new TextView(activity);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView[i].setText("Get Bar" + obj.getBar() + "-" +list.size());
                textView[i].setLayoutParams(llp);
                tagsLinearLayout.addView(textView[i],i);
            }
        }
    }
}
