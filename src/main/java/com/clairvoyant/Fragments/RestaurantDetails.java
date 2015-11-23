package com.clairvoyant.Fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.gson.JsonObject;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

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
                try {
                    JSONObject jsonObject = new JSONObject(data);
//                    Tag.TagList array = new Gson().fromJson(data, Tag.TagList.class);
                    JSONObject tagObject = jsonObject.getJSONObject("tag");
                    Tag array = new Gson().fromJson(tagObject.toString(), Tag.class);
//                    Tag[] arrays = array.getTag();
//                    ArrayList<Tag> list = new ArrayList<Tag>(Arrays.asList(array));
                    addTags(array);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }.serviceCall(Constants.API_GET_TAG, tagId, false);
    }

    private void addTags(Tag list){
        if(list != null){
            /*LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);*/

//            TextView textView[] = new TextView[list.getTags().length];
//            ImageView imageView[] = new ImageView[list.getTags().length];
            for (int i = 0; i < list.getTags().length; i++) {
                LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.nav_drawer_row, null, false);
                Tag.Tags obj = list.getTags()[i];
                TextView textView = (TextView)view.findViewById(R.id.textview_drawer_text);
                ImageView imageView =  (ImageView)view.findViewById(R.id.imageview_drawer);
//                textView[i].setTextColor(ContextCompat.getColor(activity, R.color.black));
//                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(obj.getValue() == 1) {
                    textView.setText(obj.getName() + " ");
                    imageView.setImageResource(R.drawable.ic_check_green);
                }else{
                    textView.setText(obj.getName() + " ");
                    imageView.setImageResource(R.drawable.ic_cross_red);
                }
                tagsLinearLayout.addView(view);
            }
        }
    }
}
