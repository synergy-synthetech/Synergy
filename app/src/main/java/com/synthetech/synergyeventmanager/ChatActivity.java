package com.synthetech.synergyeventmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


import static java.security.AccessController.getContext;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    ListView chatList;
    EditText message;
    Button sendButton;
    TextView eventuid_txt;
    TextView usermsg, chatmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String event_uid = getIntent().getStringExtra("EventUID");

        chatList = (ListView) findViewById(R.id.chatActivityList);
        message = (EditText) findViewById(R.id.messageEditText);
        sendButton = (Button) findViewById(R.id.sendChatButton);
        eventuid_txt = (TextView) findViewById(R.id.chatActivityEventUID);
        eventuid_txt.setText(event_uid);




        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Snackbar.make(findViewById(R.id.chatContainer), "You must be signed in to use the chat.", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Redirecting to login screen...", Toast.LENGTH_SHORT).show();
            finish();
        }//End of IF


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Event/" + event_uid + "/Chat/");


        Toast.makeText(this, "Ref: "+databaseReference.toString(),Toast.LENGTH_LONG).show();



        FirebaseListAdapter<MessageTemplate> firebaseListAdapter = new FirebaseListAdapter<MessageTemplate>(
                ChatActivity.this,
                MessageTemplate.class,
                R.layout.message_template,
                databaseReference.orderByChild("time")) {
            @Override
            protected void populateView(View v, MessageTemplate msg, int position) {

                chatmsg = (TextView) v.findViewById(R.id.chat_message_textView);
                usermsg = (TextView) v.findViewById(R.id.chat_user_textView);

                chatmsg.setText(msg.getMessage());

                usermsg.setText(msg.getUser());

            }
        };

        chatList.setAdapter(firebaseListAdapter);

        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == sendButton){
            String event_uid;
            String chat;
            event_uid = eventuid_txt.getText().toString();
            chat = message.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            MessageTemplate msg = new MessageTemplate(chat, firebaseAuth.getCurrentUser().getEmail());
            DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Event/"+event_uid+"/Chat");
            chatRef.push().setValue(msg);
            message.setText("");
        }
    }
}
