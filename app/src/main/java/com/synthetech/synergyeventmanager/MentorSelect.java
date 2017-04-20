package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class MentorSelect extends Fragment {

    ListView events;


    public MentorSelect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View MentorSelectView = inflater.inflate(R.layout.fragment_mentor_select, container, false);

        final String event_uid = getArguments().getString("EventUID");

        events = (ListView) MentorSelectView.findViewById(R.id.event_user_listView);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Join/" + event_uid);

        FirebaseListAdapter<JoinEvent> firebaseListAdapter = new FirebaseListAdapter<JoinEvent>(
                getActivity(),
                JoinEvent.class,
                R.layout.user_list_view_item,
                databaseReference.orderByChild("memberEmail")) {
            @Override
            protected void populateView(View v, JoinEvent model, int position) {
                TextView Name_listView = (TextView) v.findViewById(R.id.user_name_listView);

                Name_listView.setText(model.getMemberEmail());

            }
        };

        events.setAdapter(firebaseListAdapter);


        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserInformation eventData = (UserInformation) parent.getItemAtPosition(position);

                DatabaseReference mentorRef = FirebaseDatabase.getInstance().getReference().child("Event/" + event_uid + "/Mentor/");
                mentorRef.setValue(eventData);
                //Snackbar.make(view, "Successfully selected "+eventData.getEmail()+" as the mentor.", Snackbar.LENGTH_LONG).show();


                Fragment fr = new EventProfileFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();

            }
        });


        return MentorSelectView;
    }

}
