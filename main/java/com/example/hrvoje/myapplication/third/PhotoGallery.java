package com.example.hrvoje.myapplication.third;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hrvoje.myapplication.FullScreen;
import com.example.hrvoje.myapplication.GalleryActivity;
import com.example.hrvoje.myapplication.GallerysContainerActivity;
import com.example.hrvoje.myapplication.Model;
import com.example.hrvoje.myapplication.R;
import com.example.hrvoje.myapplication.SecondGallery.SecondGallery;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PhotoGallery extends AppCompatActivity {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Third");
        mDatabase.keepSynced(true);

        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mBlogList.setLayoutManager(staggeredGridLayoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();



        FirebaseRecyclerAdapter<Model,GalleryActivity.ModelViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, GalleryActivity.ModelViewHolder>(
                Model.class,
                R.layout.row_layout,
                GalleryActivity.ModelViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(GalleryActivity.ModelViewHolder viewHolder, Model model, int position) {

                final String post_key=getRef(position).getKey();
                viewHolder.SetTitle(model.getTitle());
                viewHolder.SetDesc(model.getDescription());
                viewHolder.SetImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent fullintent=new Intent(PhotoGallery.this,FullPhoto.class);
                        fullintent.putExtra("blog_id", post_key);
                        startActivity(fullintent);
                        finish();

                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        public View mView;


        public ModelViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void SetTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void SetDesc(String description) {
            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(description);
        }
        public void SetImage(final Context ctx, final String image){
            final ImageView post_image=(ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(post_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(post_image);
                }
            });
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PhotoGallery.this, GallerysContainerActivity.class));

    }
}