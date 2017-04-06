package com.example.hrvoje.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.hrvoje.myapplication.SecondGallery.SecondPost;
import com.example.hrvoje.myapplication.third.PhotoGallery;
import com.example.hrvoje.myapplication.third.PhotoPost;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivityChooser extends AppCompatActivity {

        private CircleImageView fantasybtn, sketch, photobtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_chooser);

        sketch=(CircleImageView) findViewById(R.id.sketchbtn);
        sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sketchint=new Intent(PostActivityChooser.this, SecondPost.class);
                startActivity(sketchint);
            }
        });

        fantasybtn=(CircleImageView) findViewById(R.id.fantasybtn);
        fantasybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fantasyint=new Intent(PostActivityChooser.this,PostActivity.class);
                startActivity(fantasyint);
            }
        });

        photobtn=(CircleImageView) findViewById(R.id.photobtn);
        photobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phtintent=new Intent(PostActivityChooser.this, PhotoPost.class);
                startActivity(phtintent);
                finish();
            }
        });

    }
}
