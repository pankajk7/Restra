package com.clairvoyant.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.clairvoyant.Activities.SearchActivity;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.Utils.ResponseCode;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Menu;
import com.clairvoyant.restra.R;
import com.google.gson.Gson;


public class DasboardFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //VIEWS
    LinearLayout searchLinearLayout;
    LinearLayout listLinearLayout;

    // TODO: Rename and change types and number of parameters
    public static DasboardFragment newInstance(String param1, String param2) {
        DasboardFragment fragment = new DasboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DasboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init();
        findViews(view);
        listeners();
        return view;
    }

    private void init(){

    }

    private void findViews(View view){
        searchLinearLayout = (LinearLayout)view.findViewById(R.id.layout_fragmentDashboard_search);
        listLinearLayout = (LinearLayout)view.findViewById(R.id.layout_fragmentDashboard_listRestaurant);
    }

    private void listeners(){
        searchLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }


    private void callAPI(){
        new RestWebService(getActivity()) {
            @Override
            public void onSuccess(String data) {
//                Restaurant.RestaurantList array = new Gson().fromJson(data, Restaurant.RestaurantList.class);
//                Tag.TagList array = new Gson().fromJson(data,Tag.TagList.class);
                Menu.MenuList array = new Gson().fromJson(data,Menu.MenuList.class);
                if(array != null) {
//                    Toast.makeText(HomeActivity.this, array.getMenu()[0].getPic_id(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(VolleyError error) {
                if(error != null) {
                    if(error.networkResponse != null) {
                        for (ResponseCode code : ResponseCode.values()) {
                            if (code.getStatusCode() == error.networkResponse.statusCode) {
//                        Toast.makeText(HomeActivity.this, code.toString(), Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }
                }
            }
        }.serviceCall(Constants.API_GET_SINGLE_RESTAURANT,"1", true);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        public void onFragmentInteraction(Uri uri);
//    }

}
