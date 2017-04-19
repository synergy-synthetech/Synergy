package com.synthetech.synergyeventmanager;


import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class ChatFragment extends Fragment implements View.OnClickListener {

    ListView chatList;
    EditText chatMessage;
    FloatingActionButton sendButton;
    TextView event_uid_chat;
    MessageTemplate msg;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ChatFragmentView = inflater.inflate(R.layout.fragment_chat, container, false);
        final String event_uid = getArguments().getString("EventUID");

        chatList = (ListView) ChatFragmentView.findViewById(R.id.chat_listView);
        chatMessage = (EditText) ChatFragmentView.findViewById(R.id.message_textView);
        sendButton = (FloatingActionButton) ChatFragmentView.findViewById(R.id.send_chatButton);

        event_uid_chat = (TextView) ChatFragmentView.findViewById(R.id.event_uid_chat);
        event_uid_chat.setText(event_uid);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()==null){
            Snackbar.make(getView(), "You must be signed in to use the chat.",Snackbar.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Redirecting to login screen...",Toast.LENGTH_SHORT).show();

            Fragment fr = new LoginFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            /*Bundle args = new Bundle();
            args.putString("EventUID", event_uid);
            fr.setArguments(args);*/
            ft.replace(R.id.main_container, fr);
            ft.commit();
        }//End of IF


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Event/"+event_uid+"/Chat");

        FirebaseListAdapter<MessageTemplate> firebaseListAdapter = new FirebaseListAdapter<MessageTemplate>(
                getActivity(),
                MessageTemplate.class,
                R.layout.message_template,
                databaseReference.orderByKey()) {
            @Override
            protected void populateView(View v, MessageTemplate model, int position) {
                TextView messageListView = (TextView) v.findViewById(R.id.chat_message_textView);
                TextView userListView = (TextView) v.findViewById(R.id.chat_user_textView);
                TextView timeListView = (TextView) v.findViewById(R.id.chatTime);

                messageListView.setText(model.getMessage());
                userListView.setText(model.getUser());
            }

        };

        chatList.setAdapter(firebaseListAdapter);


       sendButton.setOnClickListener(this);



        return ChatFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v == sendButton){
            String event_uid;
            String message;
            event_uid = event_uid_chat.getText().toString();
            message = chatMessage.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            MessageTemplate msg = new MessageTemplate(message, firebaseAuth.getCurrentUser().getEmail());
            DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Event/"+event_uid+"/Chat");
            chatRef.push().setValue(msg);
            chatMessage.setText("");

        }//End of if
    }
}
