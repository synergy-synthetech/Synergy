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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText email_login, password_login;
    private TextView register_login,forgot_password;
    private Button button_login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View userLoginFragment = inflater.inflate(R.layout.fragment_login, container, false);


        email_login = (EditText) userLoginFragment.findViewById(R.id.email_login);
        password_login = (EditText) userLoginFragment.findViewById(R.id.password_login);
        register_login = (TextView) userLoginFragment.findViewById(R.id.register_login);
        button_login = (Button) userLoginFragment.findViewById(R.id.button_login);
        forgot_password= (TextView)userLoginFragment.findViewById(R.id.forgot_password_textview);
        progressDialog = new ProgressDialog(this.getActivity());


        button_login.setOnClickListener(this);
        register_login.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, profileFragment);
            fragmentTransaction.commit();
        }

        return userLoginFragment;
    }

    @Override
    public void onClick(View v) {

        if (v == button_login)
            userLogin();

        if (v == register_login) {
            UserRegisterFragment userRegisterFragment = new UserRegisterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, userRegisterFragment);
            fragmentTransaction.commit();


        }

    }//End of onClick() method

    public void userLogin() {
        String email = email_login.getText().toString().trim();
        String password = password_login.getText().toString().trim();

        if (email.length() == 0) {
            email_login.setError("Username is required");
            email_login.requestFocus();
        }
        if (password.length() == 0) {
            password_login.setError("Password is required");
            password_login.requestFocus();
        }
        if (email.length() > 0 && password.length() > 0) {
            progressDialog.setMessage("Signing in...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginFragment.this.getActivity(), "Login successful!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle args = new Bundle();
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        args.putString("UserUID", firebaseAuth.getCurrentUser().getUid());
                        fragmentTransaction.replace(R.id.main_container, profileFragment);
                        fragmentTransaction.commit();

                    } else {
                        Toast.makeText(LoginFragment.this.getActivity(),
                                "Login unsuccessful: " + task.getException().getMessage(), //ADD THIS
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }

    }
}
