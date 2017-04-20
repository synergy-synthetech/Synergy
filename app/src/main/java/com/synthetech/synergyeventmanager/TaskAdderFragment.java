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
    TextView user_uid_text;
    ListView pendingList;

    public TaskAdderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View TaskAdderFragmentView = inflater.inflate(R.layout.fragment_task_adder, container, false);
        final String passedVar = getArguments().getString("passedVar");

        String temp[] = passedVar.split("#");

        final String event_uid = temp[0];
        final String user_uid = temp [1];

        pendingList = (ListView) TaskAdderFragmentView.findViewById(R.id.pendingTasks_listView);
        task_add_editText = (EditText) TaskAdderFragmentView.findViewById(R.id.task_add_editText);
        task_add_button = (Button) TaskAdderFragmentView.findViewById(R.id.task_add_button);
        user_uid_text = (TextView) TaskAdderFragmentView.findViewById(R.id.user_uid_textView);

        user_uid_text.setText(user_uid);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }

        /*DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");

        userRef.orderByChild("email").startAt()*/


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Task/"+ event_uid + user_uid);

        FirebaseListAdapter<Task> firebaseListAdapter = new FirebaseListAdapter<Task>(
                getActivity(),
                Task.class,
                R.layout.task_item,
                databaseReference) {
            @Override
            protected void populateView(View v, Task model, int position) {
                TextView taskText = (TextView) v.findViewById(R.id.task_textView);
                taskText.setText(model.getTask());
            }

        };

        pendingList.setAdapter(firebaseListAdapter);

        pendingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Task/" + event_uid + user_uid + task.getKey());
                taskRef.setValue(null);

            }
        });

        task_add_button.setOnClickListener(this);

        return TaskAdderFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v == task_add_button){
            String user_uid = user_uid_text.getText().toString();
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User/"+user_uid+"/Task");
            String task = task_add_editText.getText().toString();
            String key = taskRef.push().toString();
            Task task_add = new Task(task,key);
            taskRef.child(key).setValue(task_add);
        }
    }
}
