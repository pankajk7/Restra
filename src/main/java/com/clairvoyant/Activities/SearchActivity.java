package com.clairvoyant.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.clairvoyant.Adapters.SearchResultBaseAdapter;
import com.clairvoyant.Utils.ActionBarCommon;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.Utils.ResponseCode;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.restra.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    Toolbar mToolbar;
    AutoCompleteTextView searchTextView;
    ListView listView;

    ArrayList<Restaurant> restaurantArrayList;

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
        actionBarCommon.setTitleForActionBar("");

        init();
        findViews();
        listeners();
    }

    private void init() {
        restaurantArrayList = new ArrayList<Restaurant>();
    }

    private void findViews() {
        searchTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_searchLayout);
        listView = (ListView)findViewById(R.id.listview_searchLayout);
    }

    private void listeners() {
         String[] areas = new String[] {
                "Pichola", "Chandpole", "Jagdish Chowk"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, areas);
        searchTextView.setAdapter(adapter);

        searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callAPI("1");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, RestaurantInfoActivity.class);
                intent.putExtra("obj", restaurantArrayList.get(position));
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

    private void callAPI(String areaId) {
        new RestWebService(SearchActivity.this) {
            @Override
            public void onSuccess(String data) {
                Restaurant.RestaurantList array = new Gson().fromJson(data, Restaurant.RestaurantList.class);
                Restaurant[] restaurants = array.getRestaurant();
//                Tag.TagList array = new Gson().fromJson(data,Tag.TagList.class);
//                com.clairvoyant.entities.Menu.MenuList array = new Gson().fromJson(data, com.clairvoyant.entities.Menu.MenuList.class);
                if (array != null) {
                    restaurantArrayList = new ArrayList<Restaurant>(Arrays.asList(restaurants));
                    restaurantArrayList.add(restaurantArrayList.get(0));
                    restaurantArrayList.add(restaurantArrayList.get(0));
                    restaurantArrayList.add(restaurantArrayList.get(0));
                    setAdapter(restaurantArrayList);
//                    Toast.makeText(HomeActivity.this, array.getMenu()[0].getPic_id(), Toast.LENGTH_LONG).show();
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
                    }
                }
            }
        }.serviceCall(Constants.API_GET_SINGLE_RESTAURANT, areaId, true);
    }

    private void setAdapter(ArrayList<Restaurant> restaurantArrayList){
        SearchResultBaseAdapter adapter = new SearchResultBaseAdapter(SearchActivity.this, restaurantArrayList);
        listView.setAdapter(adapter);
    }
}
