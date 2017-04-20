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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuestListFragment extends Fragment implements View.OnClickListener {

    EditText guestname,guestnumber;
    Button addtolistbutton;
    ListView guestlist;
    TextView eventname_guestlist;

    public GuestListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View GuestListFragment = inflater.inflate(R.layout.fragment_guest_list, container, false);

        final String event_uid = getArguments().getString("EventUID");

        guestlist = (ListView)GuestListFragment.findViewById(R.id.guest_list);
        addtolistbutton = (Button)GuestListFragment.findViewById(R.id.add_to_list_button);
        guestname = (EditText)GuestListFragment.findViewById(R.id.guest_name);
        guestnumber = (EditText)GuestListFragment.findViewById(R.id.guest_number);
        eventname_guestlist = (TextView)GuestListFragment.findViewById(R.id.eventname_guestlist);

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        String rootRef = dataRef.toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(rootRef+"/Event/"+event_uid+"/Department/");

        FirebaseListAdapter<AddDept> firebaseListAdapter = new FirebaseListAdapter<AddDept>(
                getActivity(),
                AddDept.class,
                R.layout.dept_list_view_item,
                databaseReference.orderByChild("dept")) {
            @Override
            protected void populateView(View v, AddDept dept, int position) {

                TextView deptName = (TextView)v.findViewById(R.id.dept_name_listView);
                deptName.setText(dept.getDept());

                TextView deptHead = (TextView)v.findViewById(R.id.dept_head_listView);
                deptHead.setText(dept.getLeader());



            }
        };

        guestlist.setAdapter(firebaseListAdapter);


        guestlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddDept eventData = (AddDept) parent.getItemAtPosition(position);

                //String dept_name = eventData.getDept();

                Fragment fr = new EventProfileFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
                //Toast.makeText(view.getContext(), "Event Name: "+eventData.getName(), Toast.LENGTH_SHORT).show();

            }
        });


       eventname_guestlist.setText(event_uid);
        addtolistbutton.setOnClickListener(this);




        return GuestListFragment;


    }

    @Override
    public void onClick(View v) {
        if (v==addtolistbutton){
            String deptName = guestname.getText().toString();
            String deptHead = guestnumber.getText().toString();


            String event_uid = eventname_guestlist.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            AddDept addDept = new AddDept(deptName);
            addDept.setLeader(deptHead);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Guest/" + event_uid).push().setValue(addDept);
            String ref = databaseReference.toString();
            Snackbar.make(this.getView(), ref, Snackbar.LENGTH_LONG).show();





        }
    }
}
