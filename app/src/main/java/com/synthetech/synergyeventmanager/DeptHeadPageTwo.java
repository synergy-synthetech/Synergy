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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.synthetech.synergyeventmanager.R.id.message_eventMembers;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeptHeadPageTwo extends Fragment {
    ListView SelectHeadList;


    public DeptHeadPageTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View DeptHeadPageTwo = inflater.inflate(R.layout.fragment_dept_head_page_two, container, false);

        SelectHeadList = (ListView)DeptHeadPageTwo.findViewById(R.id.SelectHeadList);
        final String pass = getArguments().getString("passedVar");
        String temp[] = pass.split("#");
        final String event_uid = temp[0];
        final String dept_add = temp[1];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Event/" + event_uid + "/Member");

        String ref = databaseReference.toString();
        Toast.makeText(getContext(),ref,Toast.LENGTH_LONG).show();

        FirebaseListAdapter<UserInformation> firebaseListAdapter = new FirebaseListAdapter<UserInformation>(
                getActivity(),
                UserInformation.class,
                R.layout.user_list_view_item,
                databaseReference.orderByChild("email")
        ) {
            @Override
            protected void populateView(View v, UserInformation model, int position) {
                TextView name = (TextView) v.findViewById(R.id.user_name_listView);
                TextView email = (TextView) v.findViewById(R.id.user_email_listView);
                TextView phone = (TextView) v.findViewById(R.id.user_phone_listView);
                TextView uid = (TextView) v.findViewById(R.id.user_uid_listview);

                name.setText(model.getName());
                email.setText(model.getEmail());
                phone.setText(model.getPhone());
                uid.setText("");
            }//End of populate View
        };

        SelectHeadList.setAdapter(firebaseListAdapter);

        SelectHeadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserInformation user = (UserInformation) parent.getItemAtPosition(position);

                DatabaseReference addHead = FirebaseDatabase.getInstance().getReference().child("Event/"+event_uid+"/Department/"+dept_add+"/Head");

                addHead.orderByChild("dept").equals(dept_add);

                addHead.push().setValue(user);


                Fragment fr = new TaskAdderFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                String passedVar = event_uid + "#" + user.getUid();
                args.putString("passedVar", passedVar);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
                //Toast.makeText(view.getContext(), "Event Name: "+eventData.getName(), Toast.LENGTH_SHORT).show();

            }

        });




        return DeptHeadPageTwo;
    }

}
