package com.example.cardmaker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cardmaker.R;
import com.example.cardmaker.adapter.ReceiveCardAdapter;
import com.example.cardmaker.models.SendModel;
import com.example.cardmaker.models.UploadModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class ReceivedFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ReceiveCardAdapter receiveCardAdapter;

    private DatabaseReference databaseReference;
    private List<UploadModel> uploadModelList,namefindbyke;
    FirebaseUser firebaseUser;
    View view;
    SearchView search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_receivedcard,container,false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference= FirebaseDatabase.getInstance().getReference("receivecard");
        recyclerView=(RecyclerView) view.findViewById(R.id.receiverecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        uploadModelList=new ArrayList<>();

        receiveCardAdapter=new ReceiveCardAdapter(getContext(),uploadModelList);
        recyclerView.setAdapter(receiveCardAdapter);
        search = (SearchView) view.findViewById(R.id.receivesearchEmail);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploadModelList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    UploadModel uploadModel=ds.getValue(UploadModel.class);
                    if (firebaseUser.getUid().equalsIgnoreCase(uploadModel.getUserKey())){
                        uploadModelList.add(uploadModel);
                    }
                }
                receiveCardAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(view.getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        search.setOnQueryTextListener(this);
        return view;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        if (!search.isIconified()){
            search.setIconified(true);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)){
            receiveCardAdapter.resetList(uploadModelList);
        }else {
            final List<UploadModel> filterModellist=filter(uploadModelList,s);
            receiveCardAdapter.updateList(filterModellist);
        }
        return false;
    }
    private List<UploadModel> filter(List<UploadModel> sendModels,String query){
        query=query.toLowerCase();
        final List<UploadModel> filtersendList=new ArrayList<>();
        for (UploadModel model:sendModels){
            final String no=model.getUserEmail().toLowerCase();
            final String na=model.getUserName().toLowerCase();
            if (no.contains(query) || na.contains(query)){
                filtersendList.add(model);
            }
        }
        return filtersendList;
    }
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
import com.example.look.snp.adapter.FtAdapters.RvAdapter;
import com.example.look.snp.adapter.FtAdapters.RvAdapter1;
import com.example.look.snp.adapter.FtAdapters.RvAdapter2;
import com.example.look.snp.adapter.FtAdapters.RvAdapter3;
import com.example.look.snp.adapter.FtAdapters.RvAdapter4;
import com.example.look.snp.adapter.FtAdapters.RvAdapter5;
import com.example.look.snp.adapter.FtAdapters.RvAdapter6;
import com.example.look.snp.adapter.TestAdapter;
import com.example.look.snp.models.RvModel;
import com.example.look.snp.models.RvModel1;
import com.example.look.snp.models.RvModel2;
import com.example.look.snp.models.RvModel3;
import com.example.look.snp.models.RvModel4;
import com.example.look.snp.models.RvModel5;
import com.example.look.snp.models.RvModel6;
import com.example.look.snp.test.ListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReceivedFragment extends Fragment {
    private static final String TAG = "ReceivedFragment";

    RecyclerView recyclerView;
    TestAdapter adapter;
    List<ListItem> items;


    public ReceivedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receivedcard,container,false);

        items = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TestAdapter(getActivity(),items);
        recyclerView.setAdapter(adapter);

        return view;
    }
}*/
