package com.synthetech.synergyeventmanager;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText name_create, organsation_create, date_create, website_create, venue_create;
    private Switch management_create, marketing_create, creative_create, finance_create, guest_managers_create, sales_create, fnb_create;
    private Button button_back_create, button_create;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View createFragmentView = inflater.inflate(R.layout.fragment_create, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Redirecting to Login Page...");

        name_create = (EditText) createFragmentView.findViewById(R.id.name_create);
        organsation_create = (EditText) createFragmentView.findViewById(R.id.organisation_create);
        date_create = (EditText) createFragmentView.findViewById(R.id.date_create);
        website_create = (EditText) createFragmentView.findViewById(R.id.website_create);
        venue_create = (EditText) createFragmentView.findViewById(R.id.venue_create);

        management_create = (Switch) createFragmentView.findViewById(R.id.management_create);
        marketing_create = (Switch) createFragmentView.findViewById(R.id.marketing_create);
        creative_create = (Switch) createFragmentView.findViewById(R.id.creative_create);
        finance_create = (Switch) createFragmentView.findViewById(R.id.finance_create);
        guest_managers_create = (Switch) createFragmentView.findViewById(R.id.guest_managers_create);
        sales_create = (Switch) createFragmentView.findViewById(R.id.sales_create);
        fnb_create = (Switch) createFragmentView.findViewById(R.id.fnb_create);

        //button_back_create = (Button) createFragmentView.findViewById(R.id.button_back_create);
        button_create = (Button) createFragmentView.findViewById(R.id.button_create);

        if (firebaseAuth.getCurrentUser() == null) {
            progressDialog.show();
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
            progressDialog.dismiss();
        }



        databaseReference = FirebaseDatabase.getInstance().getReference();



        button_create.setOnClickListener(this);

            return createFragmentView;
        }


    public void CreateNewEvent(){

        String name = name_create.getText().toString().trim();
        String organisation = organsation_create.getText().toString().trim();
        String date = date_create.getText().toString().trim();
        String website = website_create.getText().toString().trim();
        String venue = venue_create.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String creator_uid = user.getUid();
        String email = user.getEmail();

        management_create.setOnCheckedChangeListener(this);

        CreateEvent createEvent = new CreateEvent(name,organisation,venue,date,website,creator_uid,email);

        databaseReference.child("Event/"+name+"_"+creator_uid).setValue(createEvent);

        String event_uid = createEvent.getUID();
        Fragment fr=new EventProfileFragment();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Bundle args = new Bundle();
        args.putString("EventUID", event_uid);
        fr.setArguments(args);
        ft.replace(R.id.main_container, fr);
        ft.commit();
        Snackbar.make(this.getView(),"Event created!",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if (v == button_create){
            CreateNewEvent();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      if (management_create.isChecked())
      {
        return;
      }
    }
}
