package com.synthetech.synergyeventmanager;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    //Drawer - Declaration
    DrawerLayout drawerLayout;

    //Toolbar declaration
    Toolbar toolbar;

    //Drawer Toggle declaration
    ActionBarDrawerToggle actionBarDrawerToggle;

    //Fragment Transaction declaration
    FragmentTransaction fragmentTransaction;

    //NavigationView declaration
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Set content view to activity_main.xml activity layout

        //Initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //No idea why? Don't remove. This shizz is important
        setSupportActionBar(toolbar);

        //Initialise drawerlayout var
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Set actions
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        //Drawer listener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //Begin transaction to display the search (home) fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Set the view for search fragment
        fragmentTransaction.add(R.id.main_container, new SearchFragment());

        //Commit the fragment transaction
        fragmentTransaction.commit();

        //You can set the title if you want to...
        getSupportActionBar().setTitle("Synergy Event Manager");

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.search_nav:

                        //Begin transaction to display the search (home) fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for search fragment
                        fragmentTransaction.replace(R.id.main_container, new SearchFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Synergy Event Manager");

                        //Close Drawer
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.create_nav:

                        //Begin transaction to display the Create fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for Create fragment
                        fragmentTransaction.replace(R.id.main_container, new CreateFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Create Event");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;


                   /* case R.id.hot_nav:

                        //Begin transaction to display the search (home) fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for search fragment
                        fragmentTransaction.replace(R.id.main_container, new HotFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Synergy Event Manager");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;*/


                    case R.id.profile_nav:

                        //Begin transaction to display the Profile fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for Profile fragment
                        fragmentTransaction.replace(R.id.main_container, new ProfileFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Your Profile");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.privacy_nav:

                        //Begin transaction to display the fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for privacy fragment
                        fragmentTransaction.replace(R.id.main_container, new PrivacyFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Privacy Policy");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;


                    /*case R.id.about_nav:

                        //Begin transaction to display the search (home) fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for search fragment
                        fragmentTransaction.replace(R.id.main_container, new AboutFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Synergy Event Manager");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;*/


                    /*case R.id.help_nav:

                        //Begin transaction to display the search (home) fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for search fragment
                        fragmentTransaction.replace(R.id.main_container, new HelpFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Synergy Event Manager");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;*/


                    /*case R.id.share_nav:

                        //Begin transaction to display the search (home) fragment
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        //Set the view for search fragment
                        fragmentTransaction.replace(R.id.main_container, new ShareFragment());

                        //Commit the fragment transaction
                        fragmentTransaction.commit();

                        //You can set the title if you want to...
                        getSupportActionBar().setTitle("Synergy Event Manager");

                        //Close Drawer
                        drawerLayout.closeDrawers();
                        break;*/


                }//End of Switch


                return false;
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
