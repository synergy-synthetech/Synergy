package com.synthetech.synergyeventmanager;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfile extends Fragment implements View.OnClickListener {

    private EditText name_edit, phone_edit, email_edit;
    private Button save_edit;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public EditProfile() {
        // Required empty public uctor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editProfileFragment = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        name_edit = (EditText) editProfileFragment.findViewById(R.id.name_edit);
        phone_edit = (EditText) editProfileFragment.findViewById(R.id.phone_edit);
        email_edit = (EditText) editProfileFragment.findViewById(R.id.email_edit);
        save_edit = (Button) editProfileFragment.findViewById(R.id.save_edit);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user!=null)
            email_edit.setText(user.getEmail());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://synergy-9f467.firebaseio.com/User/"+user.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                String name = userInformation.getName();
                String phone = userInformation.getPhone();

                if (name != null)
                    name_edit.setText(name);

                if (phone != null)
                    phone_edit.setText(phone);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        databaseReference = FirebaseDatabase.getInstance().getReference();

        save_edit.setOnClickListener(this);

        return editProfileFragment;
    }


    public void saveUserInformation() {
        String name = name_edit.getText().toString().trim();
        String phone = phone_edit.getText().toString().trim();
        String email = email_edit.getText().toString().trim();


        UserInformation userInformation = new UserInformation(name, phone, email);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("User/" + user.getUid()).setValue(userInformation);
    }

    @Override
    public void onClick(View v) {
        if (v == save_edit) {
            //Update
            saveUserInformation();
            Snackbar.make(this.getView(), "Changes updated!", Snackbar.LENGTH_SHORT).show();
        }

    }
}
