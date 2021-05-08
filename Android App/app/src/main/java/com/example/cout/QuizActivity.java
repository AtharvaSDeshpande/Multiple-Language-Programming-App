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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
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
    ArrayList<String> quizNameArrayList = new ArrayList();
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
                        quizNameArrayList.add(myquizQuestionNames.get(i).name + "");
                        quizidArrayList.add(myquizQuestionNames.get(i).id);
                    }
                }
                else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });



        final ArrayAdapter<String> questionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quizNameArrayList);

        final ListView questionsListView = (ListView) findViewById(R.id.quizQues);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!questionsArrayAdapter.isEmpty()) {
                    questionsListView.setAdapter(questionsArrayAdapter);
                    questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(QuizActivity.this, QuizQuestionActivity.class);
                            Log.d("idQ", quizNameArrayList.get(position) + "");
                            intent.putExtra("id1", id1);
                            intent.putExtra("k1", position);
                            intent.putExtra("UB", quizNameArrayList.size());
                            intent.putExtra("array", quizidArrayList);
                            //intent.putExtra("size1",quizidArrayList.size());
                            startActivity(intent);

                        }
                    });
                    TextView msgTextView =  findViewById(R.id.emptymsg2);
                    msgTextView.setVisibility(View.INVISIBLE);
                }
                else
                {
                    TextView msgTextView =  findViewById(R.id.emptymsg2);
                    msgTextView.setVisibility(View.VISIBLE);
                }
            }
        },3000);

    }
}