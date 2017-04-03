package com.synthetech.synergyeventmanager;


import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    ListView events;
    ProgressDialog progressDialog;
    EditText search_term;
    Button search;

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


        search_term = (EditText) searchFragmentView.findViewById(R.id.searchBar);

        search = (Button) searchFragmentView.findViewById(R.id.search_button_main);

        events = (ListView) searchFragmentView.findViewById(R.id.eventsListViewSearchFragment);


        firebaseAuth = FirebaseAuth.getInstance();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/Event");


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(getView(), "Search term: " + search_term.getText().toString(), Snackbar.LENGTH_LONG).show();

                if (v == search) {
                    if (firebaseAuth.getCurrentUser() != null) {

                    /*    if (search_term.getText(e).toString().equals("")) {
                            events.setVisibility(View.GONE);
                        } else
                            events.setVisibility(View.VISIBLE);*/

                        FirebaseListAdapter<CreateEvent> firebaseListAdapter = new FirebaseListAdapter<CreateEvent>(
                                getActivity(),
                                CreateEvent.class,
                                R.layout.event_list_view_item,
                                databaseReference.orderByChild("name").startAt(search_term.getText().toString()).endAt(search_term.getText().toString() + "\uf8ff")) {
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
                    }//End of IF -- Check user is logged in

                    else
                        Snackbar.make(getView(), "User not logged in. Please sign in!", Snackbar.LENGTH_LONG).show();
                }

            }
        });


        return searchFragmentView;
    }


}
