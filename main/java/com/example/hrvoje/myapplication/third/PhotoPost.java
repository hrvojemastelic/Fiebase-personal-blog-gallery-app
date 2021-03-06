package com.example.hrvoje.myapplication.third;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hrvoje.myapplication.MainActivity;
import com.example.hrvoje.myapplication.PostActivity;
import com.example.hrvoje.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PhotoPost extends AppCompatActivity {
    private Button mSubmitBtn;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private ImageButton mSelectImage;
    private static final int GALLERY_REQUEST=1;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private Uri mimageUri =null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_post);

        mPostTitle=(EditText)findViewById(R.id.titlefield) ;
        mPostDesc=(EditText) findViewById(R.id.descfield);
        mSubmitBtn=(Button) findViewById(R.id.addbtn);
        mSelectImage=(ImageButton)findViewById(R.id.imgselect);
        mProgress=new ProgressDialog(this);
        mStorage= FirebaseStorage.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Third");

        mSelectImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gallintent =new Intent(Intent.ACTION_GET_CONTENT);
                gallintent.setType("image/*");
                startActivityForResult(gallintent,GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }


        });

    }

    private void startPosting() {
        mProgress.setMessage("PoSSsstTTtIiiINggGGGG");


        final String tile_val=mPostTitle.getText().toString().trim();
        final String desc_val=mPostDesc.getText().toString().trim();


        if(!TextUtils.isEmpty(tile_val)&&mimageUri!=null){

            StorageReference filepath=mStorage.child("Blog_photos").child(mimageUri.getLastPathSegment());

            mProgress.show();

            filepath.putFile(mimageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri=taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost=mDatabase.push();

                    newPost.child("title").setValue(tile_val);
                    newPost.child("description").setValue(desc_val);
                    newPost.child("image").setValue(downloadUri.toString());


                    mProgress.dismiss();

                    startActivity(new Intent(PhotoPost.this,MainActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){

            mimageUri =data.getData();

            mSelectImage.setImageURI(mimageUri);
        }

    }

}