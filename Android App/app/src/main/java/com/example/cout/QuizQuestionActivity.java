package com.example.cout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class QuizQuestionActivity extends AppCompatActivity {

    TextView qque;
    Button a,b,c,d;
    FloatingActionButton qnext,qprev;
    ImageButton backbtn;

    String opa,opb,opc,opd;
    String id1,id2,questName,language;

    int Ans,k1,size1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

//    final DocumentReference docRef =db.collection("UsersInfo").document(currentuser);
//        docRef.collection("submitted").document(id2).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//        @Override
//        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//            if (value !=null && value.exists())
//            {
//                incrementMarks = false;
//            }
//        }
//    });

    public void getdatafromfirestore(){
        final DocumentReference docRef = db.collection("topics").document(id1).collection("quizQuestions").document(id2);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.w("TAG",error);
                    return;
                }
                if(value!=null && value.exists()){
                    Log.d("TAG","Current Data " + value.getData());
                    questName = (String) value.getData().get("0Question");
                    opa = (String)value.getData().get("A");
                    opb = (String)value.getData().get("B");
                    opc = (String)value.getData().get("C");
                    opd = (String)value.getData().get("D");
                    Ans = (int)value.getLong("zAnswer").intValue();


                    qque.setText(questName);
                    a.setText(opa);
                    b.setText(opb);
                    c.setText(opc);
                    d.setText(opd);
                }
                else{
                    Log.d("TAG","Curent Data: NULL");
                }
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        Intent i  = getIntent();
        id1   = i.getStringExtra("id1");
        id2   = i.getStringExtra("id2");
        language = i.getStringExtra("language");
        k1 = i.getIntExtra("k1",0);
       //size1=i.getIntExtra("size1",0);

        Log.d("id",id1);
        //Toast.makeText(this,size1,Toast.LENGTH_SHORT).show();
//        if(k1==0){
//            qprev.setVisibility(View.GONE);
//        }
//        if (k1==size1-1){
//            qnext.setVisibility(View.GONE);
//        }

        getdatafromfirestore();

        getUIs();

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setbtnsGray();
                if(Ans==1){
                    a.setBackgroundColor(Color.GREEN);
                }
                else {
                    a.setBackgroundColor(Color.RED);
                }
                setbtnClickablafalse();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setbtnsGray();
                if(Ans==2){
                    b.setBackgroundColor(Color.GREEN);
                }
                else {
                    b.setBackgroundColor(Color.RED);
                }
                setbtnClickablafalse();
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setbtnsGray();
                if(Ans==3){
                    c.setBackgroundColor(Color.GREEN);
                }
                else {
                    c.setBackgroundColor(Color.RED);
                }
                setbtnClickablafalse();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setbtnsGray();
                if(Ans==4){
                    d.setBackgroundColor(Color.GREEN);
                }
                else {
                    d.setBackgroundColor(Color.RED);
                }
                setbtnClickablafalse();
            }
        });

        qnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizQuestionActivity.this,QuizQuestionActivity.class);
                k1++;
                intent.putExtra("id1",id1);
                intent.putExtra("id2","mcqid"+k1);
                intent.putExtra("k1",k1++);
                startActivity(intent);

            }
        });
        qprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizQuestionActivity.this,QuizQuestionActivity.class);
                k1--;
                intent.putExtra("id1",id1);
                intent.putExtra("id2","mcqid"+k1);
                intent.putExtra("k1",k1--);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //onBackPressed();
                //finish();
                Intent intent = new Intent(QuizQuestionActivity.this, Topics.class);
                intent.putExtra("flag",2);
                startActivity(intent);

            }
        });

    }

    void getUIs(){
        qque = (TextView) findViewById(R.id.qque);
        a =(Button) findViewById(R.id.A);
        b =(Button) findViewById(R.id.B);
        c =(Button) findViewById(R.id.C);
        d =(Button) findViewById(R.id.D);
        qnext = (FloatingActionButton)findViewById(R.id.qnext);
        qprev = (FloatingActionButton)findViewById(R.id.qprev);
        backbtn = (ImageButton)findViewById(R.id.backbtn);
    }

    void setbtnClickablafalse(){
        a.setClickable(false);
        b.setClickable(false);
        c.setClickable(false);
        d.setClickable(false);
    }

    void setbtnsGray(){
        a.setBackgroundColor(Color.LTGRAY);
        b.setBackgroundColor(Color.LTGRAY);
        c.setBackgroundColor(Color.LTGRAY);
        d.setBackgroundColor(Color.LTGRAY);
    }
}