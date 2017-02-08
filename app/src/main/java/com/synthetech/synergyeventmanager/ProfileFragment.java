package com.synthetech.synergyeventmanager;


import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView email_profile;
    private Button logout;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton fab_edit_profile;
    ListView profile_events;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        email_profile = (TextView) profileFragmentView.findViewById(R.id.email_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        profile_events = (ListView) profileFragmentView.findViewById(R.id.profile_event_list_view);

        //inflating List View
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event");

        final FirebaseListAdapter<CreateEvent> firebaseListAdapter = new FirebaseListAdapter<CreateEvent>(
                this.getActivity(),
                CreateEvent.class,
                R.layout.event_list_view_item,
                databaseReference) {
            @Override
            protected void populateView(View v, CreateEvent eventData, int position) {

                TextView eventName_listView = (TextView) v.findViewById(R.id.event_name_listView);
                TextView eventOrganisation_listView = (TextView) v.findViewById(R.id.event_organisation_listView);
                TextView eventCreator_listView = (TextView) v.findViewById(R.id.event_createdby_listView);
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.event_list_view);

                if (eventData.getCreator_uid().equals(firebaseAuth.getCurrentUser().getUid())) {
                    eventName_listView.setText(eventData.getName());
                    eventOrganisation_listView.setText(eventData.getOrganisation());
                    eventCreator_listView.setText(eventData.getCreatorEmail());

                }
                else
                    layout.setVisibility(View.GONE);


            }
        };
        profile_events.setAdapter(firebaseListAdapter);


        profile_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(view.getContext(), "Position: " + position + " " + databaseReference.getDatabase().getReference(), Toast.LENGTH_SHORT).show();

            }
        });

        //End of Inflation of listview

        fab_edit_profile = (FloatingActionButton) profileFragmentView.findViewById(R.id.fab_edit_profile);
        fab_edit_profile.setOnClickListener(this);

        logout = (Button) profileFragmentView.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() == null) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }

        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            email_profile.setText("Email: " + user.getEmail());
        }

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
    }
}
