package com.clairvoyant.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.clairvoyant.Adapters.ImagePagerAdapter;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Image;
import com.clairvoyant.restra.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageActivity extends AppCompatActivity {

    ViewPager viewPager;

    ImagePagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager_images);

        String imageId = init();
        getImages(imageId);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private String init(){
        String id = null;
        try {
            id = (String) getIntent().getExtras().get(Constants.PARAMETER_IMAGE_ID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    private void getImages(String imageId){
        if(imageId == null){
            return;
        }
        new RestWebService(ImageActivity.this){
            @Override
            public void onSuccess(String data) {
                Image.ImageList array = new Gson().fromJson(data,Image.ImageList.class);
                Image[] arrays = array.getImage();
                ArrayList<Image> list = new ArrayList<Image>(Arrays.asList(arrays));
                setAdapter(list);
            }
        }.serviceCall(Constants.API_GET_RESTAURANT_IMAGE, imageId, true);
    }

    private void setAdapter(ArrayList<Image> list) {
        viewPagerAdapter = new ImagePagerAdapter(ImageActivity.this, list);
        viewPager.setAdapter(viewPagerAdapter);

    }
}
