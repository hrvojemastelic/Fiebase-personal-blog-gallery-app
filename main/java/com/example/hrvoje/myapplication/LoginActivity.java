package com.example.hrvoje.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText mloginemailfield;
    private EditText mloginpasswordfield;
    private Button mloginbtn;
    private FirebaseAuth mAuth;
   private DatabaseReference lDatabase;
    private ProgressDialog lProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        lProgress= new ProgressDialog(this);

        lDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        lDatabase.keepSynced(true);


        mloginbtn=(Button) findViewById(R.id.loginbtn);
        mloginemailfield=(EditText)findViewById(R.id.loginemailfield);
        mloginpasswordfield=(EditText)findViewById(R.id.loginpasswordfield);

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chechlogin();

            }
        });


    }

    private void chechlogin() {
        String email=mloginemailfield.getText().toString().trim();
        String paasword=mloginpasswordfield.getText().toString().trim();

        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(paasword)){
            lProgress.setMessage("Checking login....");
            lProgress.show();

                mAuth.signInWithEmailAndPassword(email,paasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            lProgress.dismiss();
                            CheckUserExist();
                        }
                        else{
                            lProgress.dismiss();
                            Toast.makeText(LoginActivity.this, "Error , wrong email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        }


    }

    private void CheckUserExist() {
        final String user_id=mAuth.getCurrentUser().getUid();

        lDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){

                    Intent login=new Intent(LoginActivity.this,MainActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    finish();
                }
                else{
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
