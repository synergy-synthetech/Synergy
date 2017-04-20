package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallMeetingPageTwoFragment extends Fragment {

    EditText meetingTopicName, meetingDate;
    DatePicker datePicker;
    Button addMeeting;

    public CallMeetingPageTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View CallMeetingPageTwoFragmentView = inflater.inflate(R.layout.fragment_call_meeting_page_two, container, false);
        String passedVar = getArguments().getString("passedVar");
        String temp[] = passedVar.split("#");
        final String event_uid = temp[0];
        final String deptName = temp[1];



        meetingTopicName = (EditText) CallMeetingPageTwoFragmentView.findViewById(R.id.meetingTopicName);
        meetingDate = (EditText) CallMeetingPageTwoFragmentView.findViewById(R.id.meetingDate);
        datePicker = (DatePicker) CallMeetingPageTwoFragmentView.findViewById(R.id.datePicker);
        addMeeting = (Button) CallMeetingPageTwoFragmentView.findViewById(R.id.addMeetingButton);

        final String topic = meetingTopicName.getText().toString();
        final String date = "Sample Date";



        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                Meeting meeting = new Meeting(topic, deptName, date, firebaseAuth.getCurrentUser().getEmail(), event_uid);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Event/"+event_uid+"/Meeting/"+deptName);
                databaseReference.setValue(meeting);
                Toast.makeText(getContext(), "Meeting has been called "+ databaseReference.toString(), Toast.LENGTH_LONG).show();
                Fragment fr = new ProfileFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_container, fr);
                ft.commit();

            }
        });

        return CallMeetingPageTwoFragmentView;
    }

}
