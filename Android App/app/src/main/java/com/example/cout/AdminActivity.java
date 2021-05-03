package com.example.cout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList <questionName> myQuestionNames = new ArrayList<questionName>();

    String id1, lang;
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
        Intent i = getIntent();
        id1 = i.getStringExtra("id");
        lang = i.getStringExtra("lang");
        Log.d("id", id1);
        db.collection("topics").document(id1).collection("questions").orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = 1;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("tag", document.getId());
                        String id = "" + document.getId() + "";
                        System.out.println("isApproved:"+document.get("isApproved"));
                        boolean status = (boolean) document.get("isApproved");
                        if(!status) {
                            questionName temp = new questionName(id);
                            temp.setId(id + "", count++);
                            myQuestionNames.add(temp);
                        }
                    }
                    if(myQuestionNames.size()>0) {
                        Log.d("tag", myQuestionNames.get(0).name);
                        int n = myQuestionNames.size();
                        Log.d("tag", n + "");
                        for (int i = 0; i < n; i++) {
                            idArrayList.add(myQuestionNames.get(i).name + "");
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });


        final ArrayAdapter<String> questionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, idArrayList);

        final ListView questionsListView = (ListView) findViewById(R.id.questionsListView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(myQuestionNames.size()>=1) {
                    questionsListView.setAdapter(questionsArrayAdapter);
                    questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(AdminActivity.this, CodeActivity.class);
                            Log.d("idQ", idArrayList.get(position) + "");
                            intent.putExtra("flag",1);
                            intent.putExtra("id1", id1);
                            intent.putExtra("id2", myQuestionNames.get(position).id);
                            intent.putExtra("language", lang);
                            startActivity(intent);

                        }
                    });
                }
                else{
                    System.out.println("no data in array");
                }
            }
        }, 3000);


    }

}