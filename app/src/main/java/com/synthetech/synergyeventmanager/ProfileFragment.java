package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView email_profile;
    private Button logout;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton fab_edit_profile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        email_profile = (TextView) profileFragmentView.findViewById(R.id.email_profile);
        firebaseAuth = FirebaseAuth.getInstance();


        fab_edit_profile = (FloatingActionButton) profileFragmentView.findViewById(R.id.fab_edit_profile);
        fab_edit_profile.setOnClickListener(this);

        logout = (Button) profileFragmentView.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        if(firebaseAuth.getCurrentUser() == null){
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }

        if (firebaseAuth.getCurrentUser() != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            email_profile.setText("Email: " +user.getEmail());
        }

        return profileFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v == logout){
            firebaseAuth.signOut();
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }

        if(v == fab_edit_profile){
            EditProfile editProfile = new EditProfile();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, editProfile);
            fragmentTransaction.commit();
        }
    }
}
