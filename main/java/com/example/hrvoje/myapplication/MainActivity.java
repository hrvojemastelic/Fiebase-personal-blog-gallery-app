package com.example.hrvoje.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hrvoje.myapplication.SecondGallery.SecondPost;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView sStatus;
    final Context context = this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    public EditText newpost;
    private DatabaseReference pDatabase;
    public Button post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sStatus=(TextView)findViewById(R.id.textViewstatus);

        overridePendingTransition(R.anim.fadein,0);

        pDatabase=FirebaseDatabase.getInstance().getReference().child("status");

        newpost=(EditText)findViewById(R.id.new_post);
        post=(Button)findViewById(R.id.post_btn);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });


        mAuth=FirebaseAuth.getInstance();
        mAuthlistener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent register=new Intent(MainActivity.this,RegisterActivity.class);
                    register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(register);
                }
            }
        };





       //ALERT DIALOG
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Hi there!  ");
        alertDialogBuilder
                .setMessage("Just swipe from left to right --> ;))")
                .setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



       //FLOATING ACTION BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,PostActivityChooser.class);
                startActivity(intent);
            }
        });



        //NAVIGATION DRAWER

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void startPosting() {


        final String post_val = newpost.getText().toString().trim();

       pDatabase.setValue(post_val);


    }


    @Override
                   public void onStart() {
             super.onStart();



        mAuth.addAuthStateListener(mAuthlistener);
        pDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String   newpost= String.valueOf(dataSnapshot.getValue());
                sStatus.setText(newpost);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



         }
              @Override
                             public void onBackPressed() {
                          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                 if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

                 @Override
                                 public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
                         getMenuInflater().inflate(R.menu.main, menu);
                    return true;
    }

                     @Override
                         public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
                  int id = item.getItemId();

        if (id==R.id.logout){
            logout();


        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void logout() {
        mAuth.signOut();







    }

    @SuppressWarnings("StatementWithEmptyBody")

    //ON ITEM SELECTED IN NAVIGATION DRAWER

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            startActivity(new Intent(MainActivity.this,GallerysContainerActivity.class));
        }
        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
