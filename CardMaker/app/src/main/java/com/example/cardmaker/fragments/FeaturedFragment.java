package com.example.cardmaker.fragments;

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

import com.example.cardmaker.R;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter1;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter2;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter3;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter4;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter5;
import com.example.cardmaker.adapter.FtAdapters.RvAdapter6;
import com.example.cardmaker.models.RvModel;
import com.example.cardmaker.models.RvModel1;
import com.example.cardmaker.models.RvModel2;
import com.example.cardmaker.models.RvModel3;
import com.example.cardmaker.models.RvModel4;
import com.example.cardmaker.models.RvModel5;
import com.example.cardmaker.models.RvModel6;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeaturedFragment extends Fragment {
    private static final String TAG = "ReceivedFragment";

    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView recyclerView4;
    RecyclerView recyclerView5;
    RecyclerView recyclerView6;

    List<RvModel> dataList;
    List<RvModel1> dataList1;
    List<RvModel2> datalist2;
    List<RvModel3> datalist3;
    List<RvModel4> datalist44;
    List<RvModel5> datalist5;
    List<RvModel6> datalist6;


    RvAdapter adapter;
    RvAdapter1 adapter1;
    RvAdapter2 adapter2;
    RvAdapter3 adapter3;
    RvAdapter4 adapter4;
    RvAdapter5 adapter5;
    RvAdapter6 adapter6;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1,
            databaseReference2,databaseReference3,databaseReference4
            ,databaseReference5,databaseReference6;

    public FeaturedFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured,container,false);

        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView1 = view.findViewById(R.id.recyclerView2);
        recyclerView2 = view.findViewById(R.id.recyclerView3);
        recyclerView3 = view.findViewById(R.id.recyclerView4);
        recyclerView4 = view.findViewById(R.id.recyclerView5);
        recyclerView5 = view.findViewById(R.id.recyclerView6);
        recyclerView6 = view.findViewById(R.id.recyclerView7);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("testtemp").child("one");
        databaseReference1 = firebaseDatabase.getReference("testtemp").child("two");
        databaseReference2 = firebaseDatabase.getReference("testtemp").child("three");
        databaseReference3 = firebaseDatabase.getReference("testtemp").child("four");
        databaseReference4 = firebaseDatabase.getReference("testtemp").child("five");
        databaseReference5 = firebaseDatabase.getReference("testtemp").child("six");
        databaseReference6 = firebaseDatabase.getReference("testtemp").child("seven");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//1
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel template = templateSnap.getValue(RvModel.class);
                    dataList.add(template);
                }
                adapter = new RvAdapter(getActivity(),dataList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
//2
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList1 = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel1 template = templateSnap.getValue(RvModel1.class);
                    dataList1.add(template);
                }
                adapter1 = new RvAdapter1(getActivity(),dataList1);
                recyclerView1.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
//3
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datalist2 = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel2 template = templateSnap.getValue(RvModel2.class);
                    datalist2.add(template);
                }
                adapter2 = new RvAdapter2(getActivity(),datalist2);
                recyclerView2.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
//4
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datalist3 = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel3 template = templateSnap.getValue(RvModel3.class);
                    datalist3.add(template);
                }
                adapter3 = new RvAdapter3(getActivity(),datalist3);
                recyclerView3.setAdapter(adapter3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
//5
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datalist44 = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel4 template1 = templateSnap.getValue(RvModel4.class);
                    datalist44.add(template1);
                }
                adapter4 = new RvAdapter4(getActivity(),datalist44);
                recyclerView4.setAdapter(adapter4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
        //6
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datalist5 = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel5 template = templateSnap.getValue(RvModel5.class);
                    datalist5.add(template);
                }
                adapter5 = new RvAdapter5(getActivity(),datalist5);
                recyclerView5.setAdapter(adapter5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        //7
        databaseReference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datalist6 = new ArrayList<>();
                for (DataSnapshot templateSnap: dataSnapshot.getChildren()){
                    RvModel6 template = templateSnap.getValue(RvModel6.class);
                    datalist6.add(template);
                }
                adapter6 = new RvAdapter6(getActivity(),datalist6);
                recyclerView6.setAdapter(adapter6);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
    }
}

