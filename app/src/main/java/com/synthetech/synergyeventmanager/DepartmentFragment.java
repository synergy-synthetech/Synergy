package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends Fragment implements View.OnClickListener {
    final String event_uid = getArguments().getString("EventUID");
    EditText DeptName;
    Button addBTN;


    public DepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View DepartmentFragmentView = inflater.inflate(R.layout.fragment_department, container, false);


        DeptName= (EditText)DepartmentFragmentView.findViewById(R.id.new_dept_name);
        addBTN = (Button)DepartmentFragmentView.findViewById(R.id.add_dept_button);

        addBTN.setOnClickListener(this);

        return DepartmentFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v==addBTN){
            String deptName = DeptName.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            AddDept addDept = new AddDept(deptName);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Event/"+event_uid+"/Department/");
            databaseReference.setValue(addDept);
            Snackbar.make(this.getView(),"Department added successfully!",Snackbar.LENGTH_LONG).show();
        }
    }
}
