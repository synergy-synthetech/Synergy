package com.synthetech.synergyeventmanager;


import android.app.ProgressDialog;
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

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    ListView events;
    ProgressDialog progressDialog;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View searchFragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Loading latest events...");



        events = (ListView) searchFragmentView.findViewById(R.id.eventsListViewSearchFragment);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event");

        progressDialog.show();

        FirebaseListAdapter<CreateEvent> firebaseListAdapter = new FirebaseListAdapter<CreateEvent>(
                this.getActivity(),
                CreateEvent.class,
                R.layout.event_list_view_item,
                databaseReference) {
            @Override
            protected void populateView(View v, CreateEvent eventData, int position) {

                TextView eventName_listView = (TextView) v.findViewById(R.id.event_name_listView);
                TextView eventOrganisation_listView = (TextView) v.findViewById(R.id.event_organisation_listView);
                TextView eventCreator_listView = (TextView) v.findViewById(R.id.event_createdby_listView);
                TextView event_uid_listview = (TextView) v.findViewById(R.id.event_uid_listview);

                eventName_listView.setText(eventData.getName());
                eventOrganisation_listView.setText(eventData.getOrganisation());
                eventCreator_listView.setText(eventData.getCreatorEmail());
                event_uid_listview.setText(eventData.getUID());

            }
        };

        events.setAdapter(firebaseListAdapter);


        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public DataSnapshot datasnapshot;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CreateEvent eventData = (CreateEvent) parent.getItemAtPosition(position);

                String event_uid = eventData.getUID();

                Fragment fr=new EventProfileFragment();
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("EventUID", event_uid);
                fr.setArguments(args);
                ft.replace(R.id.main_container, fr);
                ft.commit();
                //Toast.makeText(view.getContext(), "Event Name: "+eventData.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        progressDialog.dismiss();

        return searchFragmentView;
    }


}
