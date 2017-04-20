package com.synthetech.synergyeventmanager;


import android.os.Bundle;
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

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.synthetech.synergyeventmanager.R.id.dept_list;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskAdderFragment extends Fragment implements View.OnClickListener {

    Button task_add_button;
    EditText task_add_editText;
    ListView pendingList;
    String user_uid="Test";

    public TaskAdderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View TaskAdderFragmentView = inflater.inflate(R.layout.fragment_task_adder, container, false);

        pendingList = (ListView) TaskAdderFragmentView.findViewById(R.id.pendingTasks_listView);
        task_add_editText = (EditText) TaskAdderFragmentView.findViewById(R.id.task_add_editText);
        task_add_button = (Button) TaskAdderFragmentView.findViewById(R.id.task_add_button);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User/"+user_uid+"Tasks/");

        FirebaseListAdapter<Task> firebaseListAdapter = new FirebaseListAdapter<Task>(
                getActivity(),
                Task.class,
                R.layout.task_item,
                databaseReference) {
            @Override
            protected void populateView(View v, Task model, int position) {
                TextView taskText = (TextView) v.findViewById(R.id.task_textView);
                Button removeTask = (Button) v.findViewById(R.id.task_remove);
                taskText.setText(model.getTask());
            }

        };

        pendingList.setAdapter(firebaseListAdapter);

        pendingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User/"+user_uid+"/Task");

            }
        });

        task_add_button.setOnClickListener(this);

        return TaskAdderFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v == task_add_button){
            String task = task_add_editText.getText().toString();
        }
    }
}
