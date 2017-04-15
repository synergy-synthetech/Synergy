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
import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment implements View.OnClickListener {

    TextView EventName, DeptName;
    Button addBTN;
    ListView dept_list;


    public DepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View DepartmentFragmentView = inflater.inflate(R.layout.fragment_department, container, false);
        final String event_uid = getArguments().getString("EventUID");
        DeptName= (TextView)DepartmentFragmentView.findViewById(R.id.new_dept_name);
        addBTN = (Button)DepartmentFragmentView.findViewById(R.id.add_dept_button);
        EventName = (TextView)DepartmentFragmentView.findViewById(R.id.event_name_dept);
        dept_list = (ListView)DepartmentFragmentView.findViewById(R.id.dept_list);
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

                DeptName.setText(dept.getDept());

            }
        };

        dept_list.setAdapter(firebaseListAdapter);


        dept_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddDept eventData = (AddDept) parent.getItemAtPosition(position);

                String dept_name = eventData.getDept();

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


        EventName.setText(event_uid);
        addBTN.setOnClickListener(this);


        return DepartmentFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v==addBTN){
            String deptName = DeptName.getText().toString();
            String event_uid = EventName.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            AddDept addDept = new AddDept(deptName);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Event/"+event_uid+"/Department/"+deptName).setValue(addDept);
            //databaseReference.setValue(addDept);
            String ref = databaseReference.toString();
            Snackbar.make(this.getView(),ref,Snackbar.LENGTH_LONG).show();
        }
    }
}
