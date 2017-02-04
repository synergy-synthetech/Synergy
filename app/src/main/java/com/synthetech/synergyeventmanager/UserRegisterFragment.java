package com.synthetech.synergyeventmanager;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.app.ProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegisterFragment extends Fragment implements View.OnClickListener {
    //Declare xml objects
    private FirebaseAuth firebaseAuth;
    private EditText email_register, password_register, re_enter_password_register;
    private Button button_register;
    private TextView login_register;
    ProgressDialog progressDialog;
    FragmentTransaction fragmentTransaction;


    public UserRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Added this line so that onClickListener can work in Fragment
        View userRegisterFragmentView = inflater.inflate(R.layout.fragment_user_register, container, false);

        //Initialise
        //need to use view_name.findviewbyid to use findViewById() method


        login_register = (TextView) userRegisterFragmentView.findViewById(R.id.login_register);
        email_register = (EditText) userRegisterFragmentView.findViewById(R.id.email_register);
        password_register = (EditText) userRegisterFragmentView.findViewById(R.id.password_register);
        re_enter_password_register = (EditText) userRegisterFragmentView.findViewById(R.id.re_enter_password_register);
        button_register = (Button) userRegisterFragmentView.findViewById(R.id.button_register);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this.getActivity());

        button_register.setOnClickListener(this);
        login_register.setOnClickListener(this);


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user_register, container, false);
        //The above line has been commented out and replaced with the line below for onClickListener()
        return userRegisterFragmentView;
    }

    @Override
    public void onClick(View v) {

        if (v == button_register) {

            registerUser();
        }

        if (v == login_register) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }
    }

    private void registerUser() {

        String email = email_register.getText().toString().trim();
        String password = password_register.getText().toString().trim();
        String re_enter_password = re_enter_password_register.getText().toString().trim();

        if (email.length() == 0) {
            email_register.setError("Username is required");
            email_register.requestFocus();
        }
        if (password.length() == 0) {
            password_register.setError("Password is required");
            password_register.requestFocus();
        }
        if (re_enter_password.length() == 0) {
            re_enter_password_register.setError("Please confirm password");
            re_enter_password_register.requestFocus();
        }
        if (!(password.equals(re_enter_password))) {
            //re_enter_password_register.setError("Passwords don't match");
            //re_enter_password_register.requestFocus();
        }
        if (password_register.length() < 8) {
            password_register.setError("Password should be at least 8 characters long");
            password_register.requestFocus();
        }
        else {

            progressDialog.setMessage("Registering user...");
            progressDialog.show();
            //Toast.makeText(this.getActivity(), "Registering User...", Toast.LENGTH_LONG).show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserRegisterFragment.this.getActivity(), "Registration successful!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, profileFragment);
                        fragmentTransaction.commit();

                    } else {
                        Toast.makeText(UserRegisterFragment.this.getActivity(), "Registration unsuccessful!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }//End of else
    }//End of RegisterUser() Method
}//End of class



