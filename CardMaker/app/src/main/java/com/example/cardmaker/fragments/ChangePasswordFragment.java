package com.example.cardmaker.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardmaker.R;
import com.example.cardmaker.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ChangePasswordFragment extends Fragment {

    private Button updateBtn;
    private EditText oldPass,newPass;
    private TextView mPleaseWait;
    private ProgressBar mProgressBar;
    private String oldpass,newpass,passfromDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepassword,container,false);

        newPass = view.findViewById(R.id.newPass);
        oldPass = view.findViewById(R.id.oldPass);
        updateBtn = view.findViewById(R.id.updateBtn);
        mProgressBar = view.findViewById(R.id.loginRequestLoadingProgressBar);
        mPleaseWait = view.findViewById(R.id.pleaseWait);

        mPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        oldpass = oldPass.getText().toString();
        newpass = newPass.getText().toString();



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                mPleaseWait.setVisibility(View.VISIBLE);
                updatePassword();
                mProgressBar.setVisibility(View.INVISIBLE);
                mPleaseWait.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
    private void updatePassword(){
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("user").child(mAuth.getCurrentUser().getUid());

        oldpass = oldPass.getText().toString();
        newpass = newPass.getText().toString();

        if (oldpass.isEmpty() || newpass.isEmpty()){
            Toast.makeText(getActivity(),"Field can not be blank",Toast.LENGTH_SHORT).show();
        }else {
            if (mAuth != null){

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null){
                            passfromDatabase = dataSnapshot.child("password").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (!oldpass.equals(passfromDatabase)){
                    Toast.makeText(getActivity(),"Password is not matching",Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, Object> result1 = new HashMap<>();
                    result1.put("name", newpass);
                    myRef.updateChildren(result1);
                    Toast.makeText(getActivity(),"Successfully updated Password",Toast.LENGTH_SHORT).show();

                    mAuth.signOut();

                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            }
        }
    }
}





























/*
package com.example.look.snp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.look.snp.R;
import com.example.look.snp.adapter.FtAdapters.RvAdapter;
import com.example.look.snp.adapter.MyAdapters.MyAdapter;
import com.example.look.snp.adapter.custom.FtAdapter;
import com.example.look.snp.models.RvModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangePasswordFragment extends Fragment {
    private static final String TAG = "ChangePasswordFragment";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FtAdapter mAdapter;
    private Context context;

    private List<RvModel> feedItemList;


    public ChangePasswordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepassword,container,false);

        //find recyclerview layout
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // recyclerview set layoutmanager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adding data to arraylist
        feedItemList = new ArrayList<RvModel>();
        for (int i = 0; i < 4; i++) {
            RvModel getterSetter = new RvModel();
            feedItemList.add(getterSetter);
        }

        //recyclerview adapter
        mAdapter = new FtAdapter(feedItemList,context);

        //set adpater for recyclerview
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}
*/
