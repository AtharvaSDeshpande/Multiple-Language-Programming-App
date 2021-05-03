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
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
class topicName {
    String id,name;
    topicName(String id,String name){
        id = "";
        name = "NULL";
    }
    void setId(String id,String Name){
        this.id = id;
        this.name = Name;
    }
}
public class Topics extends AppCompatActivity {

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<topicName> myTopicNames = new ArrayList<topicName>();

//    List<String> idList;

    void getDocumentIds() {




    }
    ArrayList<String> idArrayList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.topicsProgressbar);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                //progressBarInsideText.setVisibility(View.GONE);
            }

        }, 3000);
        myTopicNames.clear();
        db.collection("topics").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int count = 1;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        Log.d("tag",document.getId());
                        String id = ""+ document.getId()+"";
                        String name = "" + document.getData().get("name");
                        topicName temp = new topicName(id,name);
                        temp.setId(id + "",name);
                        myTopicNames.add(temp);
                    }
//                    Log.d("tag", myTopicNames.get(0).name);
                    int n = myTopicNames.size();
//                    Log.d("tag",n+"");
                    for (int i=0;i<n;i++){
                        idArrayList.add(myTopicNames.get(i).name + "");
                    }
                }
                else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });



        final ArrayAdapter<String> questionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,idArrayList);

        final ListView topicsListView = (ListView) findViewById(R.id.topicsListView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                topicsListView.setAdapter(questionsArrayAdapter);
                topicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Topics.this,QuestionsActivity.class);
                        Log.d("idQ",idArrayList.get(position)+"");
                        intent.putExtra("id", myTopicNames.get(position).id);
                        intent.putExtra("lang", myTopicNames.get(position).name);
                        startActivity(intent);

                    }
                });
            }
        },3000);



    }
}