package com.example.sahithi_rvs.studentsworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sahithi_rvs.studentsworld.activity.SubjectFragment;
import com.example.sahithi_rvs.studentsworld.activity.TabFragment;

public class MaindupActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.edit_profile) {
                    navigatetoEditProfileActivity();
                }

                if (menuItem.getItemId() == R.id.profile) {
                    navigatetoProfileActivity();
                }

                if (menuItem.getItemId() == R.id.test_history) {
                    navigatetoHistoryActivity();
                }

                if(menuItem.getItemId() == R.id.contact){
                    navigatetocontactusactivity();
                }

                if (menuItem.getItemId() == R.id.logout) {
                    SharedPreferences logged_in;
                    SharedPreferences.Editor edit_login;
                    logged_in= getSharedPreferences("app", 0);
                    edit_login = logged_in.edit();
                    edit_login.putString("login","false");
                    edit_login.putString("uid","nil");
                    edit_login.putString("cookies","nil");
                    edit_login.commit();
                    navigatetoLoginActivity();
                }

                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                }


                return false;

            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    public void navigatetoEditProfileActivity(){

        Intent loginIntent = new Intent(getApplicationContext(),EditProfile.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
    public void navigatetoLoginActivity(){

        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void navigatetoProfileActivity(){

        Intent loginIntent = new Intent(getApplicationContext(),MyProfile.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void navigatetoHistoryActivity(){

        Intent hisIntent = new Intent(getApplicationContext(),TestHistory.class);
        // Clears History of Activity
        hisIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hisIntent);
    }

    public void navigatetocontactusactivity(){

        Intent hisIntent = new Intent(getApplicationContext(),Contactus.class);
        // Clears History of Activity
        hisIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hisIntent);
    }


}