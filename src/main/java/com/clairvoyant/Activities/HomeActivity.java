package com.clairvoyant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.clairvoyant.Fragments.FragmentDrawer;
import com.clairvoyant.Utils.ActionBarCommon;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.Utils.ResponseCode;
import com.clairvoyant.WebServices.RestWebService;
import com.clairvoyant.entities.Menu.MenuList;
import com.clairvoyant.restra.R;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity  implements
        FragmentDrawer.FragmentDrawerListener{

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_color_dark));
        }
        setContentView(R.layout.home_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBarCommon actionBarCommon = new ActionBarCommon(getSupportActionBar(), this)
                .setCustomActionBar();
        actionBarCommon.getBackButtonLayout().setVisibility(View.GONE);
        actionBarCommon.setTitleForActionBar("");

        drawerFragment = (FragmentDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_drawer);

        drawerFragment.setUp(R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar,
                getSupportActionBar());
        drawerFragment.setDrawerListener(this);

        drawerFragment.searchTextView.performClick();
        // display the first navigation drawer view on app launch
//		displayView(0);
    }

    public ActionBarDrawerToggle getDrawerToggle(){
        return drawerFragment.getDrawerToggle();
    }

    public DrawerLayout getDrawerLayout(){
        return (DrawerLayout) findViewById(R.id.drawer_layout);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Intent intent;
        switch (position) {

            case 0:
//                intent = new Intent(DashboardActivity.this,
//                        AppLoginActivity.class);
//                intent.putExtra(Constants.INTENT_PARAM_IS_SIGNUP, true);
//                startActivity(intent);
                break;
            case 1:
//                intent = new Intent(DashboardActivity.this,
//                        AppLoginActivity.class);
//                intent.putExtra(Constants.INTENT_PARAM_IS_SIGNUP, false);
//                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        getDrawerToggle().setDrawerIndicatorEnabled(true);
        super.onBackPressed();
    }
}


