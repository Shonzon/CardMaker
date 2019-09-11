package com.example.cardmaker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardmaker.R;
import com.example.cardmaker.models.PersonInfo;
import com.example.cardmaker.models.SendModel;
import com.example.cardmaker.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    Context context;
    List<SendModel> nameList;
    List<SendModel> mDataFiltered;
    List<PersonInfo> personInfos;
    String name;
    String id;

    public SearchAdapter(Context context, List<SendModel> nameList ) {
        this.context = context;
        this.nameList = nameList;
        this.mDataFiltered = nameList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()){
                    mDataFiltered = nameList;
                }else {
                    List<SendModel> listFiltered = new ArrayList<>();
                    for (SendModel row: nameList){
                        if (row.getName().toLowerCase().contains(key.toLowerCase())){
                            listFiltered.add(row);
                        }
                    }
                    mDataFiltered = listFiltered;
                }
                System.out.println(nameList.size());
                FilterResults filterResults= new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (List<SendModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView name;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.searchedText);
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items,viewGroup,false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, final int i) {
        SendModel sendModel = mDataFiltered.get(i);
        searchViewHolder.name.setText(mDataFiltered.get(i).getName());
        name = mDataFiltered.get(i).getName();

        searchViewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"name: "+ name,Toast.LENGTH_SHORT).show();
                //getInfo(personInfos);
                Query query3 = FirebaseDatabase.getInstance().getReference("user")
                        .orderByChild("name")
                        .equalTo(name);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        personInfos = new ArrayList<>();
                        personInfos.clear();
                        //String pID = "";
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PersonInfo value = snapshot.getValue(PersonInfo.class);
                            personInfos.add(value);
                            Log.d("DataINfo", "onDataChange: " +personInfos.get(i));
                            Log.d("PersonID", "onDataChange: " +personInfos.get(i).getUser_id());
                            Log.d("Adapter", "onDataChangeAdapter: "+snapshot);
                            String pID = personInfos.get(i).getUser_id();
                            id = pID;
                            Log.d("idddddd", "onDataChange: " + pID);
                            Log.d("id", "onDataChange: "+id);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//                id  = personInfos.get(i).getUser_id();
                Toast.makeText(context,"ID: "+ id,Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*public void getInfo(final List<PersonInfo> list){
        Query query3 = FirebaseDatabase.getInstance().getReference("user")
                .orderByChild("name")
                .equalTo(name);
        query3.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PersonInfo value = snapshot.getValue(PersonInfo.class);
                    //list.add(value);
                    Log.d("Adapter", "onDataChangeAdapter: "+snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //id  = list.get(i).getUser_id();
        Toast.makeText(context,"ID: "+ id,Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }


}
