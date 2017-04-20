package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView email_profile, phone_profile;
    private Button logout, created_events, joined_events;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton fab_edit_profile;
    public TextView currentUser;

    //ListView profile_events;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        final String user_uid = getArguments().getString("userUID");

        fab_edit_profile = (FloatingActionButton) profileFragmentView.findViewById(R.id.fab_edit_profile);
        //profile_events = (ListView) profileFragmentView.findViewById(R.id.profile_event_list_view);
        created_events = (Button) profileFragmentView.findViewById(R.id.profile_created_events_button);
        joined_events = (Button) profileFragmentView.findViewById(R.id.profile_joined_events_button);
        email_profile = (TextView) profileFragmentView.findViewById(R.id.email_profile);
        phone_profile = (TextView) profileFragmentView.findViewById(R.id.phone_profile);
        logout = (Button) profileFragmentView.findViewById(R.id.logout);

        currentUser = (TextView) profileFragmentView.findViewById(R.id.currentUserUID_profile);
        currentUser.setText(user_uid);


        firebaseAuth = FirebaseAuth.getInstance();


//--------------------------------------------------------------------------------------------------
//--------------------------- IF USER ISN'T LOGGED IN, OPEN LOGIN PAGE! ----------------------------
//--------------------------------------------------------------------------------------------------

        if (firebaseAuth.getCurrentUser() == null) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------


        if (firebaseAuth.getCurrentUser() != null)
            email_profile.setText("Email: " + firebaseAuth.getCurrentUser().getEmail());


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User/" + user_uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                phone_profile.setText(user.getPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fab_edit_profile.setOnClickListener(this);

        logout.setOnClickListener(this);

        created_events.setOnClickListener(this);

        joined_events.setOnClickListener(this);


        return profileFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v == logout) {
            firebaseAuth.signOut();
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }

        if (v == fab_edit_profile) {
            EditProfile editProfile = new EditProfile();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, editProfile);

            fragmentTransaction.commit();
        }

        if (v == created_events) {
            Fragment fr = new JoinedEvents();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            String user_uid = currentUser.getText().toString();
            Bundle args = new Bundle();
            args.putString("userUID", user_uid);
            fr.setArguments(args);
            ft.replace(R.id.main_container, fr);
            ft.commit();


        }

        if (v == joined_events) {
            JoinedEventAsli joinedEventAsli = new JoinedEventAsli();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, joinedEventAsli);
            fragmentTransaction.commit();
        }
    }


}

