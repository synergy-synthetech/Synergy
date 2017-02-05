package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
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
public class EventProfileFragment extends Fragment {


    public EventProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View EventProfileFragmentView = inflater.inflate(R.layout.fragment_event_profile, container, false);
        String event_uid = getArguments().getString("EventUID");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event/" + event_uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CreateEvent eventData = dataSnapshot.getValue(CreateEvent.class);


                TextView event_name_listView = (TextView) EventProfileFragmentView.findViewById(R.id.event_name_listView);
                TextView event_organisation_listView = (TextView) EventProfileFragmentView.findViewById(R.id.event_organisation_listView);
                TextView event_creator_listView = (TextView) EventProfileFragmentView.findViewById(R.id.event_createdby_listView);
                TextView event_uid_listView = (TextView) EventProfileFragmentView.findViewById(R.id.event_uid_listview);

                FloatingActionButton edit_event_profile_button = (FloatingActionButton) EventProfileFragmentView.findViewById(R.id.fab_event_profile);


                event_name_listView.setText(eventData.getName());
                event_organisation_listView.setText(eventData.getOrganisation());
                event_creator_listView.setText(eventData.getCreatorEmail());
                event_uid_listView.setText(eventData.getUID());

                FirebaseAuth firebaseAuth;
                firebaseAuth = FirebaseAuth.getInstance();
                //@SuppressWarnings("ConstantConditions") String user = firebaseAuth.getCurrentUser().toString();
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String user = firebaseUser.getEmail().toString();

                    if (user.equals(eventData.getCreatorEmail())){
                        edit_event_profile_button.setVisibility(View.VISIBLE);
                        //Toast.makeText(EventProfileFragmentView, "Welcome Admin!", Toast.LENGTH_LONG).show();
                        Snackbar.make(EventProfileFragmentView, "Welcome Admin!",Snackbar.LENGTH_LONG).show();
                    }

                    else
                        Snackbar.make(EventProfileFragmentView, "Hello, "+firebaseUser.getEmail()+"!",Snackbar.LENGTH_LONG).show();
                }
                else
                    Snackbar.make(EventProfileFragmentView, "Hi, guest!",Snackbar.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Toast.makeText(this.getContext(), "UID of Selected Event: " + event_uid, Toast.LENGTH_SHORT).show();
        //Snackbar.make(EventProfileFragmentView,"UID: "+event_uid,Snackbar.LENGTH_LONG).show();
        return EventProfileFragmentView;
    }

}
