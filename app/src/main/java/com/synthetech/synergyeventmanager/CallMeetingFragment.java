package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallMeetingFragment extends Fragment {


    public CallMeetingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View callMeetingFragmentView = inflater.inflate(R.layout.fragment_call_meeting, container, false);

        EditText topic_meeting = (EditText) callMeetingFragmentView.findViewById(R.id.topic_meeting);
        EditText venue_meeting = (EditText) callMeetingFragmentView.findViewById(R.id.venue_meeting);
        EditText time_meeting = (EditText) callMeetingFragmentView.findViewById(R.id.time_meeting);
        EditText date_meeting = (EditText) callMeetingFragmentView.findViewById(R.id.date_meeting);

        final RadioButton all_meeting = (RadioButton) callMeetingFragmentView.findViewById(R.id.all_meet);
        final RadioButton dept_meeting = (RadioButton) callMeetingFragmentView.findViewById(R.id.dept_meet);

        ListView all_members = (ListView) callMeetingFragmentView.findViewById(R.id.allMembersList);
        final ListView dept_members = (ListView) callMeetingFragmentView.findViewById(R.id.deptList);

        Button call_meeting = (Button) callMeetingFragmentView.findViewById(R.id.call_meeting);





//--------------------------------------------------------------------------------------------------
//--------------------------------------- RadioButtons ---------------------------------------------
//--------------------------------------------------------------------------------------------------
        all_meeting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (all_meeting.isChecked()) {
                    dept_members.setEnabled(false);
                } else
                    dept_meeting.setEnabled(true);
            }
        });
        dept_meeting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dept_meeting.isChecked()) {
                    all_meeting.setEnabled(false);
                } else
                    all_meeting.setEnabled(true);
            }
        });
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------



        return callMeetingFragmentView;
    }


}
