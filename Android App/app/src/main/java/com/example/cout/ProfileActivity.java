package com.example.cout;

import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import org.w3c.dom.Text;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private Button Logout;
    private FirebaseAuth firebaseAuth;
    private TextView nameLabel;
    private TextView scoreLabel;
    private TextView emailLabel;
    private ProgressDialog progressDialog;
    public Boolean admin = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameLabel = (TextView)findViewById(R.id.name);
        scoreLabel = (TextView)findViewById(R.id.score);
        emailLabel = (TextView)findViewById(R.id.email);


        progressDialog = new ProgressDialog(this);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logoutfn(); //calling logout fn
            }
        });

    }

    private void setupUIViews(){                                // setting the values from ui to these variables

        Logout = (Button)findViewById(R.id.btnLogout);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        System.out.println("email:  "+currentUser.getEmail()+" UID: "+currentUser.getUid());
        final DocumentReference docRef = db.collection("UsersInfo").document(currentUser.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null && value.exists()){
                    nameLabel.setText("Name:  "+value.get("name"));
                    scoreLabel.setText("Score:  "+value.get("Score"));
                    emailLabel.setText("Email:  "+currentUser.getEmail());
                    admin = (Boolean) value.get("isAdmin");
                    System.out.println(admin);
                }
            }
        });


    }

    private void Logoutfn(){   //defining logout fn
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);               // getting menu from resources
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuLogout:                          // menuLogout is the id from menu for logout
                Logoutfn();
                break;

            case R.id.admin_switch:
                if(admin)
                {
                    progressDialog.setMessage("Just a moment,Switching to admin mode");
                    progressDialog.show();
                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {
                            progressDialog.cancel();
                        }
                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 3000);
                    Intent intent = new Intent(ProfileActivity.this, SecondActivity.class);
                    intent.putExtra("admin",admin);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this,"You don't have access",Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }
}