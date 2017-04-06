package com.example.hrvoje.myapplication.third;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FullPhoto extends AppCompatActivity {

    private ImageView fsimageview;
    private TextView fsname;
    private Button removebutton;
    private String post_key=null;
    private DatabaseReference mDatabase;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo);

        removebutton=(Button)findViewById(R.id.removebtn) ;

        fsimageview=(ImageView)findViewById(R.id.fsimageview);
        fsname=(TextView)findViewById(R.id.fsname);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Third");;




        post_key=getIntent().getExtras().getString("blog_id");

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title=(String)dataSnapshot.child("title").getValue();
                String post_image=(String)dataSnapshot.child("image").getValue();

                fsname.setText(post_title);
                Picasso.with(FullPhoto.this).load(post_image).into(fsimageview);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(post_key).removeValue();

                Intent mainintent=new Intent(FullPhoto.this,PhotoGallery.class);
                startActivity(mainintent);
                finish();

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FullPhoto.this, PhotoGallery.class));

    }
}
