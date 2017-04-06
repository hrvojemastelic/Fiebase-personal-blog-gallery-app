package com.example.hrvoje.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

;

/**
 * Created by hrvoje on 6.2.2017..
 */

public class SplashScreen extends AppCompatActivity {

        TextView  entere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        overridePendingTransition(R.anim.fadein,0);

    entere=(TextView)findViewById(R.id.enterr);
        entere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entintent=new Intent(SplashScreen.this,RegisterActivity.class);
                startActivity(entintent);
                finish();
            }
        });




    }


}







