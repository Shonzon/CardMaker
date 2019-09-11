package com.example.cardmaker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cardmaker.fragments.AboutFragment;
import com.example.cardmaker.fragments.ChangePasswordFragment;
import com.example.cardmaker.fragments.FeaturedFragment;
import com.example.cardmaker.fragments.LogoutFragment;
import com.example.cardmaker.fragments.MyCardFragment;
import com.example.cardmaker.fragments.ReceivedFragment;
import com.example.cardmaker.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;

    private Context mContext;

    private DrawerLayout mDrawerLayout;
    private TextView navUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Showing default fragment on navigation drawer
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new FeaturedFragment())
                    .commit();
        }

        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        mContext = MainActivity.this;

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_action_name);


        NavigationView navigationView = findViewById(R.id.nav_view);

        View headview=navigationView.getHeaderView(0);
        navUserName=(TextView) headview.findViewById(R.id.nav_userName);
        if (currentUser != null){
            myRef = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
            navUserName.setText(currentUser.getEmail());
            FirebaseMessaging.getInstance().subscribeToTopic(currentUser.getUid().toString());
        }


        //navigationView.inflateHeaderView(R.layout.nav_header);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.nav_featured:
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle("Featured Cards");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,new FeaturedFragment())
                                        .commit();
                                break;
                            case R.id.nav_myCards:
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle("MY Cards");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,new MyCardFragment())
                                        .commit();
                                break;
                            case R.id.nav_receivedCards:
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle("Received Cards");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,new ReceivedFragment())
                                        .commit();
                                break;
                            case R.id.nav_ChangePassword:
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle("Change Password");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,new ChangePasswordFragment())
                                        .commit();
                                break;
                            case R.id.nav_AboutUs:
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle("About Us");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,new AboutFragment())
                                        .commit();
                                break;
                            case R.id.nav_LogOut:
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle("Log Out");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,new LogoutFragment())
                                        .commit();
                                break;

                        }

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        navigationView.getMenu().getItem(0).setChecked(true);
        //Highlighted
        //onNavigationItemSelected(navigationView.getMenu().getItem(0));

        setupFirebaseAuth();
        if (currentUser != null){
            //updateNavHeader();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        final TextView navUserName   = navigationView.getHeaderView(0).findViewById(R.id.nav_userName);
        //TextView navUserName = headerView.findViewById(R.id.nav_userName);
        //navUserName.setText(currentUser.getDisplayName());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.child("name").getValue().toString();
                navUserName.setText( data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
























    /***FireBase Database***/
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
    private void setupFirebaseAuth(){
        Log.d(TAG, "setup Firebase Auth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser user = mAuth.getCurrentUser();
        checkCurrentUser(user);
        //updateNavHeader();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onBackPressed() {
        final Boolean exit = true;
        if (exit)
            new AlertDialog.Builder(this)
                    .setTitle("Really Want to Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            // MainActivity.super.onBackPressed();
                            finish();
                            moveTaskToBack(true);
                        }
                    }).create().show();

        else {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();


            if (getSupportFragmentManager().getBackStackEntryCount() ==1) {
                finish();
            }

        }
    }

    //Back pressed
    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
}

