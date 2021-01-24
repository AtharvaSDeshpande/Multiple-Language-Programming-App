package com.example.cout;

import android.provider.ContactsContract;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class SecondActivity extends AppCompatActivity {
    private ImageView prof, score, website , quiz;
    private TextView approveText;
    public ImageView approve;
    private Button code;
    Boolean admin;
    ProfileActivity profile = new ProfileActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        admin = i.getBooleanExtra("admin", false);
        System.out.println("admin in second activity:" + admin);


        setupUIViews();
//        Toast.makeText(SecondActivity.this,"lalazzzzzzzzzz",Toast.LENGTH_SHORT).show();

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Topics.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, activity_leaderboard.class));
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, WebsiteActivity.class));
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Topics.class);
                intent.putExtra("flag", 2);
                startActivity(intent);
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, Topics.class);
                intent.putExtra("flag", 3);
                startActivity(intent);
            }
        });

    }
        private void setupUIViews()
        {
            //System.out.println("hello");// setting the values from ui to these variables
            System.out.println("Admin is:" + profile.admin);
            approve = (ImageView) findViewById(R.id.btnApprove);
            approveText = (TextView) findViewById(R.id.textApprove);
            if (admin) {
                Toast.makeText(this, "You are swtiched into admin mode", Toast.LENGTH_SHORT);
                approve.setVisibility(View.VISIBLE);
                approveText.setVisibility(View.VISIBLE);
            } else {
                approve.setVisibility(View.GONE);
                approveText.setVisibility(View.GONE);
            }
            prof = (ImageView) findViewById(R.id.btnProf);
            score = (ImageView) findViewById(R.id.btnLeaderboard);
            code = (Button) findViewById(R.id.btncode);
            website = (ImageView) findViewById(R.id.btnWeb);
            quiz = (ImageView) findViewById(R.id.btnquiz);

        }

}