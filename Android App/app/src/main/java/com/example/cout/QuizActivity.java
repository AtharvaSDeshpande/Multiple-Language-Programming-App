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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

class quizQuestionName {
    String id,name;
    quizQuestionName(String id){
        id = "";
        name = "NULL";
    }
    void setId(String id,int num){
        this.id = id;
        this.name = "MCQ" + num;
    }
}

public class QuizActivity extends AppCompatActivity {
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<quizQuestionName> myquizQuestionNames = new ArrayList<quizQuestionName>();

    String id1,lang;
    ArrayList<String> quizidArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.quizprogressBar);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                //progressBarInsideText.setVisibility(View.GONE);
            }

        }, 3000);
        myquizQuestionNames.clear();
        Intent i  = getIntent();
        id1 = i.getStringExtra("id");
        lang = i.getStringExtra("lang");
        Log.d("id",id1);
        db.collection("topics").document(id1).collection("quizQuestions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int count = 1;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        Log.d("tag",document.getId());
                        String id = ""+ document.getId()+"";
                        quizQuestionName temp = new quizQuestionName(id);
                        temp.setId(id + "",count++);
                        myquizQuestionNames.add(temp);
                    }
                    Log.d("tag",myquizQuestionNames.get(0).name);
                    int n = myquizQuestionNames.size();
                    Log.d("tag",n+"");
                    for (int i=0;i<n;i++){
                        quizidArrayList.add(myquizQuestionNames.get(i).name + "");
                    }
                }
                else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });



        final ArrayAdapter<String> questionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,quizidArrayList);

        final ListView questionsListView = (ListView) findViewById(R.id.quizQues);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                questionsListView.setAdapter(questionsArrayAdapter);
                questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(QuizActivity.this,QuizQuestionActivity.class);
                        Log.d("idQ",quizidArrayList.get(position)+"");
                        intent.putExtra("id1",id1);
                        intent.putExtra("id2","mcqid"+position);
                        intent.putExtra("language",lang);
                        intent.putExtra("k1",position);
                        //intent.putExtra("size1",quizidArrayList.size());
                        startActivity(intent);

                    }
                });
            }
        },3000);

    }
}