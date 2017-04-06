package com.example.hrvoje.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.hrvoje.myapplication.SecondGallery.SecondGallery;
import com.example.hrvoje.myapplication.third.PhotoGallery;

import de.hdodenhof.circleimageview.CircleImageView;

public class GallerysContainerActivity extends AppCompatActivity {
        private CircleImageView sketchg,fantasyg ,photob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerys_container);




        fantasyg=(CircleImageView) findViewById(R.id.fantasybtn);
        fantasyg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gamesint = new Intent(GallerysContainerActivity.this,GalleryActivity.class);
                startActivity(gamesint);
                finish();
            }
        });


        sketchg=(CircleImageView) findViewById(R.id.sketchbtn);
        sketchg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sketchintent=new Intent(GallerysContainerActivity.this,SecondGallery.class);
                startActivity(sketchintent);
                finish();

            }
        });

        photob=(CircleImageView) findViewById(R.id.photobtn) ;
        photob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phtintent=new Intent(GallerysContainerActivity.this, PhotoGallery.class);
                startActivity(phtintent);
                finish();
            }
        });


    }
}
