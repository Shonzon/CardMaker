package com.example.cardmaker.adapter.myCard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cardmaker.R;
import com.example.cardmaker.models.MyCardModel;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.SearchViewHolder> {

    Context context;
    List<MyCardModel> list;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public myAdapter(Context context, List<MyCardModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cardview,viewGroup,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int i) {
        MyCardModel cardModel = list.get(i);
        Glide.with(context).load(list.get(i).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
