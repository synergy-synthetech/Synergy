package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.synthetech.synergyeventmanager.R.id.more_call_meeting;
import static com.synthetech.synergyeventmanager.R.id.search_badge;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreEventOptions extends Fragment {

    Button more_call_meeting, more_departments, more_department_heads, more_mentor, join_event, leave_event;

    public MoreEventOptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View moreEventOptions = inflater.inflate(R.layout.fragment_more_event_options, container, false);

        final String event_uid = getArguments().getString("EventUID");

        more_call_meeting = (Button) moreEventOptions.findViewById(R.id.more_call_meeting);
        more_departments = (Button) moreEventOptions.findViewById(R.id.more_departments);
        more_department_heads = (Button) moreEventOptions.findViewById(R.id.more_department_heads);
        more_mentor = (Button) moreEventOptions.findViewById(R.id.more_mentor);
        join_event = (Button) moreEventOptions.findViewById(R.id.join_event);
        leave_event = (Button) moreEventOptions.findViewById(R.id.leave_event);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Join");

        if (firebaseAuth.getCurrentUser() != null) {
            if (databaseReference.child(event_uid + "/" + firebaseAuth.getCurrentUser().getUid()) != null) {
                leave_event.setVisibility(View.VISIBLE);
                join_event.setVisibility(View.GONE);
            }
            else {
                join_event.setVisibility(View.VISIBLE);
                leave_event.setVisibility(View.GONE);
            }
        }

        join_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == join_event) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    JoinEvent joinEvent = new JoinEvent(firebaseAuth.getCurrentUser().getEmail());
                    databaseReference.child("Join/" + event_uid + "/" + firebaseAuth.getCurrentUser().getUid()).setValue(joinEvent);

                    Snackbar.make(getView(), "Event joined successfully!", Snackbar.LENGTH_SHORT).show();
                    join_event.setVisibility(View.GONE);
                    leave_event.setVisibility(View.VISIBLE);
                }
            }
        });

        more_departments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr=new DepartmentFragment();
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
            }
        });

        leave_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databaseReference.child(event_uid).child(firebaseAuth.getCurrentUser().getUid()).setValue(null);
                Snackbar.make(getView(),"You left this event!",Snackbar.LENGTH_SHORT).show();
                join_event.setVisibility(View.VISIBLE);
                leave_event.setVisibility(View.GONE);
                // Toast.makeText(getContext(), databaseReference.toString(), Toast.LENGTH_SHORT).show();
            }
        });

       /* more_call_meeting.setOnClickListener(this);
        more_departments.setOnClickListener(this);
        more_department_heads.setOnClickListener(this);
        more_mentor.setOnClickListener(this);
*/

        return moreEventOptions;
    }

}
