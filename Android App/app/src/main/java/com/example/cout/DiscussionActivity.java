package com.example.cout;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DiscussionActivity extends AppCompatActivity {

    Button btnSendMsg;
    EditText userMsg;
    ListView discussionListView;
    ArrayList<String>discussionList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    String UserEmail, SelectedQuest, user_msg_key;
    private DatabaseReference dbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        userMsg = (EditText) findViewById(R.id.userMessage);

        discussionListView = (ListView) findViewById(R.id.discussList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, discussionList);
        discussionListView.setAdapter(arrayAdapter);

        UserEmail = getIntent().getExtras().get("user_email").toString();
        SelectedQuest = getIntent().getExtras().get("selected_quest").toString();
        System.out.println("quest: "+SelectedQuest);
        System.out.println("email: "+UserEmail);
        setTitle("Topic : " + SelectedQuest);

        System.out.println(discussionListView);
        dbr = FirebaseDatabase.getInstance().getReference().child(SelectedQuest);

        //when send button is clicked send message
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);

                DatabaseReference dbr2 = dbr.child(user_msg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", userMsg.getText().toString());
                map2.put("user", UserEmail);
                dbr2.updateChildren(map2);

                userMsg.setText("");
            }
        });

        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void updateConversation(DataSnapshot dataSnapshot){
        String msg, user, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            msg = (String) ((DataSnapshot)i.next()).getValue();
            user = (String) ((DataSnapshot)i.next()).getValue();

            conversation = user + ": " + msg;
            arrayAdapter.insert(conversation, arrayAdapter.getCount());
            arrayAdapter.notifyDataSetChanged();
        }
    }

}