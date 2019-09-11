package com.example.cardmaker.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cardmaker.R;
import com.example.cardmaker.adapter.myCard.myAdapter;
import com.example.cardmaker.models.MyCardModel;
import com.example.cardmaker.models.SendModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MyCardFragment extends Fragment {

    RecyclerView recyclerView;
    myAdapter adapter;
    DatabaseReference databaseReference;
    TextView textAlert;
    List<MyCardModel> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycards,container,false);
        recyclerView = view.findViewById(R.id.rv);
        textAlert = view.findViewById(R.id.textAlert);
        textAlert.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference("mycardtemp").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Query query6 = databaseReference
                .orderByChild("image");
        query6.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //list.clear();
            if (dataSnapshot.exists()) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyCardModel value = snapshot.getValue(MyCardModel.class);
                    list.add(value);
                    adapter = new myAdapter(getActivity(),list);
                }
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }else {
                textAlert.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


}


























/*
package com.example.look.snp.fragments;


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
import com.example.look.snp.adapter.MyAdapters.MyAdapter;
import com.example.look.snp.adapter.MyAdapters.MyAdapter1;
import com.example.look.snp.adapter.MyAdapters.MyAdapter2;
import com.example.look.snp.adapter.MyAdapters.MyAdapter3;
import com.example.look.snp.adapter.MyAdapters.MyAdapter4;
import com.example.look.snp.adapter.MyAdapters.MyAdapter5;
import com.example.look.snp.adapter.MyAdapters.MyAdapter6;
import com.example.look.snp.models.RvModel;
import com.example.look.snp.models.RvModel1;
import com.example.look.snp.models.RvModel2;
import com.example.look.snp.models.RvModel3;
import com.example.look.snp.models.RvModel4;
import com.example.look.snp.models.RvModel5;
import com.example.look.snp.models.RvModel6;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyCardFragment extends Fragment {
    private static final String TAG = "ChangePasswordFragment";

    RecyclerView rec;
    RecyclerView rec1;
    RecyclerView rec2;
    RecyclerView rec3;
    RecyclerView rec4;
    RecyclerView rec5;
    RecyclerView rec6;

    MyAdapter adapter;
    MyAdapter1 adapter1;
    MyAdapter2 adapter2;
    MyAdapter3 adapter3;
    MyAdapter4 adapter4;
    MyAdapter5 adapter5;
    MyAdapter6 adapter6;

    List<RvModel> List;
    List<RvModel1> List1;
    List<RvModel2> List2;
    List<RvModel3> List3;
    List<RvModel4> List4;
    List<RvModel5> List5;
    List<RvModel6> List6;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReference4;
    DatabaseReference databaseReference5;
    DatabaseReference databaseReference6;

    FirebaseUser me;

    public MyCardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycards,container,false);

        rec = view.findViewById(R.id.rec1);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec1 = view.findViewById(R.id.rec2);
        rec1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec2 = view.findViewById(R.id.rec3);
        rec2.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec3 = view.findViewById(R.id.rec4);
        rec3.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec4 = view.findViewById(R.id.rec5);
        rec4.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec5 = view.findViewById(R.id.rec6);
        rec5.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec6 = view.findViewById(R.id.rec7);
        rec6.setLayoutManager(new LinearLayoutManager(getActivity()));


        firebaseDatabase = FirebaseDatabase.getInstance();
        me = FirebaseAuth.getInstance().getCurrentUser();
        if (me != null) {
            databaseReference = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());
            databaseReference1 = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());
            databaseReference2 = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());
            databaseReference3 = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());
            databaseReference4 = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());
            databaseReference5 = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());
            databaseReference6 = firebaseDatabase.getReference().child("usertemplate").child(me.getUid());

        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        databaseReference.child("one").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List = new ArrayList<>();
                RvModel temp = dataSnapshot.getValue(RvModel.class);
                List.add(temp);
                adapter = new MyAdapter(getActivity(),List);
                rec.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference1.child("two").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List1 = new ArrayList<>();
                RvModel1 temp1 = dataSnapshot.getValue(RvModel1.class);
                List1.add(temp1);

                adapter1 = new MyAdapter1(getActivity(),List1);
                rec1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference2.child("three").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List2 = new ArrayList<>();
                RvModel2 temp1 = dataSnapshot.getValue(RvModel2.class);
                List2.add(temp1);

                adapter2 = new MyAdapter2(getActivity(),List2);
                rec2.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference3.child("four").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List3 = new ArrayList<>();
                RvModel3 temp1 = dataSnapshot.getValue(RvModel3.class);
                List3.add(temp1);

                adapter3 = new MyAdapter3(getActivity(),List3);
                rec3.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference4.child("five").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List4 = new ArrayList<>();
                RvModel4 temp1 = dataSnapshot.getValue(RvModel4.class);
                List4.add(temp1);

                adapter4 = new MyAdapter4(getActivity(),List4);
                rec4.setAdapter(adapter4);
                adapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference5.child("six").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List5 = new ArrayList<>();
                RvModel5 temp1 = dataSnapshot.getValue(RvModel5.class);
                List5.add(temp1);

                adapter5 = new MyAdapter5(getActivity(),List5);
                rec5.setAdapter(adapter5);
                adapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference6.child("seven").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List6 = new ArrayList<>();
                RvModel6 temp1 = dataSnapshot.getValue(RvModel6.class);
                List6.add(temp1);

                adapter6 = new MyAdapter6(getActivity(),List6);
                rec6.setAdapter(adapter6);
                adapter6.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Database Error!!!", "Failed to read value.", databaseError.toException());
            }
        });
    }
}

*/
