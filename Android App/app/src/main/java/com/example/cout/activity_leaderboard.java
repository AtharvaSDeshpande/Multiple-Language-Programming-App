package com.example.cout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;


import java.util.*;



public class activity_leaderboard extends AppCompatActivity {



    final ArrayList<String> list = new ArrayList<String>();
    ListView userScoreList;
    ProgressBar progressBar;
    TextView progressBarInsideText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getScore();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBarInsideText = (TextView)findViewById(R.id.progressBarInsideText) ;
        userScoreList = (ListView) findViewById(R.id.userScoreList);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_expandable_list_item_1, list);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                userScoreList.setAdapter(arrayAdapter);
                //progressBarInsideText.setVisibility(View.GONE);
            }

        }, 2000);

        // userScoreList.setAdapter(arrayAdapter);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {


                ///add set Adapter here

            }
        //},1000);


    //}

    public void getScore()
    {
        final FirebaseFirestore db= FirebaseFirestore.getInstance();



        db.collection("UsersInfo").orderBy("Score", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {



                    //HashMap<String,Integer>list = new HashMap<String, Integer>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Integer score = Integer.parseInt(document.get("Score").toString());
                        String name = document.get("name").toString();
                        UserInfo user = new UserInfo(name,score);
                        list.add("Name: "+user.getName()+"\nScore: "+user.getScore());

                    }
                    System.out.println("\n"+list);


                } else {
                    Log.d("TAG", "Error getting document");
                }
            }
        });



    }

}
