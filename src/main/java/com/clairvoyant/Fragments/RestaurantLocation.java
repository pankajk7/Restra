package com.clairvoyant.Fragments;

import android.app.Activity;
import android.view.View;

import com.clairvoyant.Activities.RestaurantInfoActivity;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.restra.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantLocation implements OnMapReadyCallback {

    Restaurant objRestaurant;
    Activity activity;

    public View getFragmentView(View rootView, Restaurant objRestaurant, Activity activity) {
        this.objRestaurant = objRestaurant;
        this.activity = activity;

        SupportMapFragment mapFragment = (SupportMapFragment) ((RestaurantInfoActivity) activity)
                .getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            addMarkerAnimateCamera(googleMap);
        }
    }

    private void addMarkerAnimateCamera(GoogleMap googleMap) {
        try {
            double lat = Double.parseDouble(objRestaurant.getLatitude());
            double lng = Double.parseDouble(objRestaurant.getLongitude());
            LatLng latlng = new LatLng(lat, lng);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latlng).zoom(9).build();

            MarkerOptions markerOption = new MarkerOptions().position(latlng)
                    .alpha(0.7f);
            markerOption.title(objRestaurant.getName());
            googleMap.addMarker(markerOption);
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
