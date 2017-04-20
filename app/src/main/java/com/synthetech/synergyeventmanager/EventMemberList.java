package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventMemberList extends Fragment {


    public EventMemberList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View EventMemberListView = inflater.inflate(R.layout.fragment_event_member_list, container, false);

        final String event_uid = getArguments().getString("EventUID");

        ListView memberList = (ListView) EventMemberListView.findViewById(R.id.eventMemberListView);
        TextView message_eventMembers = (TextView) EventMemberListView.findViewById(R.id.message_eventMembers);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Event/" + event_uid + "/Member");
        if (databaseReference == null) {
            memberList.setVisibility(View.GONE);
            message_eventMembers.setText("No one has joined this event yet. Press to go to Event Profile.");

            message_eventMembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fr = new MoreEventOptions();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("EventUID", event_uid);
                    fr.setArguments(args);
                    ft.replace(R.id.main_container, fr);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

        }//End of IF


        FirebaseListAdapter<UserInformation> firebaseListAdapter = new FirebaseListAdapter<UserInformation>(
                getActivity(),
                UserInformation.class,
                R.layout.user_list_view_item,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, UserInformation model, int position) {
                TextView name = (TextView) v.findViewById(R.id.user_name_listView);
                TextView email = (TextView) v.findViewById(R.id.user_email_listView);
                TextView phone = (TextView) v.findViewById(R.id.user_phone_listView);
                TextView uid = (TextView) v.findViewById(R.id.user_uid_listview);

                name.setText(model.getName());
                email.setText(model.getEmail());
                phone.setText(model.getPhone());
                uid.setText("");
            }//End of populate View
        };

        memberList.setAdapter(firebaseListAdapter);

        memberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserInformation user = (UserInformation) parent.getItemAtPosition(position);

                Fragment fr = new TaskAdderFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                String passedVar = event_uid + "#" + user.getUid();
                args.putString("passedVar", passedVar);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
                //Toast.makeText(view.getContext(), "Event Name: "+eventData.getName(), Toast.LENGTH_SHORT).show();

            }

        });

        return EventMemberListView;
    };

}




