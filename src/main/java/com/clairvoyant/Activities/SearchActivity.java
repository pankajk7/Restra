package com.clairvoyant.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.clairvoyant.Adapters.SearchResultBaseAdapter;
import com.clairvoyant.Utils.ActionBarCommon;
import com.clairvoyant.Utils.ConnectionDetector;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.Utils.ReadWriteJsonFileUtils;
import com.clairvoyant.Utils.ResponseCode;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Area;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.restra.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    Toolbar mToolbar;
    AutoCompleteTextView searchTextView;
//    ListView listView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Restaurant> restaurantArrayList;
    ArrayList<Area> areaArrayList;
    boolean isListAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_color_dark));
        }
        setContentView(R.layout.search_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarCommon actionBarCommon = new ActionBarCommon(getSupportActionBar(), SearchActivity.this)
                .setCustomActionBar();
        actionBarCommon.getBackButtonLayout().setVisibility(View.GONE);
        actionBarCommon.setTitleForActionBar("Search Restaurant");

        init();
        findViews();
        if(isListAll){
            searchTextView.setVisibility(View.GONE);
            getRestaurant("");
        }else{
            searchTextView.setVisibility(View.VISIBLE);
        }
        listeners();
        getArea();
    }

    private void init() {
        restaurantArrayList = new ArrayList<Restaurant>();
        areaArrayList = new ArrayList<Area>();
        //default false means not listing all restaurants
        isListAll = (boolean)getIntent().getBooleanExtra(Constants.PARAMETER_SEARCH_IS_LIST_ALL,false);
    }

    private void findViews() {
        searchTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_searchLayout);
//        listView = (ListView)findViewById(R.id.listview_searchLayout);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_searchLayout);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void listeners() {

        searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeypad();
                Area obj = (Area)parent.getAdapter().getItem(position);
                getRestaurant(obj.getId());
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(SearchActivity.this, RestaurantInfoActivity.class);
//                intent.putExtra("obj", restaurantArrayList.get(position));
//                startActivity(intent);
//            }
//        });


    }

    private void hideKeypad() {
        View view = getCurrentFocus();

        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view instanceof EditText) {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAdapterArea(){
        ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(SearchActivity.this,
                android.R.layout.simple_dropdown_item_1line, areaArrayList);
        searchTextView.setAdapter(adapter);
    }

    private void getArea(){
        boolean isShowLoading = true;
        // for checking data in file if exist
        String data = new ReadWriteJsonFileUtils(SearchActivity.this)
                .readJsonFileData(Constants.PARAMETER_AREA_FILE);
        // if there is data then assign to arraylist
        if (data != null) {
            areaArrayList.clear();
            areaArrayList.trimToSize();
            Area.AreaList array = new Gson().fromJson(data, Area.AreaList.class);
            areaArrayList = new ArrayList<Area>(Arrays.asList(array.getArea()));
            setAdapterArea();
            isShowLoading = false;
        }

        new RestWebService(SearchActivity.this) {
            @Override
            public void onSuccess(String data) {

                if (data != null) {
                    areaArrayList.clear();
                    areaArrayList.trimToSize();
                    Area.AreaList array = new Gson().fromJson(data, Area.AreaList.class);
                    new ReadWriteJsonFileUtils(SearchActivity.this)
                            .createJsonFileData(
                                    Constants.PARAMETER_AREA_FILE,
                                    data);
                    areaArrayList = new ArrayList<Area>(Arrays.asList(array.getArea()));
                    setAdapterArea();
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    if (error.networkResponse != null) {
                        for (ResponseCode code : ResponseCode.values()) {
                            if (code.getStatusCode() == error.networkResponse.statusCode) {
//                        Toast.makeText(HomeActivity.this, code.toString(), Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    } else if(error.getMessage().contains("java.net.UnknownHostException")){
                        Toast.makeText(SearchActivity.this, "Network Error Occur", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }.serviceCall(Constants.API_GET_AREA, isShowLoading);
    }

    private void getRestaurant(String areaId) {
        String resourceName;
        if(areaId.equalsIgnoreCase("")){
            resourceName = Constants.API_GET_RESTAURANT;
        }else{
            resourceName = Constants.API_GET_SINGLE_RESTAURANT;
        }
        new RestWebService(SearchActivity.this) {
            @Override
            public void onSuccess(String data) {
                Restaurant.RestaurantList array = new Gson().fromJson(data, Restaurant.RestaurantList.class);
//
                if (array != null) {
                    restaurantArrayList = new ArrayList<Restaurant>(Arrays.asList(array.getRestaurant()));
//                    restaurantArrayList.add(restaurantArrayList.get(0));
//                    restaurantArrayList.add(restaurantArrayList.get(0));
//                    restaurantArrayList.add(restaurantArrayList.get(0));
                    setAdapter(restaurantArrayList);
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    if (error.networkResponse != null) {
                        for (ResponseCode code : ResponseCode.values()) {
                            if (code.getStatusCode() == error.networkResponse.statusCode) {
//                        Toast.makeText(HomeActivity.this, code.toString(), Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    } else if(error.getMessage().contains("java.net.UnknownHostException")){
                        Toast.makeText(SearchActivity.this, "Network Error Occur", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }.serviceCall(resourceName, areaId, true);
    }

    private void setAdapter(ArrayList<Restaurant> restaurantArrayList){
        SearchResultBaseAdapter adapter = new SearchResultBaseAdapter(SearchActivity.this, restaurantArrayList);
//        listView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }
}
