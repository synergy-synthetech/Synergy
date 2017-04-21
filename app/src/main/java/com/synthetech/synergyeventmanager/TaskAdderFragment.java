package com.synthetech.synergyeventmanager;


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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static com.synthetech.synergyeventmanager.R.id.dept_list;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskAdderFragment extends Fragment implements View.OnClickListener {

    Button task_add_button;
    EditText task_add_editText;
    TextView user_uid_text, event_uid_text;
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
        String temp2 = temp [1];

        String temp3[] = temp2.split("@");

        final String user_email = temp3[0];

        pendingList = (ListView) TaskAdderFragmentView.findViewById(R.id.pendingTasks_listView);
        task_add_editText = (EditText) TaskAdderFragmentView.findViewById(R.id.task_add_editText);
        task_add_button = (Button) TaskAdderFragmentView.findViewById(R.id.task_add_button);
        user_uid_text = (TextView) TaskAdderFragmentView.findViewById(R.id.user_uid_textView);
        event_uid_text = (TextView) TaskAdderFragmentView.findViewById(R.id.event_uid_textView);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()== null){
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, loginFragment);
            fragmentTransaction.commit();
        }

        /*DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");

        userRef.orderByChild("email").startAt()*/


        DatabaseReference keyFinder = FirebaseDatabase.getInstance().getReference().child("User/");

        keyFinder.orderByChild("email").equalTo(user_email);

        String another = keyFinder.getParent().toString();

        final String user_uid = keyFinder.getKey();

        /*Toast.makeText(getContext(),user_uid,Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(),another,Toast.LENGTH_LONG).show();*/

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Task/"+ event_uid + user_email);

        final FirebaseListAdapter<Task> firebaseListAdapter = new FirebaseListAdapter<Task>(
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
                DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Task/" + event_uid);

                String task_fetch = task.getTask().toString();

                Toast.makeText(getContext(), task_fetch, Toast.LENGTH_LONG).show();
                Snackbar.make(TaskAdderFragmentView, task.getTask(),Snackbar.LENGTH_LONG).show();

                taskRef.orderByChild("task").equals(task_fetch);

                DatabaseReference Firebase = FirebaseDatabase.getInstance().getReference().child("Task/"+ event_uid);


                Firebase = firebaseListAdapter.getRef(position);
                Firebase.removeValue();

            }
        });

        user_uid_text.setText(user_email);
        event_uid_text.setText(event_uid);

        task_add_button.setOnClickListener(this);

        return TaskAdderFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v == task_add_button){
            String user_uid = user_uid_text.getText().toString();
            String event_uid = event_uid_text.getText().toString();
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("Task/" + event_uid + user_uid);
            String task = task_add_editText.getText().toString();
            String key = taskRef.push().toString();

            Task task_add = new Task(task);
            taskRef.push().setValue(task_add);
            task_add_editText.setText("");
        }
    }
}
