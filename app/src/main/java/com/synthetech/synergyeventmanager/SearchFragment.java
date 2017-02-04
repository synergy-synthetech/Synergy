package com.synthetech.synergyeventmanager;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
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

        events = (ListView) searchFragmentView.findViewById(R.id.eventsListViewSearchFragment);

        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Loading latest events...");
        progressDialog.show();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event");

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

                eventName_listView.setText(eventData.getName());
                eventOrganisation_listView.setText(eventData.getOrganisation());
                eventCreator_listView.setText(eventData.getCreatorEmail());




            }
        };

        events.setAdapter(firebaseListAdapter);


        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(view.getContext(), "Position: "+position+" "+databaseReference.getDatabase().getReference(), Toast.LENGTH_SHORT).show();

            }
        });

        progressDialog.dismiss();

        return searchFragmentView;
    }


}
