package com.example.cout;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import com.example.cout.Api.ApiHandler;
import com.example.cout.Api.ApiService;
import com.example.cout.Api.PostData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivityCode extends AppCompatActivity {
    String id1,id2,questName,language;
    TextView tvResult;
    boolean approval;

    EditText etInput,header,answer,problemStatement;
    String answer_1,answer_2,publicTestCase,privateTestCase;
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Button btnSubmit,btnRun,btnApprove,btnDeny;
    String userName;
    question myQuestion = new question();
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String TAG = "TAG";
    int marks,counterques;
    boolean incrementMarks = true;
    public void getMarksDataFromFirestore()
    {
        final DocumentReference docRef =db.collection("UsersInfo").document(currentuser);
        docRef.collection("submitted").document(id2).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null && value.exists())
                {
                    incrementMarks = false;
                }
            }
        });

        Log.d("User",incrementMarks+"");
//        {
//            incrementMarks = true;
//        }
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.w("TAG",error);
                    return;
                }
                if(value!=null && value.exists()){
                    marks =  Integer.parseInt(value.getData().get("Score").toString());
                    counterques = Integer.parseInt(value.getData().get("questionsSolved").toString());
                    Log.d("tag",marks+"");
                }
                else{
                    Log.d("TAG","Curent Data: NULL");
                }
            }
        });
    }
    public void getCodeDataFromFirestore(){
        myQuestion = new question();
        final DocumentReference docRef = db.collection("topics").document(id1).collection("questions").document(id2);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.w("TAG",error);
                    return;
                }
                if(value!=null && value.exists()){
                    myQuestion.reset();
                    Log.d("TAG","Current Data " + value.getData());
                    List listHeader,listAnswer_1,listAnswer_2,listTestCases;
//                    String privateTestcaseOutput;
                    questName = (String) value.getData().get("0_problemStatement");
                    listHeader = (List) value.getData().get("1_header");
                    listAnswer_1 = (List) value.getData().get("3_answer_1");
                    listAnswer_2 = (List) value.getData().get("5_answer_2");
                    listTestCases = (List) value.getData().get("4_testcases");

                    myQuestion.privateTestCaseOutput = (String) value.getData().get("6privateTestCaseOutput");
                    myQuestion.addHeader(listHeader);
                    myQuestion.addAnswer(listAnswer_1,listTestCases,listAnswer_2);
                    header.setText(myQuestion.header);
                    answer.setText(myQuestion.publicAnswer);
                    problemStatement.setText("Q)  " + questName);
                }
                else{
                    Log.d("TAG","Curent Data: NULL");
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_code);
        int flag =0;
        Intent i  = getIntent();
        flag = i.getIntExtra("flag",0);
        id1   = i.getStringExtra("id1");
        id2   = i.getStringExtra("id2");
        language = i.getStringExtra("language");
        Log.d("id",id1);

        getCodeDataFromFirestore();
        tvResult  = findViewById(R.id.tv_result);
        header = findViewById(R.id.header);
        answer = findViewById(R.id.answer);
        etInput   = findViewById(R.id.et_input);
        btnSubmit = findViewById(R.id.btn_submit);
        btnRun = findViewById(R.id.btn_run);
        btnApprove = findViewById(R.id.btnApprove);
        btnDeny = findViewById(R.id.btnDeny);
        problemStatement = findViewById(R.id.problemStatement);
        answer_1 = "} \n int main() \n { \n";
        answer_2 = "\n return 0; \n }  ";
        publicTestCase = "  cout<<\"The sum of two numbers (5,15) is\"<<add(5,15)<<endl;";
        privateTestCase = " cout<<\"The sum of two numbers (25,25) is\"<<add(25,25)<<endl;";
        String questionHeader = myQuestion.header;
        Log.d("TAG1",questionHeader);
        header.setText(questionHeader);
        etInput.setText("");
        answer.setText("");
        System.out.println("flag:"+flag);
        if(flag ==1 )
        {
            btnDeny.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            btnApprove.setVisibility(View.VISIBLE);
        }
        else{
            btnSubmit.setVisibility(View.VISIBLE);
            btnDeny.setVisibility(View.GONE);
            btnApprove.setVisibility(View.GONE);
        }

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DocumentReference docRef = db.collection("topics")
                        .document(id1).collection("questions")
                        .document(id2);
                docRef.update("isApproved",true);

                Toast.makeText(AdminActivityCode.this,"Question approved for publishing ",Toast.LENGTH_LONG).show();
                btnApprove.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);

            }
        });

        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminActivityCode.this,"Your question isn't approved for publishing",Toast.LENGTH_SHORT).show();
            }
        });



        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService apiService = ApiHandler.getRetrofitInstance(language);
                Call<String> execute = apiService.execute(new PostData(header.getText().toString() + etInput.getText().toString() + myQuestion.publicAnswer));

                tvResult.setText("Loading...");

                execute.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        tvResult.setText("");

                        try {

                            if(response.isSuccessful()){


                                JSONObject responseJson = new JSONObject(response.body().toString());

                                String output = responseJson.getString("output");
                                Log.d("op",output);
                                tvResult.setText(output);

                            }else{

                                Toast.makeText(AdminActivityCode.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(AdminActivityCode.this, "Gagal Parsing JSON : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        tvResult.setText("Failed");
                        Toast.makeText(AdminActivityCode.this, "Gagal : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService apiService = ApiHandler.getRetrofitInstance(language);
                Call<String> execute = apiService.execute(new PostData(header.getText().toString() + etInput.getText().toString() + myQuestion.privateAnswer));

                tvResult.setText("Loading...");

                execute.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        tvResult.setText("");

                        try {

                            if(response.isSuccessful()){


                                JSONObject responseJson = new JSONObject(response.body().toString());
                                String output = "NOT PASSED",submitOutput = responseJson.getString("output");
                                boolean isCorrect = false;
                                if (submitOutput.equals(myQuestion.privateTestCaseOutput))
                                {
                                    output = submitOutput + "\n\n" + "PASSED";
                                    isCorrect = true;
                                }
                                tvResult.setText(output);
                                if (isCorrect)
                                {
                                    getMarksDataFromFirestore();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (incrementMarks)
                                            {


                                                counterques++;

                                                marks+=10;

                                                Map<String, Object> data = new HashMap<>();
                                                data.put("Score", marks);
                                                data.put("questionsSolved",counterques);
                                                Map <String,Object> doc = new HashMap<>();
                                                doc.put("Score",1);
                                                doc.put("questionsSolved",1);
                                                db.collection("UsersInfo").document(currentuser).set(data, SetOptions.merge());
                                                db.collection("UsersInfo").document(currentuser).collection("submitted").document(id2).set(doc);

                                            }
                                        }
                                    },3000);

                                }
                            }else{

                                Toast.makeText(AdminActivityCode.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(AdminActivityCode.this, "Gagal Parsing JSON : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        tvResult.setText("Failed");
                        Toast.makeText(AdminActivityCode.this, "Gagal : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}