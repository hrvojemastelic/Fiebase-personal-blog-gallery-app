package com.example.hrvoje.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrvoje.myapplication.SecondGallery.SecondGallery;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by hrvoje on 6.2.2017..
 */

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mBlogList;
    private DatabaseReference gDatabase;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        gDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        gDatabase.keepSynced(true);

        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setItemAnimator(null);
        mBlogList.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mBlogList.setLayoutManager(staggeredGridLayoutManager);
    }


    @Override
        protected void onStart() {
            super.onStart();



            FirebaseRecyclerAdapter<Model,ModelViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ModelViewHolder>(
                    Model.class,
                    R.layout.row_layout,
                    ModelViewHolder.class,
                    gDatabase

        ) {
            @Override
            protected void populateViewHolder(ModelViewHolder viewHolder, Model model, int position) {

                final String post_key=getRef(position).getKey();
                viewHolder.SetTitle(model.getTitle());
                viewHolder.SetDesc(model.getDescription());
                viewHolder.SetImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent fullintent=new Intent(GalleryActivity.this,FullScreen.class);
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
        startActivity(new Intent(GalleryActivity.this, GallerysContainerActivity.class));

    }
}
