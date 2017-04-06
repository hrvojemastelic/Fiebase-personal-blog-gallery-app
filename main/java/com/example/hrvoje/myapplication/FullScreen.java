package com.example.hrvoje.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by hrvoje on 13.2.2017..
 */
public class FullScreen extends AppCompatActivity {
    private ImageView fsimageview;
    private TextView fsname;
    private Button removebutton;
    private String post_key=null;
    private DatabaseReference gDatabase;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        overridePendingTransition(R.anim.fadein,0);

        removebutton=(Button)findViewById(R.id.removebtn) ;

        fsimageview=(ImageView)findViewById(R.id.fsimageview);
        fsname=(TextView)findViewById(R.id.fsname);

        gDatabase=FirebaseDatabase.getInstance().getReference().child("Blog");;




         post_key=getIntent().getExtras().getString("blog_id");

        gDatabase.child(post_key).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title=(String)dataSnapshot.child("title").getValue();
                String post_image=(String)dataSnapshot.child("image").getValue();

                fsname.setText(post_title);
                Picasso.with(FullScreen.this).load(post_image).into(fsimageview);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    removebutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            gDatabase.child(post_key).removeValue();

            Intent mainintent=new Intent(FullScreen.this,GalleryActivity.class);
            startActivity(mainintent);
            finish();

        }
    });


      
    }   @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FullScreen.this, GalleryActivity.class));

    }
}
