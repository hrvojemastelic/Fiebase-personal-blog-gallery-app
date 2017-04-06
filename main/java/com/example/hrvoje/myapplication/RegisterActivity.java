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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mSingUp;
    private Button loginbutton;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.fadein,0);
        mprogress= new  ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users");

        mName = (EditText) findViewById(R.id.namefield);
        mEmail = (EditText) findViewById(R.id.emailfield);
        mPassword = (EditText) findViewById(R.id.passwordfield);
        mSingUp = (Button) findViewById(R.id.signupbtn);

        loginbutton=(Button)findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logintent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(logintent);
            }
        });

        mSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();

            }
        });


    }



    private void startRegister() {
      final  String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(name )&& !TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){

            mprogress.setMessage("In Progress...");
            mprogress.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String user_id=mAuth.getCurrentUser().getUid();

                        DatabaseReference current_user_db=mDatabase.child(user_id);

                        current_user_db.child("name").setValue(name);
                        current_user_db.child("email").setValue(email);
                        current_user_db.child("password").setValue(password);

                        mprogress.dismiss();

                        Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        finish();

                    }else{
                        mprogress.dismiss();
                        Toast.makeText(RegisterActivity.this,"Please fill all fields correctly",Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

    }
}