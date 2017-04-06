package com.example.hrvoje.myapplication.SecondGallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hrvoje.myapplication.FullScreen;
import com.example.hrvoje.myapplication.GalleryActivity;
import com.example.hrvoje.myapplication.GallerysContainerActivity;
import com.example.hrvoje.myapplication.R;
import com.example.hrvoje.myapplication.third.FullPhoto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FullSecond extends AppCompatActivity {


    private ImageView fsimageview;
    private TextView fsname;
    private Button removebutton;
    private String post_key=null;
    private DatabaseReference sDatabase;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_second);

        removebutton=(Button)findViewById(R.id.removebtn) ;

        fsimageview=(ImageView)findViewById(R.id.fsimageview);
        fsname=(TextView)findViewById(R.id.fsname);

        sDatabase= FirebaseDatabase.getInstance().getReference().child("Second");;




        post_key=getIntent().getExtras().getString("blog_id");

        sDatabase.child(post_key).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title=(String)dataSnapshot.child("title").getValue();
                String post_image=(String)dataSnapshot.child("image").getValue();

                fsname.setText(post_title);
                Picasso.with(FullSecond.this).load(post_image).into(fsimageview);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sDatabase.child(post_key).removeValue();

                Intent mainintent=new Intent(FullSecond.this,SecondGallery.class);
                startActivity(mainintent);
                finish();

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FullSecond.this, SecondGallery.class));

    }
}
