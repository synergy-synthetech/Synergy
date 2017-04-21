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


/**
 * A simple {@link Fragment} subclass.
 */
public class HeadDeptFragment extends Fragment {

    ListView Selectdepartment_head;


    public HeadDeptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View HeadDepartmentView = inflater.inflate(R.layout.fragment_head_dept, container, false);

        final String event_uid = getArguments().getString("EventUID");

        Selectdepartment_head = (ListView) HeadDepartmentView.findViewById(R.id.DepartmentList_head);

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

        Selectdepartment_head.setAdapter(firebaseListAdapter);


        Selectdepartment_head.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddDept dept = (AddDept) parent.getItemAtPosition(position);

                String deptName = dept.getDept();
                Fragment fr = new DeptHeadPageTwo();
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

        return HeadDepartmentView;
    }

}
