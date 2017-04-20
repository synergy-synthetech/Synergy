package com.synthetech.synergyeventmanager;

import android.app.NotificationManager;
import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.synthetech.synergyeventmanager.R.id.drawer_header_user_email;

public class MainActivity extends AppCompatActivity {
    //Drawer - Declaration
    DrawerLayout drawerLayout;
    TextView email_header;
    ImageView profile_image_header;


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




        //---------------------------------------------------------------------------------------------------------

        int notificationID = 0;
        Snackbar.make(this.findViewById(R.id.main_container), "I have reached here...",Snackbar.LENGTH_LONG).show();
        Log.d("start", "started");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.drawable.synergy_logo_jpeg);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        NotificationManager mn = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mn.notify(notificationID, mBuilder.build());
        //---------------------------------------------------------------------------------------------------------










        //Begin transaction to display the search (home) fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //Set the view for search fragment
        fragmentTransaction.add(R.id.main_container, new SearchFragment());



        //Commit the fragment transaction
        fragmentTransaction.commit();

        //You can set the title if you want to...
        getSupportActionBar().setTitle("Synergy Event Manager");


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View hView = navigationView.getHeaderView(0);

        email_header = (TextView) hView.findViewById(drawer_header_user_email);
        profile_image_header = (ImageView) hView.findViewById(R.id.drawer_header_user_image);

        if (firebaseAuth.getCurrentUser() != null) {
            email_header.setVisibility(View.VISIBLE);
            profile_image_header.setVisibility(View.VISIBLE);
            email_header.setText(firebaseAuth.getCurrentUser().getEmail());
        }

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

                       //fragmentTransaction.addToBackStack("screen 1");
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

                        //fragmentTransaction.addToBackStack(null);


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

                        //fragmentTransaction.addToBackStack(null);

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
