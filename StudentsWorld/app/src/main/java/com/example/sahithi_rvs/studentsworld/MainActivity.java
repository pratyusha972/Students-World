/*package com.example.sahithi_rvs.studentsworld;

import android.os.Bundle;
import android.app.Fragment;

import android.app.ListFragment;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


//import com.example.sahithi_rvs.studentsworld.R;
import com.example.sahithi_rvs.studentsworld.activity.CreatePostFragment;
import com.example.sahithi_rvs.studentsworld.activity.FragmentDrawer;
import com.example.sahithi_rvs.studentsworld.activity.HomeFragment;
import com.example.sahithi_rvs.studentsworld.activity.ProfileFragment;
import com.example.sahithi_rvs.studentsworld.activity.TabFragment;
import com.example.sahithi_rvs.studentsworld.activity.TestCategoryFragment;

public class MainActivity extends AppCompatActivity  {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener((FragmentDrawer.FragmentDrawerListener) this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        android.support.v4.app.Fragment fragment=null;
        ListFragment listFragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                listFragment = new HomeFragment();
                Log.d("awrwf", "athome");
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new ProfileFragment();
                title = getString(R.string.title_profile);
                Log.d("awrwf", "athome21");
                break;

            case 2:
                title="TestCategory";
                getSupportActionBar().setTitle(title);
                //if (listFragment != null) {
                {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    android.support.v4.app.Fragment newFragment = new TestCategoryFragment();
                    transaction.add(android.R.id.content, newFragment,"sffd");
                    transaction.addToBackStack(null);
                    transaction.commit();
                } */

              /*  TestCategoryFragment tstfragment= new TestCategoryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(android.R.id.content,tstfragment,"tstfragment");

                title = getString(R.string.title_messages);
                break;*/
           /*     break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }




    }
}*/