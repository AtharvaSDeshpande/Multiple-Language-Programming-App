package com.example.cout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    private EditText E_mail;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private TextView SignUp;
    private int counter=5;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        E_mail = (EditText)findViewById(R.id.etEMail);
        Password = (EditText)findViewById(R.id.etPassword);
        //Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        SignUp = (TextView)findViewById(R.id.tvRegister);

        //Info.setText("No of attempts left: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();  //getting current user

        if(user != null)     //if current user is already logged,finish and new page
        {
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on clicking login button
                if(!checkifempty())
                {
                    Toast.makeText(MainActivity.this,"Login Failed!",Toast.LENGTH_SHORT).show();
                }
                else
                    validate(E_mail.getText().toString(),Password.getText().toString());
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                           // on clicking new user?
                Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String usermail,String userpassword)   //validate fn defination  //if correct login creds,only then allow to default page
    {

        progressDialog.setMessage("Just a moment,Setting you up!");
        progressDialog.show();

        //CHECK IF THE CURRENT USER_E-MAIL AND P/W ARE IN DATABASE OR NOT
        firebaseAuth.signInWithEmailAndPassword(usermail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()  )  //if authentication successful,added to the database and redirected to default page(2)
                {
                    progressDialog.dismiss();
                    checkEmailVerification();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Failed!",Toast.LENGTH_SHORT).show();
//                    counter--;
//                    Info.setText("No of attempts left:" + String.valueOf(counter));
//                    if(counter==0)
//                    {
//                        Login.setEnabled(false);
//                    }
                }

            }
        });
    }

    private boolean checkifempty()
    {
        boolean result=false;

        //converting E-mail and Password value to string and storing in local var n
        String e = E_mail.getText().toString();
        String p = Password.getText().toString();

        if(e.isEmpty() || p.isEmpty() )   //if any of the fields are empty,flash a msg
        {
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();   //get current user
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            Toast.makeText(MainActivity.this,"Login Successful!",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }
        else {
            Toast.makeText(this,"Please verify your email first",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();                                                                         //user gets signIn on task.successful and then we check for verfication
        }

    }
}