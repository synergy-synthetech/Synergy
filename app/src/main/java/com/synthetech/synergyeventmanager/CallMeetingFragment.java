package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallMeetingFragment extends Fragment {

    TextView allMembers;
    ListView departmentList;

    public CallMeetingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View callMeetingFragmentView = inflater.inflate(R.layout.fragment_call_meeting, container, false);

        final String event_uid = getArguments().getString("EventUID");

        allMembers = (TextView) callMeetingFragmentView.findViewById(R.id.notificationAllMembers);
        departmentList = (ListView) callMeetingFragmentView.findViewById(R.id.notificationDepartmentSelection);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Event/" + event_uid + "/Department/");

        FirebaseListAdapter<AddDept> firebaseListAdapter = new FirebaseListAdapter<AddDept>(
                getActivity(),
                AddDept.class,
                R.layout.dept_list_view_item,
                databaseReference.orderByChild("dept")) {
            @Override
            protected void populateView(View v, AddDept dept, int position) {

                TextView deptName = (TextView) v.findViewById(R.id.dept_name_listView);
                TextView headName = (TextView) v.findViewById(R.id.dept_head_listView);
                deptName.setText(dept.getDept());
            }
        };

        departmentList.setAdapter(firebaseListAdapter);


        departmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddDept dept = (AddDept) parent.getItemAtPosition(position);

                String deptName = dept.getDept();
                Fragment fr = new CallMeetingPageTwoFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                String pass = event_uid + "#" + deptName ;
                args.putString("passedVar", pass);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
            }
        });


        allMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Fragment fr = new CallMeetingPageTwoFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    String passedVar = event_uid + "#" + "All";
                    args.putString("passedVar", passedVar);
                    fr.setArguments(args);
                    ft.replace(R.id.main_container, fr);
                    ft.commit();
            }
        });

        return callMeetingFragmentView;
    }


}
