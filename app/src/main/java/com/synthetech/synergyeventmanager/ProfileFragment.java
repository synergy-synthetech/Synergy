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

        fab_edit_profile = (FloatingActionButton) profileFragmentView.findViewById(R.id.fab_edit_profile);
        profile_events = (ListView) profileFragmentView.findViewById(R.id.profile_event_list_view);
        email_profile = (TextView) profileFragmentView.findViewById(R.id.email_profile);
        phone_profile = (TextView) profileFragmentView.findViewById(R.id.phone_profile);
        logout = (Button) profileFragmentView.findViewById(R.id.logout);

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





        //inflating List View
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event");

        if (firebaseAuth.getCurrentUser()!=null) {
            final FirebaseListAdapter<CreateEvent> firebaseListAdapter = new FirebaseListAdapter<CreateEvent>(
                    this.getActivity(),
                    CreateEvent.class,
                    R.layout.event_list_view_item,
                    databaseReference.orderByChild("creator_uid").equalTo(firebaseAuth.getCurrentUser().getUid())) {
                @Override
                protected void populateView(View v, CreateEvent eventData, int position) {

                    TextView eventName_listView = (TextView) v.findViewById(R.id.event_name_listView);
                    TextView eventOrganisation_listView = (TextView) v.findViewById(R.id.event_organisation_listView);
                    TextView eventCreator_listView = (TextView) v.findViewById(R.id.event_createdby_listView);




                    eventName_listView.setText(eventData.getName());
                    eventOrganisation_listView.setText(eventData.getOrganisation());
                    eventCreator_listView.setText(eventData.getCreatorEmail());


                }
            };
            profile_events.setAdapter(firebaseListAdapter);


            profile_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    CreateEvent eventData = (CreateEvent) parent.getItemAtPosition(position);

                    String event_uid = eventData.getUID();

                    Fragment fr = new EventProfileFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("EventUID", event_uid);
                    fr.setArguments(args);
                    ft.replace(R.id.main_container, fr);
                    ft.commit();
                    //Toast.makeText(view.getContext(), "Position: " + position + " " + databaseReference.getDatabase().getReference(), Toast.LENGTH_SHORT).show();

                }
            });

            //End of Inflation of listview


            email_profile.setText("Email: " + firebaseAuth.getCurrentUser().getEmail());


            firebaseAuth = FirebaseAuth.getInstance();

            FirebaseUser user = firebaseAuth.getCurrentUser();
            DatabaseReference data = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/User/"+user.getUid());


            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                    String phone = userInformation.getPhone();

                    if (phone != null)
                        phone_profile.setText("Phone: "+phone);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }//End of if - check if logged in.


        fab_edit_profile.setOnClickListener(this);

        logout.setOnClickListener(this);



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

