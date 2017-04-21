package com.synthetech.synergyeventmanager;


import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.synthetech.synergyeventmanager.R.id.more_call_meeting;
import static com.synthetech.synergyeventmanager.R.id.search_badge;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreEventOptions extends Fragment {

    Button more_call_meeting, more_departments, more_department_heads, more_mentor, more_guest, join_event, leave_event, chatButton;

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
        more_guest = (Button) moreEventOptions.findViewById(R.id.more_guest);
        more_departments = (Button) moreEventOptions.findViewById(R.id.more_departments);
        more_department_heads = (Button) moreEventOptions.findViewById(R.id.more_department_heads);
        more_mentor = (Button) moreEventOptions.findViewById(R.id.more_mentor);
        join_event = (Button) moreEventOptions.findViewById(R.id.join_event);
        leave_event = (Button) moreEventOptions.findViewById(R.id.leave_event);
        chatButton = (Button) moreEventOptions.findViewById(R.id.chat_button_moreOptions);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event/"+event_uid+"/Member");

        if (firebaseAuth.getCurrentUser() != null) {
            if (databaseReference.child(firebaseAuth.getCurrentUser().getUid()) != null) {
                leave_event.setVisibility(View.VISIBLE);
                join_event.setVisibility(View.GONE);
            } else {
                join_event.setVisibility(View.VISIBLE);
                leave_event.setVisibility(View.GONE);
            }
        }


        DatabaseReference mentorRef = FirebaseDatabase.getInstance().getReference().child("Event/" + event_uid + "/Mentor");
        if (mentorRef != null) {
            mentorRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInformation mentor = dataSnapshot.getValue(UserInformation.class);
                    //if (mentor.getEmail()!=null) {
                        //more_mentor.setText("Mentor: "+mentor.getEmail());
                    //}
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }//end of if --- mentorRef

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == chatButton){
                    /*Fragment fr = new ChatFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("EventUID", event_uid);
                    fr.setArguments(args);
                    ft.replace(R.id.main_container, fr);
                    ft.commit();*/

                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("EventUID", event_uid);
                    startActivity(intent);
                }
            }
        });

        join_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == join_event) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    //JoinEvent joinEvent = new JoinEvent(firebaseAuth.getCurrentUser().getEmail());
                    DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("User/"+firebaseAuth.getCurrentUser().getUid());

                    userData.orderByChild("email").equals(firebaseAuth.getCurrentUser().getEmail());

                    userData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            final UserInformation user = dataSnapshot.getValue(UserInformation.class);
                            Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                            databaseReference.child("Event/" + event_uid + "/Member/" + firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            databaseReference.child("Join/"+event_uid+firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            Snackbar.make(getView(), "Event joined successfully!", Snackbar.LENGTH_SHORT).show();
                            join_event.setVisibility(View.GONE);
                            leave_event.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }
            }
        });

        more_departments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new DepartmentFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
            }
        });


              more_call_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new CallMeetingFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
            }
        });



        more_department_heads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new HeadDeptFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
            }
        });


        more_mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new MentorSelect();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
            }
        });

        more_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new GuestListFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
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
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                //JoinEvent joinEvent = new JoinEvent(firebaseAuth.getCurrentUser().getEmail());
                DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("User/");

                userData.orderByChild("email").equals(firebaseAuth.getCurrentUser().getEmail());

                userData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        final UserInformation user = dataSnapshot.getValue(UserInformation.class);
                        databaseReference.child("Event/" + event_uid + "/Member/" + firebaseAuth.getCurrentUser().getUid()).setValue(null);
                        databaseReference.child("Join/"+event_uid+firebaseAuth.getCurrentUser().getUid()).setValue(null);
                        Snackbar.make(getView(), "Event left successfully!", Snackbar.LENGTH_SHORT).show();
                        join_event.setVisibility(View.VISIBLE);
                        leave_event.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



        return moreEventOptions;
    }

}
