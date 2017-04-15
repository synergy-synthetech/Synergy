package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectDepartmentFragment extends Fragment {
    ListView selectdepartment;

    public SelectDepartmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View SelectDepartmentFragment = inflater.inflate(R.layout.fragment_select_department, container, false);

        selectdepartment= (ListView)SelectDepartmentFragment.findViewById(R.id.select_department_listview);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event");
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseListAdapter<CreateEvent> firebaseListAdapter = new FirebaseListAdapter<CreateEvent>(
                getActivity(),
                CreateEvent.class,
                R.layout.dept_list_view_item,
                databaseReference.orderByChild("creatorEmail").equalTo(firebaseAuth.getCurrentUser().getEmail())) {
            @Override
            protected void populateView(View v, CreateEvent eventData, int position) {

                TextView department_name = (TextView) v.findViewById(R.id.dept_name_listView);
                TextView department_head = (TextView) v.findViewById(R.id.dept_head_listView);


                department_name.setText(eventData.getName());

                department_head.setText(eventData.getName());


            }
        };

        selectdepartment.setAdapter(firebaseListAdapter);

        return SelectDepartmentFragment;
    }


}
