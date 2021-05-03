package com.example.cout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class questionName {
    String id,name;
    questionName(String id){
        id = "";
        name = "NULL";
    }
    void setId(String id,int num){
        this.id = id;
        this.name = "Question" + num;
    }
}

public class QuestionsActivity extends AppCompatActivity {


    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList <questionName> myQuestionNames = new ArrayList<questionName>();

//    List<String> idList;

    void getDocumentIds() {




    }
    String id1,lang;
    ArrayList<String> idArrayList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.questionsProgressbar);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                //progressBarInsideText.setVisibility(View.GONE);
            }

        }, 3000);
        myQuestionNames.clear();
        Intent i  = getIntent();
        id1 = i.getStringExtra("id");
        lang = i.getStringExtra("lang");
        Log.d("id",id1);
        db.collection("topics").document(id1).collection("questions").orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int count = 1;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        Log.d("tag",document.getId());
                        String id = ""+ document.getId()+"";
                        questionName temp = new questionName(id);
                        temp.setId(id + "",count++);
                        myQuestionNames.add(temp);
                    }
                    Log.d("tag",myQuestionNames.get(0).name);
                    int n = myQuestionNames.size();
                    Log.d("tag",n+"");
                    for (int i=0;i<n;i++){
                        idArrayList.add(myQuestionNames.get(i).name + "");
                    }
                }
                else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });



        final ArrayAdapter <String> questionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,idArrayList);

        final ListView questionsListView = (ListView) findViewById(R.id.questionsListView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                questionsListView.setAdapter(questionsArrayAdapter);
                questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(QuestionsActivity.this,CodeActivity.class);
                        Log.d("idQ",idArrayList.get(position)+"");
                        intent.putExtra("id1",id1);
                        intent.putExtra("id2",myQuestionNames.get(position).id);
                        intent.putExtra("language",lang);
                        startActivity(intent);

                    }
                });
            }
        },3000);



    }
}


/*
Firestore dummy data ror reference

// Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);
                    }
                });

 */